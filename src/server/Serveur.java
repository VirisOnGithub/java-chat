package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;

public class Serveur {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int port = 1234;
    private Map<Socket, String> nameMap = new HashMap<>();

    public Serveur() {
        System.out.println("Serveur en attente de connexion");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Connexion établie avec " + clientSocket.hashCode());
                nameMap.put(clientSocket, "Client " + clientSocket.hashCode());
                Thread clientThread = new clientThread(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
