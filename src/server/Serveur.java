package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.io.InputStreamReader;

public class Serveur {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int port = 1234;

    public Serveur() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Serveur en attente de connexion");
                clientSocket = serverSocket.accept();
                Thread clientThread = new clientThread(clientSocket);
                clientThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
