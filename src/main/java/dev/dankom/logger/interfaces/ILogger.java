package dev.dankom.logger.interfaces;

import dev.dankom.logger.type.LogLevel;

public interface ILogger {
    void log(LogLevel level, String loc, Object msg);

    default void info(String loc, Object msg) {
        log(LogLevel.INFO, loc, msg);
    }

    default void error(String loc, Object msg) {
        log(LogLevel.ERROR, loc, msg);
    }

    default void fatal(String loc, Object msg) {
        log(LogLevel.FATAL, loc, msg);
    }

    default void important(String loc, Object msg) {
        log(LogLevel.IMPORTANT, loc, msg);
    }

    default void debug(String loc, Object msg) {
        log(LogLevel.TEST, loc, msg);
    }

    default void warning(String loc, Object msg) {
        log(LogLevel.WARNING, loc, msg);
    }
}
