package dev.dankom.util.general;

public class StringFormatter {
    public static String join(String... strings) {
        String out = "";
        for (String s : strings) {
            out += s;
        }
        return out;
    }
}
