package dev.dankom.exception;

public class InvalidConstructorException extends BaseException {
    public InvalidConstructorException(String msg) {
        super(msg);
    }

    public InvalidConstructorException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
