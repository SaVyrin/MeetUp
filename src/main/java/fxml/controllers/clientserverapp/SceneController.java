package fxml.controllers.clientserverapp;

import client.server.Client;
import client.server.messages.Command;
import fxml.dialogs.AcquaintanceAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class SceneController {
    @FXML
    private VBox friends;
    @FXML
    private VBox pendingRequests;
    @FXML
    private VBox onlinePeople;
    @FXML
    private Button leftPersonChangeButton;
    @FXML
    private Button rightPersonChangeButton;
    @FXML
    private Button acquaintanceButton;
    @FXML
    private Text description;
    @FXML
    private ImageView avatar;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
        client.receiveFromServer(onlinePeople, pendingRequests, friends, avatar, description);
    }

    @FXML
    private void leftPersonToShowChange() {
        sendMessage(Command.SHOW, "LEFT");
    }

    @FXML
    private void rightPersonToShowChange() {
        sendMessage(Command.SHOW, "RIGHT");
    }

    @FXML
    private void findFriend() {
        leftPersonChangeButton.setVisible(true);
        rightPersonChangeButton.setVisible(true);
        acquaintanceButton.setVisible(true);
        sendMessage(Command.SHOW, "FRIEND");
    }

    @FXML
    private void findCouple() {
        leftPersonChangeButton.setVisible(true);
        rightPersonChangeButton.setVisible(true);
        acquaintanceButton.setVisible(true);
        sendMessage(Command.SHOW, "COUPLE");
    }

    @FXML
    private void acquaintance() {
        sendMessage(Command.FRIEND_REQ, "");
        new AcquaintanceAlert();
    }

    @FXML
    private void logOutButton() {
        sendMessage(Command.LOG_OUT, "");
        System.exit(0);
    }

    private void sendMessage(Command command, String message) {
        client.sendToServer(command, message);
    }
}