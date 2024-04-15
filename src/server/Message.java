package server;

import java.net.Socket;

public class Message {
    private String text;
    private Socket sender;

    public Message(String text, Socket sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public Socket getSender() {
        return sender;
    }
}
