package dev.dankom.jmanifest;

import dev.dankom.file.jmanifest.JManifestFile;
import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.logger.LogManager;
import dev.dankom.logger.abztract.DefaultLogger;
import dev.dankom.logger.interfaces.ILogger;
import dev.dankom.type.ReflectionData;
import dev.dankom.util.general.DataStructureAdapter;
import dev.dankom.util.reflection.ReflectionUtil;
import org.json.simple.JSONObject;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JManifestGenerator {
    private Class<?> clazz;
    private File path;
    private JManifestFile file;
    private ILogger logger;

    public JManifestGenerator(File path, Class<?> clazz) {
        this.clazz = clazz;
        this.path = path;
        this.logger = LogManager.addLogger(clazz.getSimpleName(), new DefaultLogger());

        if (new File(path, clazz.getSimpleName() + ".jmanifest").exists()) {
            try {
                logger.info("ClazzGenerator$Constructor", "Deleting old file . . .");
            } catch (Exception e) {}
            new File(path, clazz.getSimpleName() + ".jmanifest").delete();
        }
    }

    public String getMethodData(Method m) {
        ReflectionData data = ReflectionUtil.getMethodData(m);
        String out = "";
        if (data.isPublic()) {
            out = "public";
        } else if (data.isPrivate()) {
            out = "private";
        } else if (data.isProtected()) {
            out = "protected";
        }
        return out;
    }

    private String getMethodAccessData(Method m) {
        ReflectionData data = ReflectionUtil.getMethodData(m);
        String out = "";
        if (data.isStatic()) {
            out += "static, ";
        }
        if (data.isFinal()) {
            out += "final, ";
        }
        if (data.isAbstract()) {
            out += "abstract, ";
        }
        if (data.isNative()) {
            out += "native, ";
        }
        if (data.isTransient()) {
            out += "transient, ";
        }
        if (data.isVolatile()) {
            out += "volatile, ";
        }
        if (data.isStrict()) {
            out += "strict, ";
        }
        if (data.isSynchronized()) {
            out += "synchronized, ";
        }
        if (out.charAt(out.length() - 2) == ',') {
            out = out.replace(", ", "");
        }
        return out;
    }

    public void gen() {
        this.file = new JManifestFile(path, clazz.getSimpleName(), generate().build());
    }

    private JsonObjectBuilder generate() {
        JsonObjectBuilder out = new JsonObjectBuilder();
        out = addSimple(out);
        out = addConstructors(out);
        out = addFields(out);
        out = addMethods(out);
        return out;
    }

    private JsonObjectBuilder addFields(JsonObjectBuilder builder) {
        List<JSONObject> fieldBuilders = new ArrayList<>();
        for (Field f : clazz.getDeclaredFields()) {
            try {
                logger.info("ClazzGenerator$addFields", "Found field " + f.getName() + " in " + clazz.getName() + "!");
            } catch (Exception e) {}
            JsonObjectBuilder fieldBuilder = new JsonObjectBuilder();
            fieldBuilder.addKeyValuePair("name", f.getName());
            fieldBuilder.addKeyValuePair("type", f.getType().getName());
            fieldBuilder = addAnnotations(fieldBuilder, f.getDeclaredAnnotations());
            fieldBuilders.add(fieldBuilder.build());
        }
        builder.addArray("fields", fieldBuilders);
        return builder;
    }

    private JsonObjectBuilder addMethods(JsonObjectBuilder builder) {
        List<JSONObject> methodBuilders = new ArrayList<>();
        for (Method m : clazz.getDeclaredMethods()) {
            try {
                logger.info("ClazzGenerator$addMethods", "Found method " + m.getName() + " in " + clazz.getName() + "!");
            } catch (Exception e) {}
            JsonObjectBuilder methodBuilder = new JsonObjectBuilder()
                    .addKeyValuePair("name", m.getName())
                    .addKeyValuePair("data", getMethodData(m))
                    .addKeyValuePair("access_data", getMethodAccessData(m));
            builder.addKeyValuePair("methods", DataStructureAdapter.arrayToList(methodBuilder.build()));
            methodBuilder = addParameterTypes(methodBuilder, m);
            methodBuilder = addAnnotations(methodBuilder, m);
            methodBuilders.add(methodBuilder.build());
        }
        builder.addArray("methods", methodBuilders);
        return builder;
    }

    private JsonObjectBuilder addConstructors(JsonObjectBuilder builder) {
        for (Constructor c : clazz.getDeclaredConstructors()) {
            try {
                logger.info("ClazzGenerator$addConstructors", "Found constructor " + c.getName() + " in " + clazz.getName() + "!");
            } catch (Exception e) {}
            builder = addAnnotations(builder, c);
        }
        return builder;
    }

    private JsonObjectBuilder addSimple(JsonObjectBuilder builder) {
        builder.addKeyValuePair("name", clazz.getName());
        builder.addKeyValuePair("simple_name", clazz.getSimpleName());
        builder.addKeyValuePair("interface", ReflectionUtil.getClassData(clazz).isInterface());
        builder.addKeyValuePair("abstract", ReflectionUtil.getClassData(clazz).isAbstract());
        builder.addKeyValuePair("final", ReflectionUtil.getClassData(clazz).isFinal());
        builder.addKeyValuePair("public", ReflectionUtil.getClassData(clazz).isPublic());
        return builder;
    }

    public JsonObjectBuilder addAnnotations(JsonObjectBuilder builder, Annotation[] annotations) {
        for (Annotation a : annotations) {
            builder.addArray("annotations",
                    DataStructureAdapter.arrayToList(new JsonObjectBuilder()
                            .addKeyValuePair("hashcode", a.hashCode())
                            .addKeyValuePair("type", a.annotationType().getName())
                            .build()));
        }
        return builder;
    }

    public JsonObjectBuilder addAnnotations(JsonObjectBuilder builder, Executable executable) {
        return addAnnotations(builder, executable.getDeclaredAnnotations());
    }

    public JsonObjectBuilder addParameterTypes(JsonObjectBuilder builder, Class<?>[] parameterTypes) {
        for (Class<?> type : parameterTypes) {
            builder.addArray("parameterTypes",
                    DataStructureAdapter.arrayToList(new JsonObjectBuilder()
                            .addKeyValuePair("type", type.getName())
                            .addKeyValuePair("hashcode", type.hashCode())
                            .build()));
        }
        return builder;
    }

    public JsonObjectBuilder addParameterTypes(JsonObjectBuilder builder, Executable executable) {
        return addParameterTypes(builder, executable.getParameterTypes());
    }

    public void put(String key, Object value) {
        file.get().put(key, value);
        file.save();
    }

    public JManifestFile getFile() {
        return file;
    }
}
