package dev.dankom.tests;

import dev.dankom.jmanifest.JManifestGenerator;
import dev.dankom.util.general.MathUtil;

import java.io.File;

public class ClassGeneratorTest {
    public static void main(String[] args) {
        JManifestGenerator generator = new JManifestGenerator(new File("./"), MathUtil.class);
        generator.gen();
    }
}
