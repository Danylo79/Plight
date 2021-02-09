package dev.dankom.logger.type;

import dev.dankom.util.general.ColorUtil;

public enum LogLevel {

    INFO("INFO", ""),
    ERROR("ERROR", ColorUtil.ANSI_RED),
    FATAL("FATAL", ColorUtil.ANSI_RED),
    IMPORTANT("IMPORTANT", ColorUtil.ANSI_MAGENTA),
    TEST("TEST", ColorUtil.ANSI_GREEN),
    WARNING("WARNING", ColorUtil.ANSI_YELLOW)
    ;

    private final String name;
    private final String color;

    LogLevel(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
