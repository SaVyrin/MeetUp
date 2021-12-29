package fxml.dialogs;

import javafx.scene.control.Alert;

public class AcquaintanceAlert {
    public AcquaintanceAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String message = "Запрос на добавление в друзья отправлен";
        alert.setHeaderText(message);
        alert.show();
    }
}
