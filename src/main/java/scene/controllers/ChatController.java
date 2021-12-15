package scene.controllers;

import acquaintance.Person;
import com.example.oop_task_1.Frame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ChatController {
    @FXML
    private ImageView avatar;

    private Person loggedInPerson;

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
}
