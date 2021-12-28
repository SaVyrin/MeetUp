package client.server;

import client.server.messages.ClientMessageHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.Socket;

public class Client {

    private final ClientMessageHandler messageHandler;

    public Client(Socket socket) {
        this.messageHandler = new ClientMessageHandler(socket);
    }

    public void sendToServer(String message) {
        messageHandler.sendMessage(message);
    }

    public void receiveFromServer(VBox friends, VBox pendingRequests, VBox onlinePeople, ImageView avatar, Text description) {
        messageHandler.receiveMessage(friends, pendingRequests, onlinePeople, avatar, description);
    }
}
