package fxml.dialogs;

import javafx.scene.control.Alert;

public class SuccessfulRegisterAlert {
    public SuccessfulRegisterAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Successfully registered");
        alert.show();
    }
}
