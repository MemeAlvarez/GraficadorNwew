package com.graficador.controllers;

import com.graficador.graphic.Graph;
import com.graficador.graphic.GraphForm;
import com.graficador.graphic.GraphLine;
import com.graficador.graphic.GraphPoint;
import com.graficador.utils.DialogUtils;
import com.graficador.utils.Utils;
import javafx.util.Pair;

import java.util.List;

public class GraphFigura {

    @FunctionalInterface
    public interface GraphTemplateAction {
        void apply(Graph graph, GraphForm graphForm, ApplicationControllerTraslacion apt);
    }

    public enum GRAPH_FIGURA {
        FIGURAORIGINAL("Figura Original", GraphFigura::createFiguraOriginal),
        FIGURATRASLADADA("Figura Trasladada", GraphFigura::createFiguraTrasladada);

        String   name  ;
        GraphTemplateAction create;

        GRAPH_FIGURA(String name,GraphTemplateAction create){
            setName  (name  );
            setCreate(create);
        }

        public void setName(String name){
            this.name = name;
        }

        public void setCreate(GraphTemplateAction create){
            this.create = create;
        }

        public String getName(){
            return name;
        }

        public void create(Graph graph, GraphForm graphForm, ApplicationControllerTraslacion apt){
            create.apply(graph,graphForm,apt);
        }

    }



    public static void createFiguraOriginal(Graph graph, GraphForm graphForm, ApplicationControllerTraslacion apt){
        GraphPoint[] points = new GraphPoint[8];
        points[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P1");
        points[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P2");
        points[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P3");
        points[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P4");
        points[4] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P5");
        points[5] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P6");
        points[6] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P7");
        points[7] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P8");

        points[0].setXY(10, 30);
        points[1].setXY(20, 30);
        points[2].setXY(20, 20);
        points[3].setXY(30, 20);
        points[4].setXY(30, 30);
        points[5].setXY(40, 30);
        points[6].setXY(40, 20);
        points[7].setXY(50, 20);

        for (GraphPoint point : points) {
            graphForm.addPoint(point);
        }

        for(int i = 0; i < points.length-1; i++) {
            graphForm.addLine(((GraphLine)new GraphLine(points[i], points[i+1]).setMainGraph(graph)).setName("L" + i));
        }

    }

    public static void createFiguraTrasladada(Graph graph, GraphForm graphForm, ApplicationControllerTraslacion apt){

        int tx = apt.getTx();
        int ty = apt.getTy();

        System.out.println("SIFUNCIONA " + tx + "," + ty);

        GraphPoint[] pointsDos = new GraphPoint[8];
        pointsDos[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'1");
        pointsDos[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'2");
        pointsDos[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'3");
        pointsDos[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'4");
        pointsDos[4] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'5");
        pointsDos[5] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'6");
        pointsDos[6] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'7");
        pointsDos[7] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'8");



        pointsDos[0].setXY(10 + tx, 30 + ty);
        pointsDos[1].setXY(20 + tx, 30 + ty);
        pointsDos[2].setXY(20 + tx, 20 + ty);
        pointsDos[3].setXY(30 + tx, 20 + ty);
        pointsDos[4].setXY(30 + tx, 30 + ty);
        pointsDos[5].setXY(40 + tx, 30 + ty);
        pointsDos[6].setXY(40 + tx, 20 + ty);
        pointsDos[7].setXY(50 + tx, 20 + ty);

        for (GraphPoint point : pointsDos) {
            graphForm.addPoint(point);
        }

        for(int i = 0; i < pointsDos.length-1; i++) {
            graphForm.addLine(((GraphLine)new GraphLine(pointsDos[i], pointsDos[i+1]).setMainGraph(graph)).setName("L" + i));
        }



    }
    public static GraphPoint[] getPointsTrasladados(Graph graph, GraphForm graphForm, ApplicationControllerTraslacion apt) {
        int tx = apt.getTx();
        int ty = apt.getTy();

        GraphPoint[] pointsDos = new GraphPoint[8];
        pointsDos[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'1");
        pointsDos[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'2");
        pointsDos[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'3");
        pointsDos[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'4");
        pointsDos[4] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'5");
        pointsDos[5] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'6");
        pointsDos[6] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'7");
        pointsDos[7] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'8");

        pointsDos[0].setXY(10 + tx, 30 + ty);
        pointsDos[1].setXY(20 + tx, 30 + ty);
        pointsDos[2].setXY(20 + tx, 20 + ty);
        pointsDos[3].setXY(30 + tx, 20 + ty);
        pointsDos[4].setXY(30 + tx, 30 + ty);
        pointsDos[5].setXY(40 + tx, 30 + ty);
        pointsDos[6].setXY(40 + tx, 20 + ty);
        pointsDos[7].setXY(50 + tx, 20 + ty);

        return pointsDos; // Devolver el arreglo de puntos trasladados
    }
    public static GraphPoint[] getPointsOriginales(Graph graph, GraphForm graphForm, ApplicationControllerTraslacion apt) {
        int tx = apt.getTx();
        int ty = apt.getTy();

        GraphPoint[] points = new GraphPoint[8];
        points[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'1");
        points[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'2");
        points[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'3");
        points[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'4");
        points[4] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'5");
        points[5] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'6");
        points[6] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'7");
        points[7] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P'8");

        points[0].setXY(10, 30);
        points[1].setXY(20, 30);
        points[2].setXY(20, 20);
        points[3].setXY(30, 20);
        points[4].setXY(30, 30);
        points[5].setXY(40, 30);
        points[6].setXY(40, 20);
        points[7].setXY(50, 20);

        return points; // Devolver el arreglo de puntos trasladados
    }

}