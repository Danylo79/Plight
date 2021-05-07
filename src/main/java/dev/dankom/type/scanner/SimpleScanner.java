package dev.dankom.type.scanner;

import dev.dankom.interfaces.runner.MethodRunner;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class SimpleScanner {
    private Scanner scanner;
    private Consumer<String> read;
    private boolean running = false;

    public SimpleScanner(InputStream is, Consumer<String> read) {
        this.scanner = new Scanner(is);
        this.read = read;
    }

    public void start() {
        running = true;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                while (running) {
                    read.accept(scanner.nextLine());
                }
            }
        }, 0);
    }

    public void stop() {
        running = false;
    }
}
