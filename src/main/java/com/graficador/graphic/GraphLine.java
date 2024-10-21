package com.graficador.graphic;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.Serializable;

public class GraphLine extends GraphObject implements Serializable {
    GraphPoint[]   points = new GraphPoint[2];
    transient Line line       ;
    String         name = "L0";

    private static final long serialVersionUID = 1L;

    public GraphLine(GraphPoint ps, GraphPoint pe, Graph graph){
        super(graph);
        points[0] = ps;
        points[1] = pe;

        line = new Line();
        setPoints(ps,pe);

        line.setStroke(graph.getMainColor().getAsColorV());
        addToGraph();
    }

    public GraphLine(GraphPoint ps, GraphPoint pe){
        super(null);
        points[0] = ps;
        points[1] = pe;

        line = new Line();
        setPoints(ps,pe);
    }

    public GraphLine setName(String name){
        this.name = name;
        return this;
    }

    public void setPoint(GraphPoint point, int index){
        points[index] = point;
    }

    public void setStartPoint(GraphPoint point_start){
        line.setStartX(point_start.getX());
        line.setStartY(point_start.getY());
    }

    public void setEndPoint(GraphPoint point_end){
        line.setEndX(point_end.getX());
        line.setEndY(point_end.getY());
    }

    public void setCoordPoint(GraphPoint point, int index){
        if (index == 0){
            setStartPoint(point);
        }else if (index == 1){
            setEndPoint(point);
        }
    }

    public void setPoints(GraphPoint ps, GraphPoint pe){
        setStartPoint(ps);
        setEndPoint  (pe);
    }

    public Line getLine(){
        return line;
    }

    public GraphPoint getStartPoint(){
        return points[0];
    }

    public GraphPoint getEndPoint(){
        return points[1];
    }

    public String getName(){
        return name;
    }

    public String getStartPointName(){
        GraphPoint point = getStartPoint();
        if (point != null){
            return point.getName();
        }
        return null;
    }

    public String getEndPointName(){
        GraphPoint point = getEndPoint();
        if (point != null){
            return point.getName();
        }
        return null;
    }

    public void colorize(){
        line.setStroke(mainGraph.getMainColor().getAsColorV());
    }

    public void onMainGraphSet(){
        colorize();
    }


    public boolean updatePoints(){
        boolean exit = true;
        for (int i = 0; i < 2; i++){
            GraphPoint point  = points[i];
            if (point != null) {
                Circle circle = point.getPoint();
                if (circle != null){
                    setCoordPoint(point,i);
                }else{
                    destroy();
                    exit = false;
                }
            }else{
                destroy();
                exit = false;
            }
        }
        return exit;
    }

    public void addToGraph(Graph graph){
        super.addToGraph(graph,line);
    }

    public void addToGraph(){
        super.addToGraph(line);
    }

    public void removeFromGraph(){
        super.removeFromGraph(line);
    }


    public void destroy(){
        removeFromGraph();
        name      = null;
        line      = null;
        points[0] = null;
        points[1] = null;
    }

    public void reload(Graph graph){
        line = new Line();
        setMainGraph(graph);

        setPoints(points[0],points[1]);
        line.setStroke(Color.color(0.2, 0.5, 0.6));

        addToGraph();
    }
}
