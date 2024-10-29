package com.graficador.main;

import com.graficador.color.ConfigColor;
import com.graficador.graphic.*;
import com.graficador.utils.Utils;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.util.List;

public class ApplicationController {

    @FXML
    private Pane mainPane;

    @FXML
    TableView<GraphPoint> pointTable;

    @FXML
    Button btnMostResult;

    @FXML
    TableColumn<GraphPoint, String> nameColumn;
    @FXML
    TableColumn<GraphPoint, Double> xColumn;
    @FXML
    TableColumn<GraphPoint, Double> yColumn;






    @FXML
    ToolBar commandToolbar;
    @FXML
    ToolBar configToolbar;

    @FXML
    TextField field_origen_x, field_origen_y;

    @FXML
    ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboTemplates;

    @FXML
    ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboLines;

    @FXML
    ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboConics;

    @FXML
    ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboPersonals;

    @FXML
    Button AAAA;

    Graph graph;

    public ApplicationController() {
        graph = new Graph(32, 64, 768, 640);
    }

    @FXML
    protected void initialize(){
        initializaPointTable();

        graph.setCommandToolbar(commandToolbar).
              setConfigToolbar (configToolbar ).
              setComboTemplate (comboTemplates).
                setComboLine (comboLines).
                setComboConics (comboConics).
                //setComboPersonals (comboPersonals).
                setComboPersonalts(comboPersonals, btnMostResult).
              setFieldOriginX  (field_origen_x).
              setFieldOriginY  (field_origen_y).
              addToMainPane    (mainPane      );
        graph.updateComponents();

        AAAA.setOnAction(event -> {
            graph.getGraphForm().aaa();
        });
    }

    public void initializaPointTable(){

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        /*
        nameColumn.setOnEditCommit(event -> {
            GraphPoint point = event.getRowValue();
            point.setName(event.getNewValue());
        });
*/
        xColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));/*
        xColumn.setOnEditCommit(event -> {
            double width = graph.getGraph_width();
            double value = event.getNewValue();
                   value = Utils.clamp(value, -width, width);

            GraphPoint point = event.getRowValue();
            point.setX(value);
            point.setCoord(new Point2D(value,0));
            graph.getGraphForm().updateLines();
        });

*/
        yColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));/*
        yColumn.setOnEditCommit(event -> {
            double height = graph.getGraph_height();
            double value  = event.getNewValue();
            value = Utils.clamp(value, -height, height);

            GraphPoint point = event.getRowValue();
            point.setY(value);
            point.setCoord(new Point2D(value,0));
            graph.getGraphForm().updateLines();
        });
*/

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn   .setCellValueFactory(new PropertyValueFactory<>("xGraph"   ));
        yColumn   .setCellValueFactory(new PropertyValueFactory<>("yGraph"   ));

        graph.setPointTable(pointTable);
    }




}