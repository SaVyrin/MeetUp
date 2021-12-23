package fxml.controllers;

import acquaintance.Person;
import client.server.Client;
import client.server.messages.MessageHandler;
import com.example.oop_task_1.Frame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ChatController {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageBox;
    @FXML
    private ImageView avatar;

    private Client client;

    private Person loggedInPerson;

    public void setLoggedInPerson(Client client, Person loggedInPerson) throws MalformedURLException {
        this.client = client;
        this.loggedInPerson = loggedInPerson;

        MessageHandler messageHandler = client.getMessageHandler();
        messageHandler.receiveMessage(chatArea);

        File fileLogo = new File(
                "C:/Users/proto/IdeaProjects/OOP_Task_1/src/main/resources/com/example/oop_task_1/images/" +
                        loggedInPerson.getSex()
                        + "Ava.jpg");
        String localUrlLogo = fileLogo.toURI().toURL().toString();
        Image imageLogo = new Image(localUrlLogo);
        avatar.setImage(imageLogo);
    }

    @FXML
    private void sendMessage() {
        String message = messageBox.getText();
        if (!message.isEmpty()) {
            MessageHandler messageHandler = client.getMessageHandler();
            messageHandler.sendMessage(message);
            messageBox.clear();
        }
    }

    public static void addMessageToTheChat(String message, TextArea textArea) {
        Platform.runLater(() -> textArea.setText(message));
    }

    @FXML
    private void openAcquaintance() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/scene.fxml"));
        Scene secondScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) avatar.getScene().getWindow();
        currentStage.setScene(secondScene);

        SceneController controller = fxmlLoader.getController();
        controller.setLoggedInPerson(loggedInPerson);
    }
}
