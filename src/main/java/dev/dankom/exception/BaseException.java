package dev.dankom.exception;

public class BaseException extends Exception {
    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
