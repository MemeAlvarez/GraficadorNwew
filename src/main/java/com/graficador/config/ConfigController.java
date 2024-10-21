package com.graficador.config;

import com.graficador.color.ConfigColor;
import com.graficador.graphic.Graph;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class ConfigController {

    Graph graph;

    @FXML
    CheckBox checkBox;

    @FXML
    ColorPicker colorPicker;

    @FXML
    Button btnSave, btnAbout;

    @FXML
    protected void initialize(){

        colorPicker.setOnAction(actionEvent -> {
            ConfigColor color_n = new ConfigColor();
            Color       color_p = colorPicker.getValue();

            color_n.setH(color_p.getHue       ()/360.0);
            color_n.setS(color_p.getSaturation());
            color_n.setV(color_p.getBrightness());

            graph.recolorizeGraph(color_n);
        });

        checkBox.setOnAction(actionEvent -> {
            graph.switchColorAuto();
        });

        btnSave.setOnAction(actionEvent -> {
            graph.getConfig().saveConfiguration(Configuration.config_route);
        });

        btnAbout.setOnAction(actionEvent -> {
            Alert aler = new Alert(Alert.AlertType.INFORMATION);
            aler.setTitle("Creditos");
            aler.setHeaderText(null);
            aler.setContentText("Jose Angel Mendoza Gutierrez 22380404\nManuel Alvarez Martinez 22380359\nAlvaro Jair Gomez Gonzalez 21380390");
            aler.show();
        });

    }

    public void onSetGraph(){
        colorPicker.setValue(graph.getMainColor().getAsColor());
        checkBox.setSelected(graph.getColorAuto());
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        onSetGraph();
    }
}
