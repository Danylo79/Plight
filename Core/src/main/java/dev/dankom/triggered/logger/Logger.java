package dev.dankom.triggered.logger;

public class Logger {

    private String name;

    public Logger(String projectName) {
        this.name = projectName;
    }

    public void log(Object msg) {
        log(name, msg);
    }

    public void log(String thread, Object msg) {
        System.out.println("[" + thread + "] " + msg);
    }
}
