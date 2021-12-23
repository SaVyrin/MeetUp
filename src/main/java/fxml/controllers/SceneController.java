package fxml.controllers;

import acquaintance.Person;
import client.server.Client;
import connections.Connection;
import connections.ServerConnection;
import com.example.oop_task_1.Frame;
import exceptions.ConnectException;
import fxml.controllers.scene.controller.PersonToShowChooser;
import fxml.dialogs.AcquaintanceAlert;
import fxml.dialogs.ConnectErrorAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


public class SceneController {
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

    PersonToShowChooser personToShowChooser;

    public void setLoggedInPerson(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
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
        new AcquaintanceAlert(loggedInPerson, personToShow);
    }

    @FXML
    private void openChat() throws IOException {
        Connection<Client> connection = new ServerConnection();
        try {
            Client client = connection.connect();
            FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/chat.fxml"));
            Scene secondScene = new Scene(fxmlLoader.load(), 800, 800);

            Stage currentStage = (Stage) acquaintanceButton.getScene().getWindow();
            currentStage.setScene(secondScene);

            ChatController controller = fxmlLoader.getController();
            controller.setLoggedInPerson(client, loggedInPerson);
        } catch (ConnectException e) {
            new ConnectErrorAlert(e.getMessage());
            e.printStackTrace();
        }
    }
}