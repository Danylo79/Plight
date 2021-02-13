package dev.dankom.agent.type;

import dev.dankom.agent.AgentLoader;
import dev.dankom.agent.type.interfaces.IAgent;
import dev.dankom.agent.type.wrappers.AgentAnnotation;
import dev.dankom.agent.type.wrappers.AgentField;
import dev.dankom.agent.type.wrappers.AgentMethod;
import dev.dankom.exception.InvalidConstructorException;
import dev.dankom.type.ReflectionData;
import dev.dankom.util.reflection.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Agent<T> implements IAgent<T> {
    private AgentLoader parent;
    private Class<?> clazz;

    public Agent(AgentLoader parent, Class<?> clazz) {
        this.parent = parent;
        this.clazz = clazz;
    }

    public Agent(Class<?> clazz) {
        this(null, clazz);
    }

    public Agent(String className) throws ClassNotFoundException {
        this(Class.forName(className));
    }

    @Override
    public boolean isFromAgentLoader() {
        return parent != null;
    }

    @Override
    public List<AgentAnnotation> annotations() {
        List<AgentAnnotation> out = new ArrayList<>();
        for (Annotation a : getClazz().getAnnotations()) {
            out.add(new AgentAnnotation(this, a));
        }
        return out;
    }

    @Override
    public List<AgentField> fields() {
        List<AgentField> out = new ArrayList<>();
        for (Field f : getClazz().getDeclaredFields()) {
            out.add(new AgentField(this, f));
        }
        return out;
    }

    @Override
    public List<AgentMethod> methods() {
        List<AgentMethod> out = new ArrayList<>();
        for (Method m : getClazz().getDeclaredMethods()) {
            out.add(new AgentMethod(this, m));
        }
        return out;
    }

    @Override
    public AgentLoader getParent() {
        return parent;
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    //Run Methods
    @Override
    public T newInstance() throws InvalidConstructorException {
        try {
            return (T) getClazz().newInstance();
        } catch (IllegalAccessException e) {
            throw new InvalidConstructorException("A Agent constructor can not have parameters if it is called by the run() or runSilent() method!");
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T newInstance(Class<?>[] argTypes, Object[] args) {
        try {
            return (T) getClazz().getDeclaredConstructor(argTypes).newInstance(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T newInstance(Object[] args) {
        List<Class<?>> argTypes = new ArrayList<>();
        for (Object o : args) {
            argTypes.add(o.getClass());
        }

        try {
            Constructor<?> ctor = getClazz().getDeclaredConstructor(getParTypes(argTypes.toArray()));
            return (T) ctor.newInstance(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    //

    public Class[] getParTypes(Object[] argTypes) {
        List<Class<?>> out = new ArrayList<>();
        for (Object o : argTypes) {
            out.add((Class<?>) o);
        }
        Class<?>[] parTypes = new Class[out.size()];
        for (int i = 0; i < out.size(); i++) {
            parTypes[i] = out.get(i);
        }
        return parTypes;
    }

    public ReflectionData getData() {
        return ReflectionUtil.getClassData(getClazz());
    }
}
