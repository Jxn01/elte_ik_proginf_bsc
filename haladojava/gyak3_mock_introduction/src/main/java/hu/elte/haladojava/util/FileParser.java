package hu.elte.haladojava.util;

import java.util.List;

public interface FileParser {

    List<String> lines(String file);

    List<String> words(String line);
}
