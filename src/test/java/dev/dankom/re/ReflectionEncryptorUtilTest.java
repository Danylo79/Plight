package dev.dankom.re;

import junit.framework.TestCase;

public class ReflectionEncryptorUtilTest extends TestCase {

    public void testEncrypt() {
        assertEquals("33-14-111-111-114-1", ReflectionEncryptorUtil.encrypt("Hello"));
    }

    public void testDecrypt() {
        assertEquals("Hello", ReflectionEncryptorUtil.decrypt("33-14-111-111-114-1"));
    }
}