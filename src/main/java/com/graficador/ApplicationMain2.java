package com.graficador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain2 extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("/com/graficador/GB2D/view-home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Graficador");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}