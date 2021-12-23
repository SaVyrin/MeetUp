package client.server.messages;

import javafx.scene.control.TextArea;
import fxml.controllers.ChatController;

import java.io.*;
import java.net.Socket;

public class ClientMessageHandler extends AbstractMessageHandler {


    public ClientMessageHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("sent to server");
        System.out.println(message);

        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to server");
            closeEverything();
        }
    }

    @Override
    public void receiveMessage(TextArea textArea) {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    StringBuilder messageFromServer = new StringBuilder();
                    String read;
                    while (!(read = bufferedReader.readLine()).equals("")) {
                        messageFromServer.append(read).append("\n");
                    }

                    ChatController.addMessageToTheChat(messageFromServer.toString(), textArea);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a server");
                    closeEverything();
                    break;
                }
            }
        }).start();
    }
}
