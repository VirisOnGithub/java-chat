package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class clientThread extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    clientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine = in.readLine();
            if (inputLine.equals("time")) {
                out.println("Il est " + java.time.LocalTime.now());
            } else if (inputLine.equals("date")) {
                out.println("Nous sommes le " + java.time.LocalDate.now());
            }
            else if (inputLine.equals("quit")) {
                out.println("Au revoir !");
                clientSocket.close();
            } 
            else {
                out.println("Commande inconnue");
            }
            System.out.println("Message reçu de " + clientSocket.hashCode() +" : " + inputLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
