package client.server;

import client.server.messages.MessageHandler;
import client.server.messages.ServerMessageHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // todo: should transfer socket, bufferedReader, bufferedWriter
    //  into ServerMessageHandler like in ClientMessageHandler

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

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
        System.out.println("Game server started");
        while (true) {
            try {
                this.socket = serverSocket.accept();
                System.out.println("Client connected from: " + socket.getInetAddress());

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                messageHandler = new ServerMessageHandler(socket, bufferedReader, bufferedWriter);

                ServerMessageHandler serverMessageHandler = (ServerMessageHandler) messageHandler;
                serverMessageHandler.sendMessage(serverMessageHandler.getChatMessages());
                serverMessageHandler.receiveMessage(null);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error creating server");
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }

    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            System.out.println("disconnected");
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
