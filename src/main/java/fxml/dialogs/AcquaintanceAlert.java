package fxml.dialogs;

import acquaintance.Person;
import javafx.scene.control.Alert;

public class AcquaintanceAlert {
    public AcquaintanceAlert(Person loggedInPerson, Person personToAcquaintance) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String message = loggedInPerson.getName() + " познакомился(ась) с " + personToAcquaintance.getName();
        alert.setHeaderText(message);
        alert.show();
    }
}
