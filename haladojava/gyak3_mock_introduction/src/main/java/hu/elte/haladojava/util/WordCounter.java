package hu.elte.haladojava.util;

import java.util.List;

public class WordCounter {

    private final FileParser fileParser;

    public WordCounter(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    public int countWords(String file) {
        List<String> lines = fileParser.lines(file);
        int words = 0;
        for (String line : lines) {
            words += line.split(" ").length;
        }
        return words;
    }
}
