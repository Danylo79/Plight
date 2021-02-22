package dev.dankom.script;

import java.util.ArrayList;
import java.util.List;

public class Token<T> {
    private Type type;
    private List<T> value;

    public Token(Type type) {
        this.type = type;
        this.value = new ArrayList<>();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }
}
