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
import javafx.scene.control.TextField;
import java.io.IOException;

public class ApplicationControllerTraslacion {

    @FXML
    private Pane mainPane;

    @FXML
    Button btnGraphFigura;
    @FXML
    Button btnLimpiar;
    @FXML
    Button btnRegresarMenu;
    @FXML
    Button btnTrasladar;

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
    TextField trasX, trasY;

    int tx , ty ;


    Graph graph;

    public ApplicationControllerTraslacion() {
        graph = new Graph(32, 64, 768, 640);
    }

    @FXML
    protected void initialize() {
        initializaPointTable();

        initializaPointTableP();
        //graph.updateComponents();
        graph.addToMainPane(mainPane);


        btnGraphFigura.setOnAction(event -> {
            trasX.setText("20");
            trasY.setText("30");
            graph.updateoriginalPointTable(this);
            graph.setFiguraPersonalida(this); // Configura la figura
        });

        btnLimpiar.setOnAction(event -> {
            trasX.setText("");
            trasY.setText("");
            graph.setLimpiar();
            graph.clearTables();
        });


        btnRegresarMenu.setOnAction(actionEvent -> {
            try {
                openMenu();
                ((Stage) btnRegresarMenu.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnTrasladar.setOnAction(event -> {
            try {
                tx = Integer.parseInt(trasX.getText());
                ty = Integer.parseInt(trasY.getText());
                System.out.println("SIENTREEEE" + tx + "," + ty);
            } catch (NumberFormatException e) {
                tx = 0;
                ty = 0; // Valor predeterminado en caso de error
                System.out.println("NOENTREEEEE" + tx + "," + ty);
            }
            graph.setFiguraTrasladada(this);
            graph.updateTranslatedPointTable(this);// Configura la figura trasladada
        });
    }

    public int getTx() {
        return tx;
    }

    public int getTy() {
        return ty;
    }

    public void initializaPointTable(){

        namePuntos.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn   .setCellValueFactory(new PropertyValueFactory<>("xGraph"   ));
        yColumn   .setCellValueFactory(new PropertyValueFactory<>("yGraph"   ));

        graph.setPointTable(pointTable);
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