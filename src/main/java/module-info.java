module com.example.oop_task_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.oop_task_1 to javafx.fxml;
    exports com.example.oop_task_1;
    exports scene.controllers;
    opens scene.controllers to javafx.fxml;
}