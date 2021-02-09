package dev.dankom.logger;

import dev.dankom.logger.type.LogLevel;
import dev.dankom.util.general.ColorUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Deprecated
public class Logger {

    private List<String> log;

    public Logger() {
        this.log = new ArrayList<>();
    }

    public void info(String loc, Object msg) {
        log(LogLevel.INFO, loc, msg);
    }

    public void warning(String loc, Object msg) {
        log(LogLevel.WARNING, loc, msg);
    }

    public void error(String loc, Object msg) {
        log(LogLevel.ERROR, loc, msg);
    }

    public void important(String loc, Object msg) {
        log(LogLevel.IMPORTANT, loc, msg);
    }

    public void test(String loc, Object msg) {
        log(LogLevel.TEST, loc, msg);
    }

    public void fatal(String loc, Object msg) {
        log(LogLevel.FATAL, loc, msg);
    }

    public void log(LogLevel level, String loc, Object msg) {
        Date date = new Date();
        String time = "[" + date.getHours() + ":" + date.getMinutes() + "." + date.getSeconds() + "]";
        loc = "[" + loc + "]";
        String print = level.getColor() + time + " [" + level.getName() + "] " + loc + " " + msg + ColorUtil.ANSI_RESET;
        System.out.println(print);
        log.add(print);
        if (level.equals(LogLevel.FATAL)) {
            Runtime.getRuntime().exit(-1);
        }
    }

    public List<String> logs() {
        return log;
    }

    public void save(File dir) {
        File dataFile;
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yy-mm-dd");
        String formattedDate = format.format(date) + "_" + date.getHours() + "-" + date.getMinutes() + "-" + date.getSeconds();
        dir = new File("./PHB/logs");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dataFile = new File(dir, formattedDate + ".log");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            dataFile.delete();
            dataFile.createNewFile();
            List<String> toSave = new ArrayList<>();

            for (String s : logs()) {
                toSave.add(ColorUtil.stripAllColor(s));
            }

            try {
                PrintWriter pw = new PrintWriter(dataFile);
                for (String str : toSave) {
                    pw.println(str);
                }
                pw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}