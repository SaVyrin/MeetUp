package fxml.controllers;

import acquaintance.Person;
import com.example.oop_task_1.Frame;
import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField inputLogin;
    @FXML
    private TextField inputPassword;

    @FXML
    private void onLoginButtonClick() throws IOException {
        String login = inputLogin.getText();
        String password = inputPassword.getText();
        Database loginDB = new Database();
        Person loggedInPerson = loginDB.getPersonFromDB(login, password);

        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/scene.fxml"));
        Scene secondScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) inputLogin.getScene().getWindow();
        currentStage.setScene(secondScene);

        SceneController controller = fxmlLoader.getController();
        controller.setLoggedInPerson(loggedInPerson);
    }

    @FXML
    public void onRegisterButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/register.fxml"));
        Scene changeScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) inputLogin.getScene().getWindow();
        currentStage.setScene(changeScene);
    }
}