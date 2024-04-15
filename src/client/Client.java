package client;

import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Client {
    private Socket clientSocket;

    public Client() {
        try {
            clientSocket = new Socket("localhost", 1234);

            BufferedReader in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();
            out.println(message);
            System.out.println(in.readLine());
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException{
        PrintWriter sendMess = new PrintWriter(clientSocket.getOutputStream(), true);
        sendMess.println(message);
    }

    public void getMessage() throws IOException{
        BufferedReader in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        System.out.println(in.readLine());
    }
}