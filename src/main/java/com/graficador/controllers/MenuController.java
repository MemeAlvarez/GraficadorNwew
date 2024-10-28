package com.graficador.controllers;

import com.graficador.ApplicationMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    Pane mainPane;

    @FXML
    Button btnInicio;
    @FXML
    Button btnTraslation;
    @FXML
    Button btnEscalation;
    @FXML
    Button btnRotation;

    @FXML
    protected void initialize(){

        mainPane.setBackground(new Background(new BackgroundFill( Color.color(1,1,1), null,null )));

        btnTraslation.setOnAction(actionEvent -> {
            try {
                openGraph();
                ((Stage)btnTraslation.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnEscalation.setOnAction(actionEvent -> {
            try {
                openGraph();
                ((Stage)btnEscalation.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnRotation.setOnAction(actionEvent -> {
            try {
                openGraph();
                ((Stage)btnRotation.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnInicio.setOnAction(actionEvent -> {
            try {
                openHome();
                ((Stage)btnInicio.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void openGraph() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("GB2D/view-traslacion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1510, 768);
        stage.setTitle("Graficador");
        stage.setScene(scene);
        stage.show();
    }

    public void openHome() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("GB2D/view-home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Graficador");
        stage.setScene(scene);
        stage.show();
    }
}
