package dev.dankom.util.reflection;

import dev.dankom.type.ReflectionData;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class ReflectionUtil {

    /**
     *
     * @param dir Package to get the classes from
     * @param type Derivative to look for
     * @return All the classes that implement/extend a type
     */
    public static <T> Set<Class<? extends T>> getAllClasses(String dir, Class<T> type) {
        Reflections reflection = new Reflections(dir);
        return reflection.getSubTypesOf(type);
    }

    /**
     *
     * @param dir Package to get the classes from
     * @return All the classes
     */
    public static <T> Set<Class<?>> getAllClasses(String dir) {
        return getAllClasses(dir, Object.class);
    }

    /**
     *
     * @param dir Package to get the classes from
     * @return All the types in the package
     */
    public static <T> Set<String> getAllTypes(String dir) {
        Reflections reflection = new Reflections(dir);
        return reflection.getAllTypes();
    }

    /**
     *
     * @param m The method to extract the data from
     * @return The ReflectionData of the method
     * @see ReflectionData
     */
    public static ReflectionData getMethodData(Method m) {
        return new ReflectionData(m);
    }

    /**
     *
     * @param c The class to extract the data from
     * @return The ReflectionData of the class
     * @see ReflectionData
     */
    public static ReflectionData getClassData(Class c) {
        return new ReflectionData(c);
    }

    /**
     *
     * @param f The field to extract the data from
     * @return The ReflectionData of the field
     * @see ReflectionData
     */
    public static ReflectionData getFieldData(Field f) {
        return new ReflectionData(f);
    }
}
