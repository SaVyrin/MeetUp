package client.server;


import client.server.messages.ClientMessageHandler;
import client.server.messages.MessageHandler;

import java.net.Socket;

public class Client {

    private Socket socket;

    private MessageHandler messageHandler;

    public Client(Socket socket) {
        this.socket = socket;
        messageHandler = new ClientMessageHandler(socket);
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
}
