package org.example;

import java.io.IOException;
import java.net.Socket;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        //client that communicates with webserver
        Socket socket = new Socket("localhost", 8080);
        int almaDb = 5;
        for(int i = 0; i < almaDb; i++) {
            socket.getOutputStream().write("alma\r\n".getBytes());
        }
        socket.getOutputStream().write("\r\n".getBytes());
        socket.getOutputStream().flush();

        // get response from server
        byte[] response = new byte[1000];
        int responseLength = socket.getInputStream().read(response);
        String responseString = new String(response, 0, responseLength);
        System.out.println(responseString);
    }
}