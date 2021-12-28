package fxml.controllers;

import acquaintance.Person;
import client.server.Client;
import client.server.messages.Command;
import com.example.oop_task_1.Frame;
import fxml.controllers.scene.controller.PersonToShowChooser;
import fxml.dialogs.AcquaintanceAlert;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


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

    private Person loggedInPerson;
    private Person personToShow;

    private Client client;
    private PersonToShowChooser personToShowChooser;

    public void setLoggedInPerson(Client client, Person loggedInPerson) {
        this.client = client;
        this.loggedInPerson = loggedInPerson;

        sendMessage(Command.LOG_IN + Command.SEPARATOR + loggedInPerson.getLogin());
        client.receiveFromServer(onlinePeople, pendingRequests, friends, avatar, description);
        this.personToShowChooser = new PersonToShowChooser(loggedInPerson);
    }

    @FXML
    private void leftPersonToShowChange() throws MalformedURLException {
        personToShow = personToShowChooser.leftChanger();
        showNextPerson();
    }

    @FXML
    private void rightPersonToShowChange() throws MalformedURLException {
        personToShow = personToShowChooser.rightChanger();
        showNextPerson();
    }

    @FXML
    private void findFriend() throws MalformedURLException {
        personToShow = personToShowChooser.chooseFriends();
        leftPersonChangeButton.setVisible(true);
        rightPersonChangeButton.setVisible(true);
        acquaintanceButton.setVisible(true);
        showNextPerson();
    }

    @FXML
    private void findCouple() throws MalformedURLException {
        personToShow = personToShowChooser.chooseCouples();
        leftPersonChangeButton.setVisible(true);
        rightPersonChangeButton.setVisible(true);
        acquaintanceButton.setVisible(true);
        showNextPerson();
    }

    private void showNextPerson() throws MalformedURLException {
        String login = personToShow.getLogin();
        String name = personToShow.getName();
        String surname = personToShow.getSurname();
        int age = personToShow.getAge();
        String city = personToShow.getCity();
        String sex = personToShow.getSex();

        File fileLogo = new File("C:/Users/proto/IdeaProjects/OOP_Task_1/src/main/resources/com/example/oop_task_1/images/" + sex + "Ava.jpg");
        String localUrlLogo = fileLogo.toURI().toURL().toString();
        Image imageLogo = new Image(localUrlLogo);
        avatar.setImage(imageLogo);

        StringBuilder descriptionText = new StringBuilder();
        descriptionText.append("@"+login+"\n");
        descriptionText.append(name + " " + surname + "\n");
        descriptionText.append("Возраст: " + age + "\n");
        descriptionText.append("Город: " + city + "\n");
        descriptionText.append("Хобби: ");

        List<String> interests = personToShow.getInterests();
        for (int i = 0; i < interests.size(); i++) {
            String interest = interests.get(i);
            descriptionText.append(interest);
            if (i == interests.size() - 1) {
                descriptionText.append(".");
            } else {
                descriptionText.append(", ");
            }
        }
        description.setText(descriptionText.toString());
    }

    @FXML
    private void acquaintance() {
        sendMessage(Command.FRIEND_REQ + Command.SEPARATOR + loggedInPerson.getLogin() + "-" + personToShow.getLogin());
        new AcquaintanceAlert(loggedInPerson, personToShow);
    }

    @FXML
    private void logOutButton() throws IOException {
        sendMessage(Command.LOG_OUT + Command.SEPARATOR + loggedInPerson.getLogin());

        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/login.fxml"));
        Scene changeScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) onlinePeople.getScene().getWindow();
        currentStage.setScene(changeScene);
    }

    private void sendMessage(String message) {
        if (!message.isEmpty()) {
            client.sendToServer(message);
        }
    }

    public static void addContentToVBox(List<String> message, String type, VBox vBox) {
        Platform.runLater(() -> {
            vBox.getChildren().clear();
            for (String str : message) {
                Label label = new Label(type + str);
                label.setStyle("-fx-background-color : #99ff99;");
                vBox.getChildren().add(label);
            }
        });
    }
}