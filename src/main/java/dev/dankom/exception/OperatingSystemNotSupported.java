package dev.dankom.exception;

public class OperatingSystemNotSupported extends BaseException {
    public OperatingSystemNotSupported(String msg) {
        super(msg);
    }

    public OperatingSystemNotSupported(String msg, Throwable ex) {
        super(msg, ex);
    }
}
