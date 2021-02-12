package dev.dankom.tests.operation;

import dev.dankom.interfaces.impl.ThreadMethodRunner;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.operation.operations.ShutdownOperation;

public class ShutdownOperationTest {
    public static void main(String[] args) {
        ShutdownOperation so = new ShutdownOperation(new ThreadMethodRunner(() -> {
            System.out.println("This is purely a test!");
        }), "Test", new DefaultLogger());
    }
}
