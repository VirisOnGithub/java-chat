package client;

import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Client {
    private Socket clientSocket;
    private boolean working = true;

    public Client() {
        while(working){
                try {
                clientSocket = new Socket("localhost", 1234);

                
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                Scanner sc = new Scanner(System.in);
                if(sc.hasNextLine()){
                    System.out.println("Enter a message : ");
                    String message = sc.nextLine();
                    if(message.equals("quit")){
                        working = false;
                    }
                    out.println(message);
                }
                BufferedReader in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
                System.out.println(in.readLine());
                sc.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
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