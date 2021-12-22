package fxml.controllers;

import acquaintance.Person;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ModalController {
    @FXML
    private Text modalText;

    public void setAcquaintanceText(Person person, Person acquaintance) {
        modalText.setText(person.getName() + " познакомился(ась) с " + acquaintance.getName());
    }
}
