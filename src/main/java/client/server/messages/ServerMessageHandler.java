package client.server.messages;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;

public class ServerMessageHandler extends AbstractMessageHandler {

    private String chatMessages = "";

    public ServerMessageHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void sendMessage(String message) {
        try {
            bufferedWriter.write(chatMessages);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to client");
            closeEverything();
        }
    }

    @Override
    public void receiveMessage(TextArea textArea) {
        System.out.println("received from client");

        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String messageFromClient = bufferedReader.readLine();
                    chatMessages += messageFromClient + "\n";
                    sendMessage(chatMessages);

                    System.out.println(chatMessages);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a client");
                    closeEverything();
                    break;
                }
            }
        }).start();
    }
}
