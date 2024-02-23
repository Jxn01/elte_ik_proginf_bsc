package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        // traverse and print the file hiearhcy from the /home/jxn/dolgok directory
        Path path = Path.of("/home/jxn");
        try {
            Files.walk(path).forEach(p -> {
                // if p is a directory, print it with a trailing slash
                if(Files.isDirectory(p)) {
                    System.out.println(" ".repeat(p.getNameCount()) + p.getFileName() + "/");
                }else{
                    System.out.println(" ".repeat(p.getNameCount()) +"|__"+ p.getFileName());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}