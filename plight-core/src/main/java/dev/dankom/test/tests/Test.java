package dev.dankom.test.tests;

import dev.dankom.test.RuntimeTest;

public class Test extends RuntimeTest {
    @dev.dankom.test.Test
    public void onTest() {
        logger.test("Test", "Successfully started TestRunner!");
    }
}
