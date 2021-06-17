package dev.dankom.jna.type;

import com.sun.jna.Platform;

public enum OperatingSystem {
    UNSPECIFIED(-1),
    MAC(0),
    LINUX(1),
    WINDOWS(2),
    SOLARIS(3),
    FREEBSD(4),
    OPENBSD(5),
    WINDOWSCE(6);

    private int id;

    OperatingSystem(int id) {
        this.id = id;
    }

    public static final OperatingSystem get(int id) {
        for (OperatingSystem op : values()) if (op.id == id) return op;
        return UNSPECIFIED;
    }

    public static final OperatingSystem get() {
        if (Platform.isMac()) {
            return OperatingSystem.MAC;
        } else if (Platform.isLinux()) {
            return OperatingSystem.LINUX;
        } else if (Platform.isWindowsCE()) {
            return OperatingSystem.WINDOWSCE;
        } else if (Platform.isWindows()) {
            return OperatingSystem.WINDOWS;
        } else if (Platform.isSolaris()) {
            return OperatingSystem.SOLARIS;
        } else if (Platform.isFreeBSD()) {
            return OperatingSystem.FREEBSD;
        } else if (Platform.isOpenBSD()) {
            return OperatingSystem.OPENBSD;
        }
        return OperatingSystem.UNSPECIFIED;
    }
}
