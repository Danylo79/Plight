package dev.dankom.logger.abztract;

import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.logger.type.LogLevel;

public class AbstractLogger {
    private ILogger logger;

    public AbstractLogger(ILogger logger) {
        this.logger = logger;
    }

    public AbstractLogger() {
        this.logger = new DefaultLogger();
    }

    public void log(LogLevel level, String loc, Object msg) {
        logger.log(level, loc, msg);
    }

    public void info(String loc, Object msg) {
        logger.log(LogLevel.INFO, loc, msg);
    }

    public void error(String loc, Object msg) {
        logger.log(LogLevel.ERROR, loc, msg);
    }

    public void fatal(String loc, Object msg) {
        logger.log(LogLevel.FATAL, loc, msg);
    }

    public void important(String loc, Object msg) {
        logger.log(LogLevel.IMPORTANT, loc, msg);
    }

    public void test(String loc, Object msg) {
        logger.log(LogLevel.TEST, loc, msg);
    }

    public void warning(String loc, Object msg) {
        logger.log(LogLevel.WARNING, loc, msg);
    }
}
