package dev.dankom.interfaces.impl;

import dev.dankom.interfaces.runner.ArgsMethodRunner;

public class SimpleArgsMethodRunner implements ArgsMethodRunner {

    protected Object[] args = new Object[0];

    @Override
    public void setArgs(Object... args) {
        this.args = args;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public void run() {

    }
}
