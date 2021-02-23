package dev.dankom.script;

public class ScriptHelper {
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBool(String s) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }
}
