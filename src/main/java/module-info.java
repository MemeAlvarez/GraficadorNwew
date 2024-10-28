module com.graficador {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.compiler;
    requires com.google.gson;
    requires jdk.jshell;

    opens com.graficador to javafx.fxml, com.google.gson;
    opens com.graficador.main to javafx.fxml;
    opens com.graficador.controllers to javafx.fxml;
    opens com.graficador.graphic to javafx.fxml;
    opens com.graficador.config to javafx.fxml, com.google.gson;  // Agregado para com.google.gson
    opens com.graficador.color to javafx.fxml, com.google.gson;   // Agregado si es necesario

    exports com.graficador.main;
    exports com.graficador.graphic;
    exports com.graficador;
    exports com.graficador.controllers;
}

