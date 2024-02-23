package hu.elte.haladojava.reflection;

// it does not have 100% test coverage, please feel free to extend it
public class StackTest {

    @TestCase(expected = IllegalStateException.class)
    public void constructor_negativeCapacity() {
        new Stack<>(-1);
    }

    @TestCase
    public void push_oneElement() throws Exception {
        Stack<Integer> stack = new Stack<>(10);
        stack.push(50);

        Asserts.assertFalse(stack.isEmpty());
    }

    @TestCase(expected = StackOverflowException.class)
    public void push_stackIsFull() throws Exception {
        Stack<Integer> stack = new Stack<>(1);
        stack.push(6);

        stack.push(100);
    }

    @TestCase
    public void pop_removesLastElement() throws Exception {
        Stack<Integer> stack = new Stack<>(1);
        stack.push(6);
        Asserts.assertFalse(stack.isEmpty());
        Asserts.assertTrue(stack.isFull());

        int lastElement = stack.pop();

        Asserts.assertEquals(6, lastElement);
        Asserts.assertTrue(stack.isEmpty());
        Asserts.assertFalse(stack.isFull());
    }

    @TestCase
    public void generic() throws Exception {
        Stack<String> stack = new Stack<>(1);
        stack.push("pearl");

        Asserts.assertTrue(stack.pop() instanceof String);
    }
}