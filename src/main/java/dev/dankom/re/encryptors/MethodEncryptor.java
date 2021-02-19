package dev.dankom.re.encryptors;

import dev.dankom.re.RefectionEncryptor;

import java.lang.reflect.Method;

public class MethodEncryptor extends RefectionEncryptor {
    public MethodEncryptor(Method method) {
        super(new ClassEncryptor(method.getDeclaringClass()) + "|" + method.getName(), "M");
    }
}
