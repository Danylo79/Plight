package dev.dankom.util.general;

import java.util.ArrayList;
import java.util.List;

public class NameHelper {

    private static List<String> names = new ArrayList<>();

    public static String getRandomName() {
        names.clear();
        names.add("Bob");
        names.add("Joe");
        names.add("Fredrick");
        names.add("Jose");
        names.add("Karen");
        names.add("Ken");
        names.add("Fred");
        names.add("Pedro");

        return names.get(MathUtil.randInt(0, names.size() - 1));
    }
}