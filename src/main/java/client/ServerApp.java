package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    private final ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            ServerApp server = new ServerApp(port);
            server.start();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot start the server", e);
        }
    }

    public ServerApp(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("Game server started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from: "+clientSocket.getInetAddress());
            //AppSession appSession = new AppSession(clientSocket);
            Thread t = new Thread();
            t.start();
        }
    }
}