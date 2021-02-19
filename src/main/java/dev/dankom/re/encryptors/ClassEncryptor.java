package dev.dankom.re.encryptors;

import dev.dankom.re.RefectionEncryptor;

public class ClassEncryptor extends RefectionEncryptor {
    public ClassEncryptor(Class<?> clazz) {
        super(clazz.getName(), "C");
    }
}
