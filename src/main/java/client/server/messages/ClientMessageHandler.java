package client.server.messages;

import fxml.controllers.SceneController;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void receiveMessage(VBox vBoxOnline, VBox vBoxPending, VBox vBoxFriends) {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    System.out.println("received from server");
                    String messageFromServer = bufferedReader.readLine();

                    String[] parsed = messageFromServer.split(Command.SEPARATOR);

                    if (parsed[0].equals(Command.ONLINE.getCommandString())) {
                        List<String> peopleOnline = new ArrayList<>(Arrays.asList(parsed).subList(1, parsed.length));
                        SceneController.addContentToVBox(peopleOnline, Command.ONLINE.getCommandString(), vBoxOnline);
                    }
                    if (parsed[0].equals(Command.FRIEND_REQ.getCommandString())) {
                        List<String> pendingRequests = new ArrayList<>(Arrays.asList(parsed).subList(1, parsed.length));
                        SceneController.addContentToVBox(pendingRequests, Command.FRIEND_REQ.getCommandString(), vBoxPending);
                    }
                    if (parsed[0].equals(Command.FRIENDS.getCommandString())) {
                        List<String> friends = new ArrayList<>(Arrays.asList(parsed).subList(1, parsed.length));
                        SceneController.addContentToVBox(friends, Command.FRIENDS.getCommandString(), vBoxFriends);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a server");
                    closeEverything();
                    break;
                }
            }
        }).start();
    }

    @Override
    public void setOnlinePeople(ObservableList<String> onlinePeople) {

    }

    @Override
    public void setPendingFriendRequests(ObservableList<String> pendingFriendRequests) {

    }

    @Override
    public void setFriends(ObservableList<String> friends) {

    }

    @Override
    public void close() {
        closeEverything();
    }
}
