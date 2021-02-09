package dev.dankom.util.general;

import junit.framework.TestCase;

public class StringFormatterTest extends TestCase {
    public void testJoin() {
        assertEquals("foobar", StringFormatter.join("foo", "bar"));
    }
}