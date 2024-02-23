package bead7.stream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ConsoleLogger cl = new ConsoleLogger();
        ConsoleCipherLogger ccl = new ConsoleCipherLogger();
        FileLogger fl = new FileLogger("output.txt");

        Stream stream1 = new Stream(50, 10, cl);
        Stream stream2 = new Stream(50, 10, ccl);
        Stream stream3 = new Stream(50, 10, fl);

        System.out.println("Normal stream starts.");
        stream1.startStreaming();
        System.out.println("Normal stream ended.");
        System.out.println("Caesar (+3, ASCII) stream starts.");
        stream2.startStreaming();
        System.out.println("Caesar (+3, ASCII) stream ended.");
        System.out.println("File stream starts.");
        stream3.startStreaming();
        System.out.println("File stream ended.");
    }
}

class Stream {
    private int maxStringLength;
    private int stringNumber;
    private Logger logger;

    public Stream(int maxStringLength, int stringNumber, Logger logger) {
        if (maxStringLength < 1 || stringNumber < 1 || logger.equals(null)) {
            throw new IllegalArgumentException("Illegal arguments.");
        }

        this.logger = logger;
        this.maxStringLength = maxStringLength;
        this.stringNumber = stringNumber;
    }

    public void startStreaming() {
        String dictionary = "0123456789qwertzuioplkjhgfdsayxcvbnmQWERTZUIOPLKJHGFDSAYXCVBNM";
        for (int i = 0; i < stringNumber; i++) {
            StringBuilder sb = new StringBuilder();
            int length = (int) (Math.random() * maxStringLength) + 1;
            for (int j = 0; j < length; j++) {
                int randomFromDictionary = (int) (Math.random() * dictionary.length()- 1);
                char character = dictionary.charAt(randomFromDictionary);
                sb.append(character);
            }

            logger.log(sb.toString());
        }
    }
}

abstract class Logger {
    public void log(String string) {
    }
}

class ConsoleLogger extends Logger {

    @Override
    public void log(String string) {
        System.out.println(string);
    }

    public ConsoleLogger() {
    };
}

class ConsoleCipherLogger extends Logger {
    @Override
    public void log(String string) {
        StringBuilder result = new StringBuilder();
        for (char character : string.toCharArray()) {
            int originalAlphabetPosition = character - 'a';
            int newAlphabetPosition = (originalAlphabetPosition + 3) % 26;
            char newCharacter = (char) ('a' + newAlphabetPosition);
            result.append(newCharacter);
        }
        System.out.println(result.toString());
    }

    public ConsoleCipherLogger() {
    };
}

class FileLogger extends Logger {
    private String filename;

    public FileLogger(String filename) {
        this.filename = filename;
    }

    @Override
    public void log(String string) {
        try{
            File file = new File(filename);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(string+"\n");
            fileWriter.close();
        }catch(IOException exc){
            exc.printStackTrace();
        }
    }
}