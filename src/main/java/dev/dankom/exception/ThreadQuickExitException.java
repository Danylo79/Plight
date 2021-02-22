package dev.dankom.exception;

public class ThreadQuickExitException extends BaseException {
    public ThreadQuickExitException(String msg) {
        super(msg);
    }

    public ThreadQuickExitException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
