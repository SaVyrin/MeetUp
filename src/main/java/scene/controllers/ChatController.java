package scene.controllers;

import acquaintance.Person;
import client.server.Client;
import client.server.messages.MessageHandler;
import com.example.oop_task_1.Frame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageBox;
    @FXML
    private ImageView avatar;

    private Person loggedInPerson;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            client = new Client(new Socket("localhost", 9999));
            System.out.println("Connected to server");
        } catch (IOException e) {
            System.out.println("Error connecting to server");
        }

        MessageHandler messageHandler = client.getMessageHandler();
        messageHandler.receiveMessage(chatArea);
    }

    public void initPerson(Person loggedInPerson) throws MalformedURLException {
        this.loggedInPerson = loggedInPerson;

        File fileLogo = new File("C:/Users/proto/IdeaProjects/OOP_Task_1/src/main/resources/com/example/oop_task_1/" +
                loggedInPerson.getSex()
                + "Ava.jpg");
        String localUrlLogo = fileLogo.toURI().toURL().toString();
        Image imageLogo = new Image(localUrlLogo);
        avatar.setImage(imageLogo);
    }

    @FXML
    private void openAcquaintance() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("scene.fxml"));
        Scene secondScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) avatar.getScene().getWindow();
        currentStage.setScene(secondScene);

        SceneController controller = fxmlLoader.getController(); // get controller
        controller.setLoggedInPerson(loggedInPerson); // set params for controller
    }

    @FXML
    private void sendButtonAction() {
        String message = messageBox.getText();
        if (!message.isEmpty()) {
            addMessage(message, chatArea);

            MessageHandler messageHandler = client.getMessageHandler();
            messageHandler.sendMessage(message);
            messageBox.clear();
        }
    }

    public static void addMessage(String message, TextArea textArea) {
        Platform.runLater(() -> textArea.setText(message));
    }
}
