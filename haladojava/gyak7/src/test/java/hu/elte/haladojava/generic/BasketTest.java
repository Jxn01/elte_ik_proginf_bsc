package hu.elte.haladojava.generic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

class BasketTest {

    static final CrimsonGold CRIMSON_GOLD = new CrimsonGold();
    static final Jonathan JONATHAN = new Jonathan();

    @Test
    void copy() {
        Basket<CrimsonGold> basket = new Basket<>();
        basket.add(CRIMSON_GOLD);
        Basket<Fruit> other = new Basket<>();

        Basket.<Apple> copy(basket, other);

        Assertions.assertEquals(2, other.size());
    }

    @Test
    void addAll() {
        List<Apple> apples = Arrays.asList(CRIMSON_GOLD, JONATHAN);
        Basket<Fruit> basket = new Basket<>();

        basket.addAll(apples);

        Assertions.assertEquals(2, basket.size());
    }

    @Test
    void forEach() {
        Basket<Apple> basket = new Basket<>();
        basket.add(CRIMSON_GOLD);
        basket.add(JONATHAN);
        Consumer<Fruit> appleConsumer = Mockito.mock(Consumer.class);

        basket.forEach(appleConsumer);

        Mockito.verify(appleConsumer).accept(CRIMSON_GOLD);
        Mockito.verify(appleConsumer).accept(JONATHAN);
        Mockito.verifyNoMoreInteractions(appleConsumer);
    }

    @Test
    void biggestThing() {
        Basket<Apple> basket = new Basket<>();
        basket.add(CRIMSON_GOLD);
        basket.add(JONATHAN);

        Comparator<Fruit> comparator = Comparator.comparing(Fruit::averageWeight);

        Assertions.assertEquals(JONATHAN, basket.biggestThing(comparator).get());
    }

    static class CrimsonGold extends Apple {
        CrimsonGold() {
            super(50);
        }
    }

    static class Jonathan extends Apple {
        Jonathan() {
            super(150);
        }
    }

    static class Banana extends Fruit {
        Banana() {
            super(200);
        }
    }

    static abstract class Apple extends Fruit {
        Apple(int averageWeight) {
            super(averageWeight);
        }
    }

    static class Fruit {
        int averageWeight;
        Fruit(int averageWeight) {
            this.averageWeight = averageWeight;
        }
        int averageWeight() {
            return averageWeight;
        }

    }
}
