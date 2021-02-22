package dev.dankom.tests;

import dev.dankom.clazz.ClassManifestGenerator;
import dev.dankom.util.general.MathUtil;

import java.io.File;

public class ClassGeneratorTest {
    public static void main(String[] args) {
        ClassManifestGenerator generator = new ClassManifestGenerator(new File("./"), MathUtil.class);
        generator.gen();
    }
}
