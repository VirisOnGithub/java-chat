package client;

import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Client {
    private Socket clientSocket;
    private boolean working = true;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner sc = new Scanner(System.in);

    public Client() {
        try {
            clientSocket = new Socket("localhost", 1234);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Enter your name: ");
            String name = sc.nextLine();
            out.println(name);

            Thread readThread = new Thread(this::readFromServer);
            Thread writeThread = new Thread(this::writeToServer);
            readThread.start();
            writeThread.start();

            readThread.join();
            writeThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readFromServer() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToServer() {
        while(working){
            if(sc.hasNextLine()){
                String message = sc.nextLine();
                out.println(message);
            }
        }
        sc.close();
    }
}