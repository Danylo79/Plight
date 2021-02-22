package dev.dankom.script;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private PlightScriptLoader loader;

    public Tokenizer(PlightScriptLoader loader) {
        this.loader = loader;
    }

    public List<Block> tokenize() {
        String input = loader.getS();
        List<Block> out = new ArrayList<>();

        String[] split = input.split(Type.END_LINE.getToken());
        for (String b : split) {
            List<Character> characters = new ArrayList<>();
            for (int i = 0; i < b.length(); i++) {
                characters.add(b.charAt(i));
            }

            String store = "";
            for (Character c : characters) {
                Type t = Type.get(Character.toString(c));
                if (Character.isLetter(c)) {
                    store += c;
                    continue;
                }
                Token token = new Token(Type.INVALID);
                if (t.equals(Type.EQUALS)) {
                    token.setType(Type.VAR);
                } else if (t.equals(Type.EQUALS_COLON)) {

                }
            }
        }

        return out;
    }

    public boolean isOpenToken(Type t) {
        return t.equals(Type.STRING_QUOTE) || t.equals(Type.OPEN_PARAMETERS);
    }
}
