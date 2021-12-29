package fxml.controllers.fxapp;

import com.example.oop_task_1.ClientServerApp;
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
            SceneLogic sceneLogic = new SceneLogic(login, password);

            FXMLLoader fxmlLoader = new FXMLLoader(ClientServerApp.class.getResource("fxAppFxml/scene.fxml"));
            Scene secondScene = new Scene(fxmlLoader.load(), 800, 800);

            Stage currentStage = (Stage) inputLogin.getScene().getWindow();
            currentStage.setScene(secondScene);

            SceneController controller = fxmlLoader.getController();
            controller.setSceneLogic(sceneLogic);
        } catch (ConnectException e) {
            new ConnectErrorAlert(e.getMessage());
        }
    }

    @FXML
    private void onRegisterButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientServerApp.class.getResource("fxAppFxml/register.fxml"));
        Scene changeScene = new Scene(fxmlLoader.load(), 800, 800);

        Stage currentStage = (Stage) inputLogin.getScene().getWindow();
        currentStage.setScene(changeScene);
    }
}
