import org.junit.jupiter.api.Test;
import pst8ra.javabead.ClassDumper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassDumperTest {
    @Test
    public void dumpTest1() {
        String expected = """
                public class TestClass1 extends java.lang.Thread implements java.lang.Runnable, java.io.Serializable {
                \tprivate static final int CONST;
                \ttransient volatile boolean flag;
                \tprotected java.lang.Object obj;
                \tpublic int n;
                                
                \tpublic static void printConst(...) { /* method body */ }
                }
                """;
        String actual = ClassDumper.dump(TestClass1.class);
        assertEquals(expected.strip(), actual.strip());
    }

    @Test
    public void dumpTest2() {
        String expected = """
                public class TestClass2 extends java.lang.Thread {
                \tprivate static final int CONST;
                \ttransient volatile boolean flag;
                \tprotected java.lang.Object obj;
                \tpublic int n;
                                
                \tpublic static void printConst(...) { /* method body */ }
                }""";
        String actual = ClassDumper.dump(TestClass2.class);
        assertEquals(expected, actual);
    }

    @Test
    public void dumpTest3() {
        String expected = """
                class TestClass3 extends java.lang.Object {
                \tprivate static final int CONST;
                \ttransient volatile boolean flag;
                \tprotected java.lang.Object obj;
                \tpublic int n;
                                
                \tpublic static void printConst(...) { /* method body */ }
                }""";
        String actual = ClassDumper.dump(TestClass3.class);
        assertEquals(expected, actual);
    }
}
