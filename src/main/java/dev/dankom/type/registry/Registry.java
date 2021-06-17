package dev.dankom.type.registry;

import dev.dankom.annotation.json.JsonSerializable;
import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.interfaces.Copyable;
import dev.dankom.interfaces.Storeable;
import dev.dankom.interfaces.Temporary;
import dev.dankom.interfaces.Wrapper;
import dev.dankom.util.general.DataStructureAdapter;
import org.json.simple.JSONAware;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Registry<T> implements Iterable<T>, Copyable<Registry<T>>, Storeable<T>, Wrapper<List<T>>, Temporary, JSONAware, JsonSerializable {
    private List<T> list;
    private UUID id;

    public Registry() {
        open();
    }

    public Registry(Collection<T> registry) {
        this();
        for (T r : registry) {
            add(r);
        }
    }

    public Registry(List<T> registry) {
        this((Collection<T>) registry);
    }

    public Registry(Registry<T> registry) {
        this(registry.toList());
    }

    public int size() {
        return list.size() - 1;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(T o) {
        return list.contains(o);
    }

    public Iterator<T> iterator() {
        return list.iterator();
    }

    public T get(int index) {
        return list.get(index);
    }

    public boolean add(T t) {
        return list.add(t);
    }

    public void insert(T t, int index) {
        list.add(index, t);
    }

    public boolean remove(T o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<T> c) {
        return list.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    public boolean removeAll(Collection<T> c) {
        return list.removeAll(c);
    }

    public boolean retainAll(Collection<T> c) {
        return list.retainAll(c);
    }

    public void clear() {
        list.clear();
    }

    public void eachItem(Consumer<T> consumer) {
        for (T o : list) {
            consumer.accept(o);
        }
    }

    public void eachIndex(Consumer<Integer> consumer) {
        for (int i = 0; i < size(); i++) {
            consumer.accept(i);
        }
    }

    public void eachBoth(BiConsumer<T, Integer> consumer) {
        for (int i = 0; i < size(); i++) {
            T o = get(i);
            consumer.accept(o, i);
        }
    }

    public Registry<T> copy() {
        return new Registry<T>(this);
    }

    public List<T> fetch() {
        return list;
    }

    public void open() {
        list = new ArrayList<>();
        id = UUID.randomUUID();
    }

    public void close() {
        list.clear();
        id = null;
    }

    public T[] toArray() {
        return DataStructureAdapter.listToArray(list);
    }

    public List<T> toList() {
        return new ArrayList<>(toCollection());
    }

    public Collection<T> toCollection() {
        return list;
    }

    public String toJSONString() {
        return new JsonObjectBuilder().addKeyValuePair("id", id.toString()).addArray("values", list).build().toJSONString();
    }
}
