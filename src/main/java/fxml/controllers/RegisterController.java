package fxml.controllers;

import acquaintance.Person;
import com.example.oop_task_1.Frame;
import database.PeopleDatabase;
import exceptions.ConnectException;
import fxml.dialogs.ConnectErrorAlert;
import fxml.dialogs.SuccessfulRegisterAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    @FXML
    private TextField inputLogin;
    @FXML
    private TextField inputPassword;
    @FXML
    private TextField inputSurname;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputAge;
    @FXML
    private ToggleGroup inputSex;
    @FXML
    private TextField inputCity;
    @FXML
    private GridPane inputInterests;

    @FXML
    private void onLoginButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/login.fxml"));
        Scene changeScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) inputLogin.getScene().getWindow();
        currentStage.setScene(changeScene);
    }

    @FXML
    private void onRegisterButtonClick() {
        String login = inputLogin.getText();
        String password = inputPassword.getText();
        String surname = inputSurname.getText();
        String name = inputName.getText();
        int age = Integer.parseInt(inputAge.getText());

        String sex = "";
        RadioButton selectedSexRadio = (RadioButton) inputSex.getSelectedToggle();
        if (selectedSexRadio.getText().equals("М")) {
            sex = "male";
        }
        if (selectedSexRadio.getText().equals("Ж")) {
            sex = "female";
        }

        List<String> interests = new ArrayList<>();
        List<Node> checkBoxes = inputInterests.getChildren();
        for (Node node : checkBoxes) {
            CheckBox checkBox = (CheckBox) node;
            if (checkBox.isSelected()) {
                interests.add(checkBox.getText());
            }
        }

        String city = inputCity.getText();

        Person person = new Person(
                0,
                login,
                password,
                name,
                surname,
                age,
                sex,
                city,
                interests);
        try {

            PeopleDatabase registerDB = new PeopleDatabase();
            registerDB.insertPerson(person);
            new SuccessfulRegisterAlert();

        } catch (ConnectException e) {
            new ConnectErrorAlert(e.getMessage());
            e.printStackTrace();
        }
    }
}
