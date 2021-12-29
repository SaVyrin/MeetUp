package fxml.controllers.fxapp;

import exceptions.DBConnectException;
import fxml.dialogs.AcquaintanceAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.MalformedURLException;

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

    private SceneLogic sceneLogic;

    public void setSceneLogic(SceneLogic sceneLogic) {
        this.sceneLogic = sceneLogic;
    }

    @FXML
    private void leftPersonToShowChange() throws MalformedURLException {
        sceneLogic.changeLeft();
        sceneLogic.showNextPerson(avatar,description);
    }

    @FXML
    private void rightPersonToShowChange() throws MalformedURLException {
        sceneLogic.changeRight();
        sceneLogic.showNextPerson(avatar,description);
    }

    @FXML
    private void findFriend() throws MalformedURLException {
        leftPersonChangeButton.setVisible(true);
        rightPersonChangeButton.setVisible(true);
        acquaintanceButton.setVisible(true);

        sceneLogic.chooseFriends();
        sceneLogic.showNextPerson(avatar,description);
    }

    @FXML
    private void findCouple() throws MalformedURLException {
        leftPersonChangeButton.setVisible(true);
        rightPersonChangeButton.setVisible(true);
        acquaintanceButton.setVisible(true);

        sceneLogic.chooseCouples();
        sceneLogic.showNextPerson(avatar,description);
    }

    @FXML
    private void acquaintance() throws DBConnectException {
        sceneLogic.acquaintance();
        new AcquaintanceAlert();
    }
}