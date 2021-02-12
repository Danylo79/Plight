package dev.dankom.agent.type;

import dev.dankom.agent.AgentLoader;
import dev.dankom.agent.type.interfaces.IAgent;
import dev.dankom.exception.InvalidConstructorException;
import dev.dankom.util.general.DataStructureAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Agent implements IAgent {
    private AgentLoader parent;
    private Class<?> clazz;

    public Agent(AgentLoader parent, Class<?> clazz) {
        this.parent = parent;
        this.clazz = clazz;
    }

    public Agent(Class<?> clazz) {
        this(null, clazz);
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
    public void run() throws InvalidConstructorException {
        try {
            getClazz().newInstance();
        } catch (IllegalAccessException e) {
            throw new InvalidConstructorException("A Agent constructor can not have parameters if it is called by the run() or runSilent() method!");
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(Class<?>[] argTypes, Object[] args) throws InvalidConstructorException {
        try {
            getClazz().getDeclaredConstructor(argTypes).newInstance(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(Object[] args) throws InvalidConstructorException {
        List<Class<?>> argTypes = new ArrayList<>();
        for (Object o : args) {
            argTypes.add(o.getClass());
        }

        try {
            getClazz().getDeclaredConstructor(DataStructureAdapter.listToArray(argTypes)).newInstance(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    //
}
