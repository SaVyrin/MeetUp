package fxml.controllers;

import client.server.Client;
import client.server.messages.Command;
import com.example.oop_task_1.Frame;
import connections.Connection;
import connections.ServerConnection;
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

        try {
            Connection<Client> serverConnection = new ServerConnection();

            Client client = serverConnection.connect();
            client.sendToServer(Command.LOG_IN, login + Command.SEPARATOR + password);

            FXMLLoader fxmlLoader = new FXMLLoader(Frame.class.getResource("fxml/scene.fxml"));
            Scene secondScene = new Scene(fxmlLoader.load(), 800, 800);

            Stage currentStage = (Stage) inputLogin.getScene().getWindow();
            currentStage.setScene(secondScene);

            SceneController controller = fxmlLoader.getController();
            controller.setClient(client);
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
