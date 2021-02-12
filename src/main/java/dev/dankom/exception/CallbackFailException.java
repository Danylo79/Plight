package dev.dankom.exception;

public class CallbackFailException extends BaseException {
    public CallbackFailException() {
        super("Callback failed to return properly!");
    }
}
