package dev.dankom.script;

import java.util.ArrayList;
import java.util.List;

public class Block {

    private List<Type> tokens = new ArrayList<>();

    public void addToken(Type t) {
        getTokens().add(t);
    }

    public List<Type> getTokens() {
        return tokens;
    }
}
