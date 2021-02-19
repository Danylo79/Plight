package dev.dankom.re.encryptors;

import dev.dankom.re.RefectionEncryptor;

import java.lang.reflect.Field;

public class FieldEncryptor extends RefectionEncryptor {
    public FieldEncryptor(Field f) {
        super(f.getDeclaringClass() + "|" + f.getName(), "C");
    }
}
