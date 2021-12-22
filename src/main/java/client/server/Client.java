package client.server;

import acquaintance.Person;
import client.server.messages.ClientMessageHandler;
import client.server.messages.MessageHandler;

import java.net.Socket;

public class Client {

    private final MessageHandler messageHandler;

    public Client(Socket socket) {
        this.messageHandler = new ClientMessageHandler(socket);
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

}
