package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Serveur {
    private ServerSocket serverSocket;
    private int port = 1234;
    private Map<Socket, PrintWriter> clientMap = new HashMap<>();
    private Map<Socket, String> clientNames = new HashMap<>();
    private Queue<Message> messages = new LinkedList<>();

    public Serveur() {
        System.out.println("Serveur en attente de connexion");
        try {
            serverSocket = new ServerSocket(port);
            Thread writeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    writeToClients();
                }
            });
            writeThread.start();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion établie avec " + clientSocket.hashCode());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clientMap.put(clientSocket, out);
                Thread readThread = new Thread(() -> readFromClient(clientSocket));
                readThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientName = in.readLine(); // Lire le nom du client
            clientNames.put(clientSocket, clientName);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                synchronized (messages) {
                    messages.add(new Message(inputLine, clientSocket));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToClients() {
        while (true) {
            synchronized (messages) {
                Message message = messages.poll();
                if (message != null) {
                    Collection<PrintWriter> outputs;
                    synchronized (clientMap) {
                        outputs = new ArrayList<>(clientMap.values());
                    }
                    for (Map.Entry<Socket, PrintWriter> entry : clientMap.entrySet()) {
                        if (!entry.getKey().equals(message.getSender())) {
                            entry.getValue().println(clientNames.get(message.getSender()) + " : " + message.getText());
                        }
                    }
                }
            }
        }
    }
}