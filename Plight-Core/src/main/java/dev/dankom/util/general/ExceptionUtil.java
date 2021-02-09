package dev.dankom.util.general;

public class ExceptionUtil {
    public static void throwCompactException(String msg) {
        throwException(new Exception(msg));
    }

    public static void throwException(String msg) {
        throwException(new Exception(msg));
    }

    public static void throwCompactException(Exception exception) {
        throwException(exception);
    }

    public static void throwException(Exception exception) {
        try {
            throw exception;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
