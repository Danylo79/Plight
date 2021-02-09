package dev.dankom.type;

import lombok.AccessLevel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionData {

    private final int modifiers;

    public ReflectionData(Method m) {
        this.modifiers = m.getModifiers();
    }

    public ReflectionData(Class c) {
        this.modifiers = c.getModifiers();
    }

    public ReflectionData(Field f) {
        this.modifiers = f.getModifiers();
    }

    public boolean isPublic() {
        return Modifier.isPublic(modifiers);
    }
    
    public boolean isPrivate() {
        return Modifier.isPrivate(modifiers);
    }

    public boolean isProtected() {
        return Modifier.isProtected(modifiers);
    }

    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }

    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }

    public boolean isSynchronized() {
        return Modifier.isSynchronized(modifiers);
    }

    public boolean isVolatile() {
        return Modifier.isVolatile(modifiers);
    }

    public boolean isTransient() {
        return Modifier.isTransient(modifiers);
    }

    public boolean isNative() {
        return Modifier.isNative(modifiers);
    }

    public boolean isInterface() {
        return Modifier.isInterface(modifiers);
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(modifiers);
    }

    public boolean isStrict() {
        return Modifier.isStrict(modifiers);
    }

    public AccessLevel getAccessLevel() {
        if (isPublic()) {
            return AccessLevel.PUBLIC;
        } else if (isPrivate()) {
            return AccessLevel.PRIVATE;
        } else {
            return AccessLevel.PROTECTED;
        }
    }
}
