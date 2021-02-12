package dev.dankom.util.general;

public class JavaUtil {
    public static long totalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public void stop(int status) {
        Runtime.getRuntime().exit(status);
    }

    public void halt(int status) {
        Runtime.getRuntime().halt(status);
    }

    public static String version() {
        return System.getProperty("java.version");
    }

    public static int getVersion() {
        String version = version();
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);
    }
}
