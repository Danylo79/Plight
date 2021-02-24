package dev.dankom.script.type.method;

public class ScriptMethodParameter {
    private final String name;
    private final String type;

    public ScriptMethodParameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ScriptMethodParameter{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
