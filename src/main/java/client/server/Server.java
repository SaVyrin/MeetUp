package client.server;

import client.server.messages.MessageHandler;
import client.server.messages.ServerMessageHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    private MessageHandler messageHandler;

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot start the server", e);
        }
    }

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void start() {
        System.out.println("Server started");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected from: " + socket.getInetAddress());

                messageHandler = new ServerMessageHandler(socket);

                ServerMessageHandler serverMessageHandler = (ServerMessageHandler) messageHandler;
                serverMessageHandler.sendMessage(serverMessageHandler.getChatMessages());
                serverMessageHandler.receiveMessage(null);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error creating connection");
            }
        }
    }
}
