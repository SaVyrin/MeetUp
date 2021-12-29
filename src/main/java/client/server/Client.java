package client.server;

import acquaintance.Person;
import client.server.messages.ClientMessageHandler;
import client.server.messages.Command;
import client.server.messages.Message;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.Socket;

public class Client {

    private final ClientMessageHandler messageHandler;

    public Client(Socket socket) {
        this.messageHandler = new ClientMessageHandler(socket);
    }

    public void sendToServer(String message, Person loggedInPerson, Command command) {
        Message messageToServer = new Message(loggedInPerson);
        messageToServer.setCommand(command);
        messageToServer.setMessage(message);
        messageHandler.sendMessage(messageToServer);
    }

    public void receiveFromServer(VBox friends, VBox pendingRequests, VBox onlinePeople, ImageView avatar, Text description) {
        messageHandler.receiveMessage(friends, pendingRequests, onlinePeople, avatar, description);
    }
}
