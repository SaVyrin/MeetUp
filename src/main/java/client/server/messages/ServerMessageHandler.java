package client.server.messages;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class ServerMessageHandler implements MessageHandler {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String chatMessages = "";

    public String getChatMessages() {
        return chatMessages;
    }

    public ServerMessageHandler(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        this.socket = socket;
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("sent to client");

        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("received");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to client");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void receiveMessage(TextArea textArea) {
        System.out.println("received from client");

        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String messageFromClient = bufferedReader.readLine();
                    //ChatController.addMessage(messageFromClient, textArea);
                    chatMessages += messageFromClient + "\n";
                    sendMessage(chatMessages);
                    System.out.println(messageFromClient);
                    System.out.println(chatMessages);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a client");
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }
            }
        }).start();
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
        }
    }
}
