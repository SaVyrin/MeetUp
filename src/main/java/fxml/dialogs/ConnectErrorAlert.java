package fxml.dialogs;

import javafx.scene.control.Alert;

public class ConnectErrorAlert {
    public ConnectErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(message);
        alert.show();
    }
}
