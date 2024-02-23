package hu.elte.haladojava.generic;

import java.util.*;
import java.util.function.BiConsumer;

public class HashMultiMap<K, V> implements MultiMap<K, V> {

    private final Map<K, List<V>> backingMap = new HashMap<>();

    @Override
    public int size() {
        return Math.toIntExact(
                backingMap.values().stream().flatMap(List::stream).count());
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public boolean put(K key, V value) {
        List<V> values = backingMap.get(key);
        if (values == null) {
            List<V> newValue = new ArrayList<>();
            newValue.add(value);
            backingMap.put(key, newValue);
        } else {
            values.add(value);
            backingMap.put(key, values);
        }
        return false;
    }

    @Override
    public boolean putAll(K key, Collection<V> values) {
        values.forEach(v -> put(key, v));
        return false;
    }

    @Override
    public void clear() {
        backingMap.clear();
    }

    @Override
    public List<V> get(K key) {
        return backingMap.getOrDefault(key, List.of());
    }

    @Override
    public Set<K> keySet() {
        return backingMap.keySet();
    }

    @Override
    public void forEach(BiConsumer<K, V> action) {
        backingMap.forEach(
                (k, vs) -> vs.forEach(
                        v -> action.accept(k, v)));
    }
}
