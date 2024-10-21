package com.graficador.graphic;

import com.graficador.utils.Utils;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.Serializable;

public class GraphPoint extends GraphObject implements Serializable {
    transient Circle    point;
    transient Text      text ;
              Point2D   coord  = new Point2D(0,0);
    String    name           ;

    Point2D  origin     = new Point2D(0,0);
    double[] data_coord = new double[2];
    String   data_text  = "";

    private static final long serialVersionUID = 1L;


    public GraphPoint(double x, double y,Graph graph){
        super(graph);
        point         = new Circle(x,y,2);
        text          = new Text(x,y,"Empty");
        text.setScaleX(0.75);
        text.setScaleY(0.75);

        addToGraph(mainGraph);
    }

    public GraphPoint(double x, double y){
        super(null);
        point         = new Circle(x,y,2);
        text          = new Text(x,y,"Empty");
        text.setScaleX(0.75);
        text.setScaleY(0.75);
    }

    public Point2D getCoordReal(double x, double y){
        double rx = mainGraph.coordToRealX(x);
        double ry = mainGraph.coordToRealY(y);
        return new Point2D(rx,ry);
    }

    public Point2D getCoordGraph(double x, double y){
        double gx = mainGraph.coordToGraphX(x);
        double gy = mainGraph.coordToGraphY(y);
        return new Point2D(gx,gy);
    }

    public double getX(){
        return getPoint().getCenterX();
    }

    public double getY(){
        return getPoint().getCenterY();
    }

    public double getXGraph(){
        return getCoordGraph(getX(),0).getX();
    }

    public double getYGraph(){
        return getCoordGraph(0,getY()).getY();
    }

    public double getXReal(){
        return getCoordReal(getX(),0).getX();
    }

    public double getYReal(){
        return getCoordReal(0,getY()).getY();
    }


    public Circle getPoint(){
        return point;
    }

    public String getName(){
        return name;
    }

    public Text   getText(){
        return text;
    }

    public String getCoordText(){
        Point2D coord = getCoordGraph(getX(),getY());
        return String.format("(%.2f, %.2f)",coord.getX(),coord.getY());
    }


    public void setCoord(Point2D coord){
        this.coord = coord;
    }

    public void setX(double x) {
        double width = mainGraph.getGraph_width();

        // x no debería modificar coord directamente.
        x = Utils.clamp(x + origin.getX(), -width, width);

        getPoint().setCenterX(getCoordReal(x, 0).getX());
        setXText(x);
        updateText();
    }

    public void setY(double y) {
        double height = mainGraph.getGraph_height();

        // y no debería modificar coord directamente.
        y = Utils.clamp(y + origin.getY(), -height, height);

        getPoint().setCenterY(getCoordReal(0, y).getY());
        setYText(y);
        updateText();
    }


    public void setXY(double x, double y) {
        // Toma en cuenta el origen al calcular las nuevas coordenadas
        setX(x);
        setY(y);
    }

    public void setXText(double x){
        getText().setX(getCoordReal(x,0).getX());
    }

    public void setYText(double y){
        getText().setY(getCoordReal(0,y).getY());
    }

    public GraphPoint setName(String name){
        this.name = name;
        text.setText(name);
        return this;
    }

    public GraphPoint setOrigin(Point2D origin){
        this.origin = origin;
        return this;
    }

    public GraphPoint setOrigin(double x, double y){
        this.origin = new Point2D(x,y);
        return this;
    }

    public GraphPoint setRadius(double radius){
        getPoint().setRadius(radius);
        return this;
    }



    public void adjust() {

        //System.out.println("Actualizando" + getName());

        // Obtener las coordenadas gráficas originales del punto (relativas al antiguo origen)
        Point2D originalGraphCoord = getCoordGraph(getX(), getY());

        // Calcula las nuevas coordenadas gráficas en base al nuevo origen
        double adjustedX = originalGraphCoord.getX() + (origin.getX() - mainGraph.graphForm.getPreviousOrigin().getX());
        double adjustedY = originalGraphCoord.getY() + (origin.getY() - mainGraph.graphForm.getPreviousOrigin().getY());

        // Convertir las coordenadas gráficas ajustadas a coordenadas reales
        Point2D realCoord = getCoordReal(adjustedX, adjustedY);

        // Actualizamos las coordenadas internas del punto
        coord = new Point2D(realCoord.getX(), realCoord.getY());

        // Ajusta visualmente la posición del punto en el gráfico
        getPoint().setCenterX(realCoord.getX());
        getPoint().setCenterY(realCoord.getY());

        // Ajustar la posición del texto asociado
        setXText(realCoord.getX());
        setYText(realCoord.getY());

        // Actualizar el texto con el nuevo nombre y coordenadas
        updateText();
    }



    public void colorize(){
        Color color = mainGraph.getMainColor().getAsColorV();
        point.setStroke(color);
        text.setStroke (color);
        point.setStrokeWidth(4);
    }

    public void onMainGraphSet(){
        colorize();
    }



    public void updateText(){
        setName(name);
    }

    public void addToGraph(Graph graph){
        super.addToGraph(graph,point,text);
    }

    public void addToGraph(){
        super.addToGraph(point,text);
    }

    public void removeFromGraph(){
        super.removeFromGraph(point,text);
    }

    public void destroy(){
        removeFromGraph();
        point = null;
        text  = null;
    }


    public void datalize(){
        data_coord[0] = getX();
        data_coord[1] = getY();

        data_text = getText().getText();
    }

    public void reload(Graph graph){
        point = new Circle(0,0,2    );
        text  = new Text  (0,0,data_text);

        setMainGraph(graph);

        double x = data_coord[0];
        double y = data_coord[1];

        x = Utils.clamp(x,0,mainGraph.getPrefWidth ());
        y = Utils.clamp(y,0,mainGraph.getPrefHeight());

        point.setCenterX(x);
        point.setCenterY(y);
        text.setX(x);
        text.setY(y);

        addToGraph();
    }
}
