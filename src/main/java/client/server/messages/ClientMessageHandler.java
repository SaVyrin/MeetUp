package client.server.messages;

import javafx.scene.control.TextArea;
import scene.controllers.ChatController;

import java.io.*;
import java.net.Socket;

public class ClientMessageHandler implements MessageHandler {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ClientMessageHandler(Socket socket) {
        this.socket = socket;
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating MessageHandler");
        }
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
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void receiveMessage(TextArea textArea) {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    System.out.println("received from server");

                    StringBuilder messageFromServer = new StringBuilder();
                    String read;
                    while (!(read = bufferedReader.readLine()).equals("")) {
                        messageFromServer.append(read).append("\n");
                    }
                    ChatController.addMessage(messageFromServer.toString(), textArea);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a server");
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
            System.out.println("Error closingEverything");
        }
    }
}
