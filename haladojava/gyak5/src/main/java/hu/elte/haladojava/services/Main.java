package hu.elte.haladojava.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> mohamed = Files.readAllLines(Paths.get("Data8317.csv"));

    }
}
