package com.graficador.main;

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
    Button btnContinue;

    @FXML
    protected void initialize(){

        mainPane.setBackground(new Background(new BackgroundFill( Color.color(1,1,1), null,null )));

        Image     image     = new Image(getClass().getResourceAsStream("/com/graficador/images/encabezado/btn_continuar.JPG"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth (btnContinue.getPrefWidth());  // Ajustar el ancho
        imageView.setFitHeight(btnContinue.getPrefHeight());

        btnContinue.setGraphic(imageView);
        btnContinue.setOnAction(actionEvent -> {
            try {
                openGraph();
                ((Stage)btnContinue.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void openGraph() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("view-graph.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1510, 768);
        stage.setTitle("Graficador");
        stage.setScene(scene);
        stage.show();
    }
}
