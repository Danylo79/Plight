package dev.dankom.type.scanner;

import java.util.function.Consumer;

public class ConsoleScanner extends SimpleScanner {
    public ConsoleScanner(Consumer<String> read) {
        super(System.in, read);
    }
}
