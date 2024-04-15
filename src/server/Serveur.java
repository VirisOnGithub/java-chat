package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Serveur {
    private ServerSocket serverSocket;
    private int port = 1234;
    private Map<Socket, PrintWriter> clientMap = new HashMap<>();

    public Serveur() {
        System.out.println("Serveur en attente de connexion");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion établie avec " + clientSocket.hashCode());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clientMap.put(clientSocket, out);
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        while (true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    for (PrintWriter out : clientMap.values()) {
                        out.println(inputLine);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}