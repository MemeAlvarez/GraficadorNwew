package com.graficador.graphic;

import com.graficador.utils.Utils;

import javafx.geometry.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class GraphForm extends GraphObject implements Serializable {

    private final ArrayList<GraphPoint> list_point ;
    private final ArrayList<GraphLine>  list_line  ;

    private static final long serialVersionUID = 1L;

    Point2D         origin = new Point2D(0,0);
    private Point2D previousOrigin;

    public GraphForm(Graph graph) {
        super(graph);
        list_point = new ArrayList<>();
        list_line  = new ArrayList<>();
    }

    public void addPoint(GraphPoint point){
        point.addToGraph(mainGraph);
        list_point.add(point);
    }

    public void addLine(GraphLine line){
        line.addToGraph(mainGraph);
        list_line.add(line);
    }


    public void removePoint(GraphPoint point){
        point.destroy();
        list_point.remove(point);
    }

    public void removeLine(GraphLine line){
        line.destroy();
        list_line.remove(line);
    }

    public Boolean canAddPoint(double x,double y){
        return mainGraph.inBoundsXY(x,y);
    }

    public ArrayList<GraphPoint> getPoints(){
        return list_point;
    }

    public ArrayList<GraphLine> getLines(){
        return list_line;
    }

    public int getPointCount(){
        return getPoints().size();
    }

    public int getLineCount(){
        return getLines().size();
    }

    public GraphPoint getLastPoint(){
        ArrayList<GraphPoint> points = getPoints();
        return points.get(points.size() - 2);
    }

    public Point2D getOrigin(){
        return origin;
    }

    public GraphPoint findPoint(String name){
        for(GraphPoint point : getPoints()){
            if (Objects.equals(point.getName(), name)){
                return point;
            }
        }
        return null;
    }

    public void executeGraphFormScript(Graph graph){
        //GraphTemplates.createCircle(graph,this);
        //GraphTemplates.createLine(graph,this,90);
    }

    public void colorize(){
        for(GraphPoint point: getPoints()){
            point.colorize();
        }
        for(GraphLine line: getLines()){
            line.colorize();
        }
    }

    public void updateLines(){
        int list_size = list_line.size();
        int i = list_size - 1;
        while(i >= 0){
            GraphLine line = list_line.get(i);
            if (!line.updatePoints()){
                list_line.remove(i);
            }
            i--;
        }
    }

    public void updatePoints(){
        for(GraphPoint point: list_point){
            point.setOrigin(origin);
            point.adjust();
        }
    }


    public void clearNull(){
        ArrayList<GraphPoint> pointList = getPoints();
        ArrayList<GraphLine> lineList   = getLines();

        for(GraphPoint point : pointList){
            if (point == null) {
                removePoint(point);
            }
        }

        for(GraphLine line : lineList){
            if (line == null) {
                removeLine(line);
            }
        }
    }

    public void clearPoints(){
        ArrayList<GraphPoint> pointList = getPoints();
        for(GraphPoint point : pointList){
            point.destroy();
        }
        pointList.clear();
    }

    public void clearLines(){
        ArrayList<GraphLine> lineList = getLines();
        for(GraphLine line : lineList){
            line.destroy();
        }
        lineList.clear();
    }

    public void clear(){
        clearPoints();
        clearLines();
    }


    public Point2D getPreviousOrigin() {
        return previousOrigin;
    }

    public void setPreviousOrigin(Point2D previousOrigin) {
        this.previousOrigin = previousOrigin;
    }

    public void setOrigin(double x, double y){
        origin = new Point2D(x,y);
    }



    public void destroy(){
        for(GraphPoint point:getPoints()){
            point.destroy();
        }
        for(GraphLine line:getLines()){
            line.destroy();
        }
    }

    public void datalize(){
        for(GraphPoint point:getPoints()){
            point.datalize();
        }
        for(GraphLine line:getLines()){
            line.datalize();
        }
    }

    public void reload(){
        for(GraphPoint point:getPoints()){
            point.reload(mainGraph);
        }
        for(GraphLine line:getLines()){
            line.reload(mainGraph);
        }
    }

    public interface AdjustInterface {
        public default void execute(GraphForm form,Graph graph, GraphPoint point, int index, int size){

        }
    }

    public void adjustPoints(AdjustInterface inter){
        int i    = 0;
        int size = list_point.size();
        for(GraphPoint point: list_point) {
            inter.execute(this, getMainGraph(),point, i++, size);
        }
    }

    public void aaa(){
        adjustPoints(new AdjustInterface() {
            @Override
            public void execute(GraphForm form, Graph graph, GraphPoint point, int index, int size) {
                point.setXY(0,0);
            }
        });
    }

}
