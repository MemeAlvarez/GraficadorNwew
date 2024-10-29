package com.graficador.graphic;

import com.graficador.ApplicationMain;
import com.graficador.color.ConfigColor;
import com.graficador.config.ConfigController;
import com.graficador.config.Configuration;
import com.graficador.controllers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Graph extends Pane {

    Configuration config     = Configuration.loadConfiguration(Configuration.config_route);
    ConfigColor   mainColor  = null;
    Boolean       color_auto = null;

    enum GRAPH_COMMANDS{
        SELECT        ,
        POINT_ADD
    }

    GRAPH_COMMANDS graphState = GRAPH_COMMANDS.POINT_ADD;

    Point2D graph_origin = new Point2D(0,0);

    File current_file = null;

    int    graph_division = 8 ;
    double graph_width    = 64;
    double graph_height   = 64;

    Line line_h = new Line();
    Line line_v = new Line();

    ArrayList<Line> div_list_l = new ArrayList<Line>();
    ArrayList<Text> div_list_t = new ArrayList<Text>();

    TableView<GraphPoint>                   pointTable;

    TableView<GraphPoint>                   OriginalTable;
    TableView<GraphPoint>                   OriginalTableP ;


    ToolBar                                 commandToolbar;
    ToolBar                                 manageToolbar ;
    ToolBar                                 configToolbar ;
    ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboTemplate ;
    TextField                               field_origen_x, field_origen_y;

    GraphForm graphForm = new GraphForm(this);

    public Graph (double x, double y, double w, double h){
        prepareConfig();
        prepareGraph(x,y,w,h);
    }

    //Grafica
    public void drawGraph(){
        Color alterColor = getAlterColor();
        drawAxis();
        drawDiv ();

        colorizeGraph(mainColor);
        colorizeAxis(alterColor);
        colorizeDiv (alterColor);
    }

    public void redrawGraph(){
        Color alterColor = getAlterColor();
        clearAxis();
        clearDiv();

        drawAxis();
        drawDiv ();

        colorizeGraph(mainColor);
        colorizeAxis(alterColor);
        colorizeDiv (alterColor);
    }


    //Ejes
    public void drawAxisHorizontal(){
        line_h.setStartY (graph_origin.getY());
        line_h.setEndX   (getWidth());
        line_h.setEndY   (graph_origin.getY());
        getChildren().add(line_h);
    }

    public void drawAxisVertical(){
        line_v.setStartX (graph_origin.getX());
        line_v.setEndY   (getHeight());
        line_v.setEndX   (graph_origin.getX());
        getChildren().add(line_v);
    }

    public void drawAxis(){
        drawAxisHorizontal();
        drawAxisVertical  ();
    }

    public void clearAxisHorizontal(){
        getChildren().remove(line_h);
    }

    public void clearAxisVertical(){
        getChildren().remove(line_v);
    }

    public void clearAxis(){
        clearAxisHorizontal();
        clearAxisVertical  ();
    }


    //Divisiones

    public void drawDiv(){
        double width      = getWidth ();
        double height     = getHeight();
        int    div_count  = graph_division;
        div_count *= 2;

        for(int i = 0; i <= div_count; i++){
            drawDivHorizontal(i,div_count,width );
            drawDivVertical  (i,div_count,height);
        }

    }

    public void drawDivHorizontal(int i,int div_count, double width){
        Line line_div = new Line();
        Text text_div = null;

        //linea
        double line_div_x = width-(width*(1.0-( (double) i /div_count)));

        line_div.setStartX(line_div_x);
        line_div.setEndX  (line_div_x);

        line_div.setStartY(graph_origin.getY() + 2);
        line_div.setEndY  (graph_origin.getY() - 2);

        //texto
        double text_val = coordToGraphX(line_div_x);
        if (text_val != 0) {
            String text_str = String.format("%.2f",text_val);
            text_div = new Text(0, 0, text_str);

            double div_factor = (double) (64 - div_count)/64;
                   div_factor = Math.max(div_factor, 0.25);
            text_div.setScaleX(div_factor);
            text_div.setScaleY(div_factor);

            text_div.setX(line_div_x - text_div.getBoundsInLocal().getWidth() * 0.25);
            text_div.setY(line_div.getEndY() - text_div.getBoundsInLocal().getWidth() * 0.25);

            getChildren().add(text_div);
            div_list_t   .add(text_div);
        }

        getChildren().add(line_div);
        div_list_l   .add(line_div);

    }

    public void drawDivVertical(int i,int div_count, double height){
        Line line_div = new Line();
        Text text_div = null;

        //linea
        double line_div_y = height-(height*(1.0-( (double) i /div_count)));

        line_div.setStartY(line_div_y);
        line_div.setEndY  (line_div_y);

        line_div.setStartX(graph_origin.getX() + 2);
        line_div.setEndX  (graph_origin.getX() - 2);

        //texto
        double text_val = coordToGraphY(line_div_y);
        if (text_val != 0) {
            String text_str = String.format("%.2f",text_val);
            text_div = new Text(0, 0, text_str);

            double div_factor = (double) (64 - div_count)/64;
                   div_factor = Math.max(div_factor, 0.25);
            text_div.setScaleX(div_factor);
            text_div.setScaleY(div_factor);

            text_div.setY(line_div_y + text_div.getBoundsInLocal().getHeight() * 0.25); // <- watafuck 0.25?
            text_div.setX(line_div.getEndX() + text_div.getBoundsInLocal().getWidth()*0.25 );

            getChildren().add(text_div);
            div_list_t   .add(text_div);
        }

        getChildren().add(line_div);
        div_list_l   .add(line_div);

    }

    public void clearDiv(){
        for(Line line : div_list_l){
            getChildren().remove(line);
        }
        div_list_l.clear();
    }


    //Comandos

    public void executeCommand(GRAPH_COMMANDS command, InputEvent inputEvent){
        MouseEvent mouseEvent = (MouseEvent)inputEvent;
        /* BYEBYE
        switch (command){
            case SELECT:

            break;

            case POINT_ADD:
                addPoint( mouseEvent.getX(),mouseEvent.getY(),2);
            break;



            case POINT_LINE_ADD:
                addPointLine( mouseEvent.getX(),mouseEvent.getY(),2);
            break;
        }*/
    }

    public void addPoint(double x, double y, double rad){
        if ( canAddPoint(x,y) ) {
            GraphPoint point = ((GraphPoint) ( new GraphPoint(x, y).
                                setMainGraph(this) )).
                                setOrigin(graphForm.getOrigin()).
                                setRadius(rad).
                                setName("P"+graphForm.getPointCount());
            point.setXY(coordToGraphX(x),coordToGraphY(y));
            graphForm.addPoint(point);
            updatePointTable();
        }
    }

    public void addPointLine(double x, double y, double rad){
        if ( canAddPoint(x,y) ) {
            GraphPoint point = ((GraphPoint) ( new GraphPoint(x, y).
                    setMainGraph(this) )).
                    setRadius(rad).
                    setName("P"+graphForm.getPointCount());
            graphForm.addPoint(point);

            int l_count = graphForm.getPointCount();
            if (l_count > 1) {
                GraphLine line = new GraphLine(graphForm.getLastPoint(), point).setName("L"+graphForm.getLineCount());
                graphForm.addLine(line);
            }

            updatePointTable();
        }
    }

    public Boolean canAddPoint(double x, double y){
        return ( inBoundsXY(x,y) );
    }

    public Boolean inBoundsX(double x){
        return ( x > 0 && x <= getWidth() );
    }

    public Boolean inBoundsY(double y){
        return ( y > 0 && y <= getHeight() );
    }

    public Boolean inBoundsXY(double x, double y){
        return (inBoundsX(x) && inBoundsY(y));
    }

    public void updateComponents(){
        updatePointTable();
    }

    public void updatePointTable(){
        graphForm.clearNull();
        ObservableList<GraphPoint> list = FXCollections.observableArrayList( graphForm.getPoints() );
        pointTable.setItems(list);
        pointTable.refresh();
    }

    public void setGraphState(GRAPH_COMMANDS state){
        graphState = state;
    }

    public Boolean isGraphState(GRAPH_COMMANDS state){
        return (graphState == state);
    }

    public void clearPoints(){
        ArrayList<GraphPoint> points = graphForm.getPoints();
        for(GraphPoint point : points){
            point.destroy();
        }
        points.clear();
    }

    //Transformaciones
    public double coordToGraphX(double x){
        double width_m = getWidth()*0.5;
        return ( (x - width_m)/(width_m) )*(graph_width);
    }

    public double coordToGraphY(double y){
        double height_m = getHeight()*0.5;
        return ( (height_m - y)/(height_m) )*(graph_height);
    }

    public double coordToRealX(double x){
        double width_m = getWidth()*0.5;
        return width_m + ( width_m*(x/graph_width) );
    }

    public double coordToRealY(double y){
        double height_m = getHeight()*0.5;
        return height_m - ( height_m*(y/graph_height) );
    }


    //Origen de la grafica
    public void setGraphOriginX(double x){
        graph_origin = new Point2D(x,graph_origin.getY());
    }

    public void setGraphOriginY(double y){
        graph_origin = new Point2D(graph_origin.getX(),y);
    }

    public void setGraphOriginXY(double x, double y){
        graph_origin = new Point2D(x,y);
    }

    public void setGraphOrigin(Point2D point2D){
        graph_origin = point2D;
    }

    public Point2D getGraphOrigin(){
        return graph_origin;
    }


    //Agregar a mainPane
    public void addToMainPane(Pane mainPane){
        mainPane.getChildren().add(this);
    }

    //g&s

    public Configuration getConfig() {
        return config;
    }

    public void setColorAuto(Boolean auto){
        color_auto = auto;
        config.setColorAuto(color_auto);
    }

    public Boolean getColorAuto(){
        return color_auto;
    }

    public void setMainColor(ConfigColor color){
        mainColor = color;
        config.setBackgroundColor(mainColor);
    }

    public void switchColorAuto(){
        setColorAuto(!getColorAuto());
    }

    public ConfigColor getMainColor(){
        return mainColor;
    }

    public Color getAlterColor(){return mainColor.getAsColorV(1.0,0.0);}

    public GraphForm getGraphForm(){
        return graphForm;
    }

    public double getGraph_width(){
        return graph_width;
    }

    public double getGraph_height(){
        return graph_height;
    }

    public TableView<GraphPoint> getPointTable() {
        return pointTable;
    }
    public Graph setPointTable(TableView<GraphPoint> pointtable) {

        //Hide

        pointtable.setOnMouseClicked(mouseEvent -> {
            TablePosition<?, ?> pos = pointtable.getFocusModel().getFocusedCell();
            if (pos != null && pos.getColumn() == 0) {
                GraphPoint point = pointtable.getSelectionModel().getSelectedItem();
                if (point != null) {
                    pointtable.refresh();  // Refresca después de cambiar la visibilidad
                }
            }
        });


        //Delete
        pointtable.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.BACK_SPACE){
                TablePosition<?, ?> pos = pointtable.getFocusModel().getFocusedCell();
                if (pos != null && pos.getRow() >= 0) {
                    GraphPoint point = pointtable.getSelectionModel().getSelectedItem();
                    if (point != null) {
                        graphForm.removePoint(point);
                        graphForm.updateLines();
                        updateComponents();
                    }
                }
            }
        });

        this.pointTable = pointtable;
        return this;
    }
    public Graph setPointTableOriginal(TableView<GraphPoint> originaltable) {

        //Hide

        originaltable.setOnMouseClicked(mouseEvent -> {
            TablePosition<?, ?> pos = originaltable.getFocusModel().getFocusedCell();
            if (pos != null && pos.getColumn() == 0) {
                GraphPoint point = originaltable.getSelectionModel().getSelectedItem();
                if (point != null) {
                    originaltable.refresh();  // Refresca después de cambiar la visibilidad
                }
            }
        });


        //Delete
        originaltable.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.BACK_SPACE){
                TablePosition<?, ?> pos = originaltable.getFocusModel().getFocusedCell();
                if (pos != null && pos.getRow() >= 0) {
                    GraphPoint point = originaltable.getSelectionModel().getSelectedItem();
                    if (point != null) {
                        graphForm.removePoint(point);
                        graphForm.updateLines();
                        updateComponents();
                    }
                }
            }
        });

        this.OriginalTable = originaltable;
        return this;
    }
    public Graph setPointTableP(TableView<GraphPoint> originaltableP) {

        //Hide

        originaltableP.setOnMouseClicked(mouseEvent -> {
            TablePosition<?, ?> pos = originaltableP.getFocusModel().getFocusedCell();
            if (pos != null && pos.getColumn() == 0) {
                GraphPoint point = originaltableP.getSelectionModel().getSelectedItem();
                if (point != null) {
                    originaltableP.refresh();  // Refresca después de cambiar la visibilidad
                }
            }
        });


        //Delete
        originaltableP.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.BACK_SPACE){
                TablePosition<?, ?> pos = originaltableP.getFocusModel().getFocusedCell();
                if (pos != null && pos.getRow() >= 0) {
                    GraphPoint point = originaltableP.getSelectionModel().getSelectedItem();
                    if (point != null) {
                        graphForm.removePoint(point);
                        graphForm.updateLines();
                        updateComponents();
                    }
                }
            }
        });

        this.OriginalTableP = originaltableP;
        return this;
    }

    public ToolBar getCommandToolbar() {
        return commandToolbar;
    }

    public Graph setCommandToolbar(ToolBar commandToolbar) {
        List<Node> itemList = commandToolbar.getItems();

        if (itemList.size() >= 1) {

            ((Button)(itemList.get(0))).setOnAction(event -> {
                setGraphState(GRAPH_COMMANDS.SELECT);
                graphForm.clear ();
                updateComponents();
            });

        }
        this.commandToolbar = commandToolbar;
        return this;
    }



    public Graph setConfigToolbar(ToolBar configToolbar){
        List<Node> itemList = configToolbar.getItems();

        if (!itemList.isEmpty()){
            ((Button) (itemList.get(0))).setOnAction(event -> {
                openConfigDialog();
            });
        }
        this.configToolbar = configToolbar;
        return this;
    }

    public Graph setComboTemplate(ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboTemplate){

        comboTemplate.getItems().addAll(
                GraphTemplates.GRAPH_TEMPLATE.RECTANGULO,
                GraphTemplates.GRAPH_TEMPLATE.TRIANGULO ,
                GraphTemplates.GRAPH_TEMPLATE.CIRCULO   ,
                GraphTemplates.GRAPH_TEMPLATE.LINEA     ,
                GraphTemplates.GRAPH_TEMPLATE.ELIPSE    ,
                GraphTemplates.GRAPH_TEMPLATE.ARCO      ,
                GraphTemplates.GRAPH_TEMPLATE.CARTESIANASABSOLUTAS
        );

        comboTemplate.setOnAction(actionEvent -> {
            GraphTemplates.GRAPH_TEMPLATE template = comboTemplate.getSelectionModel().getSelectedItem();
            if (template != null){
                template.create(this,graphForm);
                updateComponents();
                //loadGraphFormFileStream(template.getFileStream());
            }
        });

        this.comboTemplate = comboTemplate;
        return this;
    }

    public Graph setComboLine(ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboTemplate){

        comboTemplate.getItems().addAll(
                GraphTemplates.GRAPH_TEMPLATE.LINEA
        );

        comboTemplate.setOnAction(actionEvent -> {
            GraphTemplates.GRAPH_TEMPLATE template = comboTemplate.getSelectionModel().getSelectedItem();
            if (template != null){
                template.create(this,graphForm);
                updateComponents();
                //loadGraphFormFileStream(template.getFileStream());
            }
        });

        this.comboTemplate = comboTemplate;
        return this;
    }

    public Graph setComboConics(ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboTemplate){

        comboTemplate.getItems().addAll(
                GraphTemplates.GRAPH_TEMPLATE.CIRCULO   ,
                GraphTemplates.GRAPH_TEMPLATE.ELIPSE    ,
                GraphTemplates.GRAPH_TEMPLATE.ARCO
        );

        comboTemplate.setOnAction(actionEvent -> {
            GraphTemplates.GRAPH_TEMPLATE template = comboTemplate.getSelectionModel().getSelectedItem();
            if (template != null){
                template.create(this,graphForm);
                updateComponents();
                //loadGraphFormFileStream(template.getFileStream());
            }
        });

        this.comboTemplate = comboTemplate;
        return this;
    }

    public Graph setComboPersonals(ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboTemplate){

        comboTemplate.getItems().addAll(
                GraphTemplates.GRAPH_TEMPLATE.CARTESIANASABSOLUTAS,
                GraphTemplates.GRAPH_TEMPLATE.POLARESABSOLUTAS,
                GraphTemplates.GRAPH_TEMPLATE.CARTESIANASRELATIVAS,
                GraphTemplates.GRAPH_TEMPLATE.POLARESRELATIVAS
        );

        comboTemplate.setOnAction(actionEvent -> {
            GraphTemplates.GRAPH_TEMPLATE template = comboTemplate.getSelectionModel().getSelectedItem();
            if (template != null){
                template.create(this,graphForm);
                updateComponents();
                //loadGraphFormFileStream(template.getFileStream());
            }
        });

        this.comboTemplate = comboTemplate;
        return this;
    }

    //probar segunda forma
    public Graph setComboPersonalts(ComboBox<GraphTemplates.GRAPH_TEMPLATE> comboTemplate, Button btnShowProcedure) {

        comboTemplate.getItems().addAll(
                GraphTemplates.GRAPH_TEMPLATE.CARTESIANASABSOLUTAS,
                GraphTemplates.GRAPH_TEMPLATE.POLARESABSOLUTAS,
                GraphTemplates.GRAPH_TEMPLATE.CARTESIANASRELATIVAS,
                GraphTemplates.GRAPH_TEMPLATE.POLARESRELATIVAS
        );

        comboTemplate.setOnAction(actionEvent -> {
            GraphTemplates.GRAPH_TEMPLATE template = comboTemplate.getSelectionModel().getSelectedItem();

            if (template != null) {
                // Siempre ejecutar estas dos líneas, independientemente de la opción seleccionada
                template.create(this, graphForm);
                updateComponents();

                // Si es CARTESIANASABSOLUTAS, ocultar el botón y salir sin asignar acción al botón
                if (template == GraphTemplates.GRAPH_TEMPLATE.CARTESIANASABSOLUTAS) {
                    btnShowProcedure.setVisible(false); // Ocultar el botón
                    return;  // Salir sin hacer más
                }

                // Si es otro template, mostrar el botón y asignar la acción correspondiente
                btnShowProcedure.setVisible(true); // Hacer visible el botón
                btnShowProcedure.setOnAction(event -> {
                    try {
                        openProcedureFXML(template);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });

        return this;
    }

    // Método que abre el FXML correspondiente dependiendo del template seleccionado
    public void openProcedureFXML(GraphTemplates.GRAPH_TEMPLATE template) throws IOException {
        String fxmlFile = "";

        // Seleccionar el archivo FXML basado en el template
        switch (template) {
            case CARTESIANASABSOLUTAS:
                //fxmlFile = "cartesianas_absolutas.fxml";
                break;
            case POLARESABSOLUTAS:
                fxmlFile = "viewsProcedure/pol-abs.fxml";
                break;
            case CARTESIANASRELATIVAS:
                fxmlFile = "viewsProcedure/cart-relat.fxml";
                break;
            case POLARESRELATIVAS:
                fxmlFile = "viewsProcedure/pol-relat.fxml";
                break;
            default:
                throw new IllegalArgumentException("No FXML available for this template");
        }

        // Cargar el archivo FXML seleccionado
        Stage stage = new Stage();
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Procedimiento");
        stage.show();
    }



    public Graph setFieldOriginX(TextField field_origen_x) {
        field_origen_x.setText(String.valueOf(graphForm.getOrigin().getX()));
        field_origen_x.setOnAction(actionEvent -> {
            double x = Double.parseDouble(field_origen_x.getText());

            // Guardamos el origen anterior antes de actualizar
            graphForm.setPreviousOrigin(graphForm.getOrigin());

            // Cambia el origen en el graphForm
            graphForm.setOrigin(x, graphForm.getOrigin().getY());

            // Recalcula todas las posiciones de los puntos
            graphForm.updatePoints();
            graphForm.updateLines();
            updateComponents();
        });
        this.field_origen_x = field_origen_x;
        return this;
    }


    public Graph setFieldOriginY(TextField field_origen_y) {
        field_origen_y.setText(String.valueOf(graphForm.getOrigin().getY()));
        field_origen_y.setOnAction(actionEvent -> {
            double y = Double.parseDouble(field_origen_y.getText());

            // Guardamos el origen anterior antes de actualizar
            graphForm.setPreviousOrigin(graphForm.getOrigin());

            // Cambia el origen en el graphForm
            graphForm.setOrigin(graphForm.getOrigin().getX(), y);

            // Recalcula todas las posiciones de los puntos
            graphForm.updatePoints();
            graphForm.updateLines();
            updateComponents();
        });
        this.field_origen_y = field_origen_y;
        return this;
    }



    //Colorize

    public void colorizeAxisHorizontal(Color color){
        line_h.setStroke(color);
    }

    public void colorizeAxisVertical(Color color){
        line_v.setStroke(color);
    }

    public void colorizeAxis(Color color){
        colorizeAxisHorizontal(color);
        colorizeAxisVertical  (color);
    }

    public void colorizeDivAxis(int i,Color color){
        Line line = div_list_l.get(i);
        line.setStroke(color);
    }

    public void colorizeDivText(int i,Color color){
        Text text = div_list_t.get(i);
        text.setStroke(color);
    }

    public void colorizeDiv(Color color){
        for(int i = 0; i < div_list_l.size(); i++){
            colorizeDivAxis(i,color);
        }
        for(int i = 0; i < div_list_t.size(); i++){
            colorizeDivText(i,color);
        }
    }

    public void colorizeGraph(ConfigColor color){
        setStyle("-fx-background-color: "+color.getAsHSB()+";");
    }

    public void recolorizeGraph(ConfigColor color){
        setMainColor(color);
        Color alterColor = getAlterColor();
        colorizeGraph(mainColor );
        colorizeAxis (alterColor);
        colorizeDiv  (alterColor);
        if (getColorAuto()) {
            System.out.println("Color");
            getGraphForm().colorize();
        }
    }


    private void openConfigDialog() {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/graficador/views/view-config.fxml"));
            Parent root = loader.load();

            ConfigController controller = loader.getController();
            controller.setGraph(this);
            // Crear un nuevo Stage para el diálogo
            Stage configStage = new Stage();
            configStage.initModality(Modality.APPLICATION_MODAL);
            configStage.initOwner(configToolbar.getScene().getWindow()); // Asocia el diálogo con la ventana principal
            configStage.setTitle("Configuración");

            // Crear la escena y configurar el Stage
            Scene scene = new Scene(root);
            configStage.setScene(scene);

            // Mostrar el diálogo
            configStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores, como mostrar un mensaje al usuario si el FXML no se carga
        }
    }


    public void prepareGraph(double x, double y, double w,double h){
        setLayoutX(x);
        setLayoutY(y);

        setMinWidth (0);
        setMinHeight(0);

        setPrefWidth(w);
        setPrefHeight(h);

        setWidth  (w);
        setHeight (h);

        //ASPECT RATIO WOOOO
        double aspectRatio = w / h;

        // Escala el ancho o la altura dependiendo del aspecto ratio
        if (aspectRatio > 1) {
            // Si el contenedor es más ancho que alto, ajusta la altura proporcionalmente
            graph_height *= (h / w);
        } else {
            // Si el contenedor es más alto que ancho, ajusta el ancho proporcionalmente
            graph_width *= (w / h);
        }

        setGraphOriginX(w*0.5);
        setGraphOriginY(h*0.5);

        drawGraph();

        setOnMousePressed(mouseEvent -> {
            executeCommand(graphState,mouseEvent);
        });

        graphForm.executeGraphFormScript(this);
    }

    public void prepareConfig(){
        setMainColor(config.getBackgroundColor());
        setColorAuto(config.getColorAuto()      );
    }

    //***********************************
    public Graph setLimpiar() {
        setGraphState(GRAPH_COMMANDS.SELECT);
        graphForm.clear();


        return this;
    }
    public void clearTables() {
        // Limpia la tabla de puntos originales
        if (OriginalTable != null) {
            OriginalTable.setItems(FXCollections.observableArrayList()); // Establece una lista vacía
        }

        // Limpia la tabla de puntos trasladados
        if (OriginalTableP != null) {
            OriginalTableP.setItems(FXCollections.observableArrayList()); // Establece una lista vacía
        }
    }

    //FUNCIONES QUE USO PARA LA TRASLACION
    public Graph setFiguraPersonalida(ApplicationControllerTraslacion apt) {
        GraphFigura.GRAPH_FIGURA template = GraphFigura.GRAPH_FIGURA.FIGURAORIGINAL;

        // Ejecuta el método create para graficar con el template CARTESIANASABSOLUTAS
        template.create(this, graphForm, apt);




        return this;
    }
    public Graph setFiguraTrasladada(ApplicationControllerTraslacion apt) {
        GraphFigura.GRAPH_FIGURA template = GraphFigura.GRAPH_FIGURA.FIGURATRASLADADA;

        // Ejecuta el método create para graficar con el template CARTESIANASABSOLUTAS
        template.create(this, graphForm, apt);




        return this;
    }
    public void updateoriginalPointTable(ApplicationControllerTraslacion apt) {
        try {
            if (OriginalTable == null) {
                System.out.println("Error: pointTable no está inicializada.");
                return; // Salir del método si es nulo
            }

            ObservableList<GraphPoint> translatedPoints = FXCollections.observableArrayList(GraphFigura.getPointsOriginales(this, graphForm, apt));
            OriginalTable.setItems(translatedPoints);
            OriginalTable.refresh();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la excepción para depuración
        }
    }
    public void updateTranslatedPointTable(ApplicationControllerTraslacion apt) {
        try {
            if (OriginalTableP == null) {
                System.out.println("Error: pointTableP no está inicializada.");
                return; // Salir del método si es nulo
            }

            ObservableList<GraphPoint> translatedPoints = FXCollections.observableArrayList(GraphFigura.getPointsTrasladados(this, graphForm, apt));
            OriginalTableP.setItems(translatedPoints);
            OriginalTableP.refresh();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la excepción para depuración
        }
    }


    //FUNCIONES QUE USO PARA LA ESCALACION
    public Graph setFiguraPersonalidaS(ApplicationControllerEscalacion apt) {
        GraphFiguraS.GRAPH_FIGURA template = GraphFiguraS.GRAPH_FIGURA.FIGURAORIGINAL;

        // Ejecuta el método create para graficar con el template CARTESIANASABSOLUTAS
        template.create(this, graphForm, apt);




        return this;
    }
    public Graph setFiguraEscalada(ApplicationControllerEscalacion apt) {
        GraphFiguraS.GRAPH_FIGURA template = GraphFiguraS.GRAPH_FIGURA.FIGURAESCALADA;

        // Ejecuta el método create para graficar con el template CARTESIANASABSOLUTAS
        template.create(this, graphForm, apt);



        return this;
    }
    public void updateoriginalPointTableS(ApplicationControllerEscalacion apt) {
        try {
            if (OriginalTable == null) {
                System.out.println("Error: pointTable no está inicializada.");
                return; // Salir del método si es nulo
            }

            ObservableList<GraphPoint> translatedPoints = FXCollections.observableArrayList(GraphFiguraS.getPointsOriginales(this, graphForm, apt));
            OriginalTable.setItems(translatedPoints);
            OriginalTable.refresh();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la excepción para depuración
        }
    }
    public void updateTranslatedPointTableS(ApplicationControllerEscalacion apt) {
        try {
            if (OriginalTableP == null) {
                System.out.println("Error: pointTableP no está inicializada.");
                return; // Salir del método si es nulo
            }

            ObservableList<GraphPoint> translatedPoints = FXCollections.observableArrayList(GraphFiguraS.getPointsEscalados(this, graphForm, apt));
            OriginalTableP.setItems(translatedPoints);
            OriginalTableP.refresh();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la excepción para depuración
        }
    }

    //FUNCIONES QUE USO PARA LA ROTACION
    public Graph setFiguraPersonalidaR(ApplicationControllerRotacion apt) {
        GraphFiguraR.GRAPH_FIGURA template = GraphFiguraR.GRAPH_FIGURA.FIGURAORIGINAL;

        // Ejecuta el método create para graficar con el template CARTESIANASABSOLUTAS
        template.create(this, graphForm, apt);




        return this;
    }
    public Graph setFiguraRotada(ApplicationControllerRotacion apt) {
        GraphFiguraR.GRAPH_FIGURA template = GraphFiguraR.GRAPH_FIGURA.FIGURAROTADA;

        // Ejecuta el método create para graficar con el template CARTESIANASABSOLUTAS
        template.create(this, graphForm, apt);



        return this;
    }
    public void updateoriginalPointTableR(ApplicationControllerRotacion apt) {
        try {
            if (OriginalTable == null) {
                System.out.println("Error: pointTable no está inicializada.");
                return; // Salir del método si es nulo
            }

            ObservableList<GraphPoint> translatedPoints = FXCollections.observableArrayList(GraphFiguraR.getPointsOriginales(this, graphForm, apt));
            OriginalTable.setItems(translatedPoints);
            OriginalTable.refresh();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la excepción para depuración
        }
    }
    public void updateTranslatedPointTableR(ApplicationControllerRotacion apt) {
        try {
            if (OriginalTableP == null) {
                System.out.println("Error: pointTableP no está inicializada.");
                return; // Salir del método si es nulo
            }

            ObservableList<GraphPoint> translatedPoints = FXCollections.observableArrayList(GraphFiguraR.getPointsRotados(this, graphForm, apt));
            OriginalTableP.setItems(translatedPoints);
            OriginalTableP.refresh();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la excepción para depuración
        }
    }

}
