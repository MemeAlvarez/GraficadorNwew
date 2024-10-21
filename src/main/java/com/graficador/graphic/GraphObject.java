package com.graficador.graphic;

import javafx.scene.Node;

import java.io.Serializable;

public class GraphObject implements Serializable {
    transient Graph mainGraph;

    private static final long serialVersionUID = 1L;


    public GraphObject(Graph graph){
        mainGraph = graph;
    }

    public void addToGraph(Graph graph,Node... obj) {
        setMainGraph(graph);
        for (Node _obj : obj) {
            mainGraph.getChildren().add(_obj);
        }
    }

    public void addToGraph(Node... obj) {
        for (Node _obj : obj) {
            mainGraph.getChildren().add(_obj);
        }
    }

    public Graph getMainGraph(){
        return mainGraph;
    }

    public GraphObject setMainGraph(Graph graph){
        mainGraph = graph;
        onMainGraphSet();
        return this;
    }

    public void onMainGraphSet(){}

    public void colorize(){}

    public void removeFromGraph(Node... obj){
        for (Node _obj : obj) {
            mainGraph.getChildren().remove(_obj);
        }
    }

    public void destroy(){}

    public void datalize(){}

    public void reload(){}
}
