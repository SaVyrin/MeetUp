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
    private final Person loggedInPerson;

    public Client(Socket socket, Person loggedInPerson) {
        this.messageHandler = new ClientMessageHandler(socket);
        this.loggedInPerson = loggedInPerson;
    }

    public void sendToServer(String message, Command command) {
        Message messageToServer = new Message(loggedInPerson);
        messageToServer.setCommand(command);
        messageToServer.setMessage(message);
        messageHandler.sendMessage(messageToServer);
    }

    public void receiveFromServer(VBox friends, VBox pendingRequests, VBox onlinePeople, ImageView avatar, Text description) {
        messageHandler.receiveMessage(friends, pendingRequests, onlinePeople, avatar, description);
    }
}
