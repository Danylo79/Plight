package dev.dankom.exception;

public class CallbackFailException extends Exception {
    public CallbackFailException() {
        super("Callback failed to return properly!");
    }
}
