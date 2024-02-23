package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) throws IOException {
        // webserver
        int almaCounter = 0;
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            if (line.equals("alma")) {
                almaCounter++;
            }
        }
        System.out.println("almaCounter: " + almaCounter);
        out.write("Alma counter: " + almaCounter + "\r\n");
        out.flush();
        clientSocket.close();
    }
}