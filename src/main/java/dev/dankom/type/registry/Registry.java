package dev.dankom.type.registry;

import dev.dankom.interfaces.Copyable;
import dev.dankom.interfaces.Storeable;
import dev.dankom.util.general.DataStructureAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Registry<T> implements Iterable<T>, Copyable<Registry<T>>, Storeable<T> {
    private List<T> list = new ArrayList<>();

    public Registry(List<T> registry) {
        for (T r : registry) {
            add(r);
        }
    }

    public Registry(Collection<T> registry) {
        for (T r : registry) {
            add(r);
        }
    }

    public Registry(Registry<T> registry) {
        this(registry.toList());
    }

    public int size() {
        return list.size();
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

    public boolean containsAll(@NotNull Collection<T> c) {
        return list.containsAll(c);
    }

    public boolean addAll(@NotNull Collection<? extends T> c) {
        return list.addAll(c);
    }

    public boolean removeAll(@NotNull Collection<T> c) {
        return list.removeAll(c);
    }

    public boolean retainAll(@NotNull Collection<T> c) {
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

    public T[] toArray() {
        return DataStructureAdapter.listToArray(list);
    }

    public List<T> toList() {
        return new ArrayList<>(toCollection());
    }

    public Collection<T> toCollection() {
        return new Collection<T>() {
            @Override
            public int size() {
                return Registry.this.size();
            }

            @Override
            public boolean isEmpty() {
                return Registry.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return Registry.this.contains((T) o);
            }

            @NotNull
            @Override
            public Iterator<T> iterator() {
                return Registry.this.iterator();
            }

            @NotNull
            @Override
            public Object[] toArray() {
                return Registry.this.toArray();
            }

            @NotNull
            @Override
            public <T1> T1[] toArray(@NotNull T1[] a) {
                return (T1[]) Registry.this.toArray();
            }

            @Override
            public boolean add(T t) {
                return Registry.this.add(t);
            }

            @Override
            public boolean remove(Object o) {
                return Registry.this.remove((T) o);
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return Registry.this.containsAll((Collection<T>) c);
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends T> c) {
                return Registry.this.addAll(c);
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return Registry.this.removeAll((Collection<T>) c);
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return Registry.this.retainAll((Collection<T>) c);
            }

            @Override
            public void clear() {
                Registry.this.clear();
            }
        };
    }
}
