package hu.elte.haladojava.generic;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public interface MultiMap<K, V> {

    int size();

    boolean isEmpty();

    boolean put(K key, V value);

    boolean putAll(K key, Collection<V>/* TODO what is the right generic parameter here? */ values);

    void clear();

    List<V> get(K key);

    Set<K> keySet();

    void forEach(BiConsumer<K, V>/* TODO what is the right generic parameter here? */ action);
}
