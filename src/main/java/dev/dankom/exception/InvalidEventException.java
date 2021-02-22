package dev.dankom.exception;

public class InvalidEventException extends BaseException {
    public InvalidEventException(String msg) {
        super(msg);
    }

    public InvalidEventException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
