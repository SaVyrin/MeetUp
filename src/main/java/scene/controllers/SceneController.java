package scene.controllers;

import acquaintance.Finder;
import acquaintance.People;
import acquaintance.Person;
import client.ClientApp;
import com.example.oop_task_1.Frame;
import javafx.event.ActionEvent;
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
    private Button leftPersonChanger;
    @FXML
    private Button rightPersonChanger;
    @FXML
    private Button acquaintance;
    @FXML
    private Text description;
    @FXML
    private ImageView avatar;

    private final List<Person> people = People.generatePeopleList();
    private List<Person> peopleToShow = People.generatePeopleList();
    private int currShownPersonIndex = 0;

    private Person loggedInPerson;

    @FXML
    private void leftPersonToShowChanger() throws MalformedURLException {
        if (currShownPersonIndex == 0) {
            return;
        }
        currShownPersonIndex--;
        nextPersonToShow();
    }

    @FXML
    private void rightPersonToShowChanger() throws MalformedURLException {
        if (currShownPersonIndex == peopleToShow.size() - 1) {
            return;
        }
        currShownPersonIndex++;
        nextPersonToShow();
    }

    @FXML
    private void findFriend() throws MalformedURLException {
        peopleToShow = Finder.findFriend(people, loggedInPerson);
        leftPersonChanger.setVisible(true);
        rightPersonChanger.setVisible(true);
        acquaintance.setVisible(true);
        currShownPersonIndex = 0;
        nextPersonToShow();
    }

    @FXML
    private void findCouple() throws MalformedURLException {
        peopleToShow = Finder.findCouple(people, loggedInPerson);
        leftPersonChanger.setVisible(true);
        rightPersonChanger.setVisible(true);
        acquaintance.setVisible(true);
        currShownPersonIndex = 0;
        nextPersonToShow();
    }

    @FXML
    public void acquaintance() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("modal.fxml"));
        Scene modalScene = new Scene(fxmlLoader.load(), 300, 200);

        ModalController controller = fxmlLoader.getController(); // get controller
        controller.setAcquaintanceText(loggedInPerson, peopleToShow.get(currShownPersonIndex));

        Stage stage = new Stage();
        stage.setScene(modalScene);
        stage.setTitle("My modal window");
        stage.show();
    }

    @FXML
    public void openChat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("chat.fxml"));
        Scene secondScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) acquaintance.getScene().getWindow();
        currentStage.setScene(secondScene);

        ChatController controller = fxmlLoader.getController(); // get controller
        controller.initPerson(loggedInPerson); // set params for controller
    }

    public void setLoggedInPerson(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }

    private void nextPersonToShow() throws MalformedURLException {
        Person personToShow = peopleToShow.get(currShownPersonIndex);

        String name = personToShow.getName();
        String surname = personToShow.getSurname();
        int age = personToShow.getAge();
        String city = personToShow.getCity();
        String sex = personToShow.getSex();

        File fileLogo = new File("C:/Users/proto/IdeaProjects/OOP_Task_1/src/main/resources/com/example/oop_task_1/" + sex + "Ava.jpg");
        String localUrlLogo = fileLogo.toURI().toURL().toString();
        Image imageLogo = new Image(localUrlLogo);
        avatar.setImage(imageLogo);

        StringBuilder descriptionText = new StringBuilder();
        descriptionText.append(name + " " + surname + "\n");
        descriptionText.append("Возраст: " + age + "\n");
        descriptionText.append("Город: " + city + "\n");
        descriptionText.append("Хобби: ");

        Object[] interests = personToShow.getInterests().toArray();
        for (int i = 0; i < interests.length; i++) {
            String interest = (String) interests[i];
            descriptionText.append(interest);
            if (i == interests.length - 1) {
                descriptionText.append(".");
            } else {
                descriptionText.append(", ");
            }
        }
        description.setText(descriptionText.toString());
    }
}