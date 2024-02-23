package hu.elte.haladojava.generic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.function.BiConsumer;

class HashMultiMapTest {

    static final Person JOHN = new Person("John");
    static final Person JANE = new Person("Jane");

    @Test
    void size() {
        MultiMap<Person, Pet> multiMap = newHashMultiMap();
        multiMap.put(JOHN, new Pet("Charlie"));
        multiMap.put(JOHN, new Pet("Murphy"));
        multiMap.put(JANE, new Pet("Daisy"));

        Assertions.assertEquals(3, multiMap.size());
    }

    @Test
    void isEmpty() {
        MultiMap<String, Integer> multiMap = newHashMultiMap();

        Assertions.assertTrue(multiMap.isEmpty());
    }

    @Test
    void isEmpty_notEmpty() {
        MultiMap<Person, Pet>  multiMap = newHashMultiMap();
        multiMap.put(JOHN, new Pet("Charlie"));

        Assertions.assertFalse(multiMap.isEmpty());
    }

    @Test
    void put() {
        MultiMap<Person, Pet>  multiMap = newHashMultiMap();
        Dog charlie = new Dog("Charlie");

        multiMap.put(JOHN, charlie);

        Assertions.assertEquals(1, multiMap.size());
        Assertions.assertEquals(List.of(charlie), multiMap.get(JOHN));
    }

    @Test
    void putAll() {
        MultiMap<Person, Pet>  multiMap = newHashMultiMap();
        Dog charlie = new Dog("Charlie");
        Dog hugo = new Dog("Hugo");

        multiMap.putAll(JOHN, Arrays.asList(charlie, hugo));

        Assertions.assertEquals(2, multiMap.size());
        Assertions.assertEquals(Arrays.asList(charlie, hugo), multiMap.get(JOHN));
    }

    @Test
    void clear() {
        MultiMap<Person, Pet>  multiMap = newHashMultiMap();
        multiMap.put(JOHN, new Pet("Charlie"));

        multiMap.clear();

        Assertions.assertTrue(multiMap.isEmpty());
    }

    @Test
    void get_missingKey() {
        MultiMap<Person, String> multiMap = newHashMultiMap();

        Assertions.assertEquals(Collections.emptyList(), multiMap.get(JOHN));
    }

    @Test
    void keySet() {
        MultiMap<Person, Pet>  multiMap = newHashMultiMap();
        multiMap.put(JOHN, new Pet("Charlie"));
        multiMap.put(JOHN, new Pet("Murphy"));
        multiMap.put(JANE, new Pet("Daisy"));

        Assertions.assertEquals(new HashSet<>(Arrays.asList(JOHN, JANE)), multiMap.keySet());
    }

    @Test
    void forEach() {
        MultiMap<Person, Animal>  multiMap = newHashMultiMap();
        Pet charlie = new Pet("Charlie");
        Pet murphy = new Pet("Murphy");
        Pet daisy = new Pet("Daisy");
        multiMap.put(JOHN, charlie);
        multiMap.put(JOHN, murphy);
        multiMap.put(JANE, daisy);
        BiConsumer<Person, Animal> biConsumer = Mockito.mock(BiConsumer.class);

        multiMap.forEach(biConsumer);

        Mockito.verify(biConsumer).accept(JOHN, charlie);
        Mockito.verify(biConsumer).accept(JOHN, murphy);
        Mockito.verify(biConsumer).accept(JANE, daisy);
        Mockito.verifyNoMoreInteractions(biConsumer);
    }

    private <K, V> MultiMap<K, V> newHashMultiMap() {
        return new HashMultiMap<>();
    }

    static class Person extends IdentifiableByName {
        Person(String name) {
            super(name);
        }
    }

    static class Dog extends Pet {
        Dog(String name) {
            super(name);
        }
    }

    static class Pet extends Animal {
        Pet(String name) {
            super(name);
        }
    }

    static class Animal extends IdentifiableByName {
        Animal(String name) {
            super(name);
        }
    }

    static class IdentifiableByName {
        final String name;
        IdentifiableByName(String name) {
            this.name = name;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IdentifiableByName that = (IdentifiableByName) o;
            return Objects.equals(name, that.name);
        }
        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
