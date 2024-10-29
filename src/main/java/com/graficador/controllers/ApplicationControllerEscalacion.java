package com.graficador.controllers;

import com.graficador.ApplicationMain;
import com.graficador.graphic.Graph;
import com.graficador.graphic.GraphPoint;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationControllerEscalacion {

    @FXML
    private Pane mainPane;

    @FXML
    Button btnGraphFigura;
    @FXML
    Button btnLimpiar;
    @FXML
    Button btnRegresarMenu;
    @FXML
    Button btnEscalar;
    @FXML
    ToolBar configToolbar;
    @FXML
    TableView<GraphPoint> pointTable;

    @FXML
    TableColumn<GraphPoint, String> namePuntos;
    @FXML
    TableColumn<GraphPoint, Double> xColumn;

    @FXML
    TableColumn<GraphPoint, Double> yColumn;

    //-----------------------------------------
    @FXML
    TableView<GraphPoint> pointTableP;

    @FXML
    TableColumn<GraphPoint, String> namePuntosP;
    @FXML
    TableColumn<GraphPoint, Double> xColumnP;
    @FXML
    TableColumn<GraphPoint, Double> yColumnP;

    @FXML
    TextField EscX, EscY;

    double Sx , Sy ;


    Graph graph;

    public ApplicationControllerEscalacion() {
        graph = new Graph(32, 64, 768, 640);
    }

    @FXML
    protected void initialize() {
        initializaPointTable();
        initializaPointTableP();

        graph.addToMainPane(mainPane);
        graph.setConfigToolbar (configToolbar);
        btnGraphFigura.setOnAction(event -> {
            EscX.setText("1.25");
            EscY.setText("1.25");
            graph.updateoriginalPointTableS(this);
            graph.setFiguraPersonalidaS(this); // Configura la figura
        });

        btnLimpiar.setOnAction(event -> {
            EscX.setText("");
            EscY.setText("");
            graph.setLimpiar();
            graph.clearTables();
        });


        btnRegresarMenu.setOnAction(actionEvent -> {
            try {
                openMenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((Stage) btnRegresarMenu.getScene().getWindow()).close();

        });

        btnEscalar.setOnAction(event -> {
            try {
                Sx = Double.parseDouble(EscX.getText());
                Sy = Double.parseDouble(EscY.getText());
                System.out.println("SIENTREEEE" + Sx + "," + Sy);
            } catch (NumberFormatException e) {
                Sx = 0;
                Sy = 0; // Valor predeterminado en caso de error
                System.out.println("NOENTREEEEE" + Sx + "," + Sy);
            }
            graph.setFiguraEscalada(this);
            graph.updateTranslatedPointTableS(this);// Configura la figura trasladada
        });
    }

    public double getSx() {
        return Sx;
    }

    public double getSy() {
        return Sy;
    }

    public void initializaPointTable(){

        namePuntos.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn   .setCellValueFactory(new PropertyValueFactory<>("xGraph"   ));
        yColumn   .setCellValueFactory(new PropertyValueFactory<>("yGraph"   ));

        graph.setPointTableOriginal(pointTable);
    }

    public void initializaPointTableP(){

        namePuntosP.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumnP   .setCellValueFactory(new PropertyValueFactory<>("xGraph"   ));
        yColumnP   .setCellValueFactory(new PropertyValueFactory<>("yGraph"   ));

        graph.setPointTableP(pointTableP);
    }



    //metodo para regresar al menu
    public void openMenu() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("GB2D/view-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }
}