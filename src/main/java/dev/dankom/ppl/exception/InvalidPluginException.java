package dev.dankom.ppl.exception;

public class InvalidPluginException extends Exception {
    public InvalidPluginException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
