package dev.dankom.logger.profiler;

import dev.dankom.logger.LogManager;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.util.general.JavaUtil;
import dev.dankom.util.general.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class Profiler {
    private List<ProfilerSection> sections = new ArrayList<>();
    private final ILogger logger = LogManager.getLogger("profiler");

    public void clear() {
        sections.clear();
    }

    public void startSection(String section) {
        try {
            stopSection(getCurrentSection().getName());
        }
        catch (IndexOutOfBoundsException e) {}
        catch (NullPointerException e) {}

        ProfilerSection s = new ProfilerSection(section);
        sections.add(s);
        getCurrentSection().start();
    }

    public void stopSection(String section) {
        getSection(section).stop();
    }

    public void crash(String message, Exception e, String... extraMachineInfo) {
        String[] phrases = new String[] { "Really Again!", "This is horrible :(", "Why! so many bugs :c", "This is horrible!", "Im giving up again!" };

        getCurrentSection().crash();

        try {
            logger.error("<crash>", "// " + phrases[(int) MathUtil.randDouble(1, phrases.length)]);
            logger.error("<crash>", getCurrentSection().getName() + " Section: " + message + " (Start: " + getCurrentSection().getStartTime().getSeconds() + "s, End: " + getCurrentSection().getEndTime().getSeconds() + "s, Time Passed: " + getCurrentSection().getTimePassed().getSeconds() + "s)");
            logger.error("<crash>", "------------------Stacktrace------------------");
            logger.error("<crash>", e.getClass().getName() + ": " + e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) {
                logger.error("<crash>", "  " + ste.toString());
            }
            if (e.getCause() != null) {
                logger.error("<crash>", "-----------------------------------------");
                logger.error("<crash>", "     ");
                logger.error("<crash>", "------------------Cause------------------");
                Throwable cause = e.getCause();
                logger.error("<crash>", "Cause by " + cause.getClass().getSimpleName());
                for (StackTraceElement ste : cause.getStackTrace()) {
                    logger.error(getClass().getSimpleName(), "  " + ste.toString());
                }
            }
            logger.error("<crash>", "-----------------------------------------");
            logger.error("<crash>", "     ");
            logger.error("<crash>", "----------Specs----------");
            logger.error("<crash>", "Java Version: " + JavaUtil.version());
            logger.error("<crash>", "Available Memory: " + JavaUtil.freeMemory());
            for (String s : extraMachineInfo) {
                logger.error("<crash>", s);
            }
            logger.error("<crash>", "-------------------------");
        } catch (NullPointerException exception) {
            logger.error("<crash>$exception>", "Profiler failed to profile!");
            exception.printStackTrace();
        } catch (Exception exception) {
            logger.error("<crash>$exception>", "Profiler failed to profile!");
            exception.printStackTrace();
        }

        System.exit(-1);
    }

    public void crash(String message, Exception e) {
        crash(message, e, new String[] {});
    }

    public ProfilerSection getSection(String name) {
        for (ProfilerSection ps : sections) {
            if (ps.getName().equalsIgnoreCase(name)) {
                return ps;
            }
        }
        return null;
    }

    public ProfilerSection getCurrentSection() {
        int sectionNum = 0;
        ProfilerSection section = null;

        try {
            section = sections.get(sectionNum);
        } catch (IndexOutOfBoundsException e) {}

        try {
            while (!section.isStopped()) {
                try {
                    sectionNum++;
                    section = sections.get(sectionNum);
                } catch (IndexOutOfBoundsException e) {
                    return sections.get(sectionNum - 1);
                }
            }
        } catch (NullPointerException e) {}
        return section;
    }
}
