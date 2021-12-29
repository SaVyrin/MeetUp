package client.server.messages;

import acquaintance.Person;
import fxml.controllers.SceneController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientMessageHandler extends AbstractMessageHandler {

    public ClientMessageHandler(Socket socket) {
        super(socket);
    }

    public void sendMessage(Message message) {
        System.out.println("sent to server");
        System.out.println(message);

        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to server");
            closeEverything();
        }
    }

    public void receiveMessage(VBox vBoxOnline, VBox vBoxPending, VBox vBoxFriends, ImageView avatar, Text description) {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    System.out.println("received from server");
                    Message messageFromServer = (Message) objectInputStream.readObject();

                    Command command = messageFromServer.getCommand();

                    if (command.equals(Command.ONLINE)) {
                        List<String> peopleOnline = messageFromServer.getOnlinePeople();
                        SceneController.addContentToVBox(peopleOnline, Command.ONLINE.getCommandString(), vBoxOnline);
                    }
                    if (command.equals(Command.FRIEND_REQ)) {
                        List<String> pendingRequests = messageFromServer.getPendingFriendRequests();
                        SceneController.addContentToVBox(pendingRequests, Command.FRIEND_REQ.getCommandString(), vBoxPending);
                    }
                    if (command.equals(Command.FRIENDS)) {
                        List<String> friends = messageFromServer.getFriends();
                        SceneController.addContentToVBox(friends, Command.FRIENDS.getCommandString(), vBoxFriends);
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from a server");
                    closeEverything();
                    break;
                }
            }
        }).start();
    }
}
