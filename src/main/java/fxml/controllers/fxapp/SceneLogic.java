package fxml.controllers.fxapp;

import acquaintance.Person;
import client.server.PersonToShowChooser;
import database.PeopleDatabase;
import exceptions.DBConnectException;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class SceneLogic {

    private Person loggedInPerson;
    private PersonToShowChooser personToShowChooser;

    public SceneLogic(String login, String password) throws DBConnectException {
        PeopleDatabase peopleDatabase = new PeopleDatabase();
        this.loggedInPerson = peopleDatabase.getPersonFromDB(login, password);
        personToShowChooser = new PersonToShowChooser(loggedInPerson);
    }

    public void changeLeft(){
        personToShowChooser.leftChanger();
    }
    public void changeRight(){
        personToShowChooser.rightChanger();
    }
    public void chooseFriends(){
        personToShowChooser.chooseFriends();
    }
    public void chooseCouples(){
        personToShowChooser.chooseCouples();
    }

    public void showNextPerson(ImageView avatar, Text description) throws MalformedURLException {
        Person personToShow = personToShowChooser.getCurrShownPerson();

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
        descriptionText.append("@" + login + "\n");
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
}
