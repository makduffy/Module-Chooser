module com.example.modulechooser {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.modulechooser to javafx.fxml;
    exports com.example.modulechooser.main;
    opens com.example.modulechooser.main to javafx.fxml;
    exports com.example.modulechooser.model;
    opens com.example.modulechooser.model to javafx.fxml;
}