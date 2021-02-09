package dev.dankom.util.general.args;

import java.util.ArrayList;
import java.util.List;

public class ArgParser {

    public ArgList defaultArgs = new ArgList();

    public void addArg(Arg arg) {
        defaultArgs.add(arg);
    }

    public ArgList parseArgs(String[] args) {
        ArgList out = defaultArgs.clone();
        int growCount = 2;
        for (int i = 0; i < args.length; i += growCount) {
            if (out.get(args[i]) != null) {
                if (out.get(args[i]).isFlag()) {
                    out.get(args[i]).setValue(!(boolean)out.get(args[i]).getValue());
                    growCount = 1;
                } else {
                    out.get(args[i]).setValue(args[i + 1]);
                    growCount = 2;
                }
            } else {
                continue;
            }
        }
        checkArgs(out);
        return out;
    }

    public void checkArgs(ArgList out) {
        for (Arg a : out.args) {
            if (a.isRequired() && a.getValue() == defaultArgs.get(a.getName())) {
                try {
                    throw new Exception("Required argument " + a.getName() + " is not present!");
                } catch (Exception e) {
                    Runtime.getRuntime().exit(-1);
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Arg<VALUE> {
        private final String name;
        private VALUE value;
        private final VALUE defaultValue;
        private final boolean flag;
        private final boolean required;

        public Arg(String name, VALUE defaultValue, boolean flag, boolean required) {
            this.name = name;
            this.defaultValue = defaultValue;
            this.value = defaultValue;
            this.flag = flag;
            this.required = required;
        }

        public String getName() {
            return name;
        }

        public VALUE getValue() {
            return value;
        }

        public void setValue(VALUE value) {
            this.value = value;
        }

        public VALUE getDefaultValue() {
            return defaultValue;
        }

        public boolean isFlag() {
            return flag;
        }

        public boolean isRequired() {
            return required;
        }
    }

    public static class ArgList {
        private List<Arg> args;

        public ArgList() {
            args = new ArrayList<>();
        }

        public void add(Arg arg) {
            args.add(arg);
        }

        public void set(String name, Object value) {
            for (Arg a : args) {
                if (a.getName().equalsIgnoreCase(name)) {
                    a.setValue(value);
                } else {
                    continue;
                }
            }
        }

        public Arg get(String name) {
            for (Arg a : args) {
                if (a.getName().equalsIgnoreCase(name)) {
                    return a;
                } else {
                    continue;
                }
            }
            return null;
        }

        public ArgList clone() {
            ArgList out = new ArgList();
            for (Arg a : args) {
                out.add(a);
            }
            return out;
        }
    }
}
