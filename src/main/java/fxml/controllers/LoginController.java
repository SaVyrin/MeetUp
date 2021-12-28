package fxml.controllers;

import acquaintance.Person;
import client.server.Client;
import com.example.oop_task_1.Frame;
import connections.Connection;
import connections.ServerConnection;
import database.PeopleDatabase;
import exceptions.ConnectException;
import fxml.dialogs.ConnectErrorAlert;
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

        Connection<Client> serverConnection = new ServerConnection();
        try {
            PeopleDatabase loginDB = new PeopleDatabase();
            Person loggedInPerson = loginDB.getPersonFromDB(login, password);

            Client client = serverConnection.connect();

            FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/scene.fxml"));
            Scene secondScene = new Scene(fxmlLoader.load(), 800, 800);

            Stage currentStage = (Stage) inputLogin.getScene().getWindow();
            currentStage.setScene(secondScene);

            SceneController controller = fxmlLoader.getController();
            controller.setLoggedInPerson(client, loggedInPerson);
        } catch (ConnectException e) {
            new ConnectErrorAlert(e.getMessage());
        }
    }

    @FXML
    private void onRegisterButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/register.fxml"));
        Scene changeScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) inputLogin.getScene().getWindow();
        currentStage.setScene(changeScene);
    }
}
