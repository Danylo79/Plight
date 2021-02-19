package dev.dankom.re;

import dev.dankom.re.encryptors.ClassEncryptor;
import dev.dankom.re.encryptors.MethodEncryptor;
import dev.dankom.type.Token;

import java.util.HashMap;
import java.util.Map;

public class ReflectionEncryptorUtil {
    public static Map<String, Integer> characters() {
        Map<String, Integer> characters = new HashMap<>();

        characters.put("a", characters.size());
        characters.put("b", characters.size());
        characters.put("c", characters.size());
        characters.put("d", characters.size());
        characters.put("e", characters.size());
        characters.put("f", characters.size());
        characters.put("g", characters.size());
        characters.put("h", characters.size());
        characters.put("i", characters.size());
        characters.put("j", characters.size());
        characters.put("k", characters.size());
        characters.put("l", characters.size());
        characters.put("m", characters.size());
        characters.put("n", characters.size());
        characters.put("o", characters.size());
        characters.put("p", characters.size());
        characters.put("q", characters.size());
        characters.put("r", characters.size());
        characters.put("s", characters.size());
        characters.put("t", characters.size());
        characters.put("u", characters.size());
        characters.put("v", characters.size());
        characters.put("w", characters.size());
        characters.put("x", characters.size());
        characters.put("y", characters.size());
        characters.put("z", characters.size());

        characters.put("A", characters.size());
        characters.put("B", characters.size());
        characters.put("C", characters.size());
        characters.put("D", characters.size());
        characters.put("E", characters.size());
        characters.put("F", characters.size());
        characters.put("G", characters.size());
        characters.put("H", characters.size());
        characters.put("I", characters.size());
        characters.put("J", characters.size());
        characters.put("K", characters.size());
        characters.put("L", characters.size());
        characters.put("M", characters.size());
        characters.put("N", characters.size());
        characters.put("O", characters.size());
        characters.put("P", characters.size());
        characters.put("Q", characters.size());
        characters.put("R", characters.size());
        characters.put("S", characters.size());
        characters.put("T", characters.size());
        characters.put("U", characters.size());
        characters.put("V", characters.size());
        characters.put("W", characters.size());
        characters.put("X", characters.size());
        characters.put("Y", characters.size());
        characters.put("Z", characters.size());

        characters.put("|", characters.size());
        characters.put("=", characters.size());
        characters.put(">", characters.size());
        characters.put("<", characters.size());
        characters.put(".", characters.size());

        characters.put("(", characters.size());
        characters.put(")", characters.size());

        characters.put("{", characters.size());
        characters.put("}", characters.size());

        characters.put("[", characters.size());
        characters.put("]", characters.size());

        return characters;
    }

    public static String encrypt(String s) {
        String out = "";
        for (int i = 0; i < s.chars().count(); i++) {
            String c = "" + s.charAt(i);
            out += getCharCaseStrict(c) + "-1";
        }
        return out;
    }

    public static String decrypt(String s) {
        String[] split = s.split("-1");
        String out = "";
        for (String sp : split) {
            System.out.println(sp);
            int i = Integer.parseInt(sp);
            String dc = "";
            for (Map.Entry me : characters().entrySet()) {
                if (me.getValue().equals(i)) {
                    dc += me.getKey();
                    break;
                }
            }
            out += dc;
        }
        return out;
    }

    public static String[] decryptMethod(String method) {
        String[] split = decrypt(method).split(Token.PIPE.token());
        String clazz = split[0];
        String methodName = split[1];
        return new String[] { clazz, methodName };
    }

    private static int getCharCaseStrict(String key) {
        for (Map.Entry me : characters().entrySet()) {
            if (me.getKey().equals(key)) {
                return (int) me.getValue();
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        try {
            MethodEncryptor me = new MethodEncryptor(ReflectionEncryptorUtil.class.getMethod("test"));
            for (String s : decryptMethod(me.encryptS())) {
                System.out.println(s);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        System.out.println("Test!");
    }
}
