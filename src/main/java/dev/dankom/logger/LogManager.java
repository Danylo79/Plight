package dev.dankom.logger;

import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.logger.profiler.Profiler;

import java.util.HashMap;
import java.util.Map;

public class LogManager {
    private static Map<String, ILogger> loggers;
    private static Map<String, Profiler> profilers;

    //Logger
    public static ILogger getLogger(String name) {
        return loggers.get(name);
    }

    public static ILogger addLogger(String name, ILogger logger) {
        if (!loggers.containsKey(name)) {
            loggers.put(name, logger);
        }
        return logger;
    }

    public static void removeLogger(String name) {
        loggers.remove(name);
    }

    //Profiler
    public static Profiler getProfiler(String name) {
        return profilers.get(name);
    }

    public static Profiler addProfiler(String name, Profiler profiler) {
        if (!profilers.containsKey(name)) {
            profilers.put(name, profiler);
        }
        return profiler;
    }

    public static void removeProfiler(String name) {
        profilers.remove(name);
    }

    static {
        if (loggers == null || profilers == null) {
            loggers = new HashMap<>();
            profilers = new HashMap<>();

            addLogger("default", new DefaultLogger());
            addLogger("profiler", new DefaultLogger());
            addProfiler("default", new Profiler());
        }
    }
}
