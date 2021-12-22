package client.server.messages;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;

public class ServerMessageHandler implements MessageHandler {

    private final Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String chatMessages = "";

    public String getChatMessages() {
        return chatMessages;
    }

    public ServerMessageHandler(Socket socket) {
        this.socket = socket;
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
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

    private void closeEverything() {
        try {
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
            System.out.println("Error closing everything");
        }
    }
}
