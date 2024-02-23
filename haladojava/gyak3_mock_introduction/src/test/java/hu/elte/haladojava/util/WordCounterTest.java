package hu.elte.haladojava.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordCounterTest {

    @Test
    public void countWords() {
        FileParser fileParser = null; // TODO
        WordCounter underTest = new WordCounter(fileParser);

        int actualWords = underTest.countWords("file");

        Assertions.assertEquals(3, actualWords); // 3?
    }
}