package com.graficador.graphic;

import com.graficador.utils.DialogUtils;
import com.graficador.utils.Utils;
import javafx.util.Pair;

import java.util.List;

public class GraphTemplates {

    @FunctionalInterface
    public interface GraphTemplateAction {
        void apply(Graph graph, GraphForm graphForm);
    }

    public enum GRAPH_TEMPLATE {

        RECTANGULO ("Rectangulo", GraphTemplates::createRectangulo),
        TRIANGULO  ("Triangulo" , GraphTemplates::createTriangulo ),
        CIRCULO    ("Circulo"   , GraphTemplates::createCirculo   ),
        ELIPSE     ( "Elipse"   , GraphTemplates::createElipse    ),
        ARCO       ("Arco"      , GraphTemplates::createArco      ),

        LINEA ("linea",GraphTemplates::createLinea),

        CARTESIANASABSOLUTAS("Figura Personalizada",GraphTemplates::createCartAbs),
        POLARESABSOLUTAS("Figura Personalizada",GraphTemplates::createPolAbs),
        CARTESIANASRELATIVAS("Figura Personalizada",GraphTemplates::createCartRel),
        POLARESRELATIVAS("Figura Personalizada",GraphTemplates::createPolRel)
        ;

        String   name  ;
        GraphTemplateAction create;

        GRAPH_TEMPLATE(String name,GraphTemplateAction create){
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

        public void create(Graph graph, GraphForm graphForm){
            create.apply(graph,graphForm);
        }

    }

    public static void createRectangulo(Graph graph, GraphForm graphForm) {
        List<Pair<String, Class<?>>> fields = List.of(
                new Pair<>("Puntos", Integer.class),  // Renombrado a "Puntos"
                new Pair<>("Base", Double.class),
                new Pair<>("Altura", Double.class)
        );

        Pair<Integer, Double[]> result = DialogUtils.showDialog(fields);

        if (result != null) {
            Integer pointCount = result.getKey();
            Double base = result.getValue()[0];   // Primer valor: base
            Double altura = result.getValue()[1]; // Segundo valor: altura

            System.out.println("Puntos: " + pointCount + ", Base: " + base + ", Altura: " + altura);

            // Aquí va el código para crear el rectángulo
            GraphPoint[] points = new GraphPoint[4];
            points[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P0");
            points[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P1");
            points[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P2");
            points[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P3");

            points[0].setXY(-base * 0.5, 0);
            points[1].setXY(base * 0.5, 0);
            points[2].setXY(base * 0.5, altura);
            points[3].setXY(-base * 0.5, altura);

            for (GraphPoint point : points) {
                graphForm.addPoint(point);
            }

            // Conectar los puntos para formar el rectángulo
            graphForm.addLine(((GraphLine)new GraphLine(points[0], points[1]).setMainGraph(graph)).setName("L0"));
            graphForm.addLine(((GraphLine)new GraphLine(points[1], points[2]).setMainGraph(graph)).setName("L1"));
            graphForm.addLine(((GraphLine)new GraphLine(points[2], points[3]).setMainGraph(graph)).setName("L2"));
            graphForm.addLine(((GraphLine)new GraphLine(points[3], points[0]).setMainGraph(graph)).setName("L3"));
        }
    }



    public static void createTriangulo(Graph graph, GraphForm graphForm) {
        List<Pair<String, Class<?>>> fields = List.of(
                new Pair<>("Puntos", Integer.class),
                new Pair<>("Base"  , Double.class),
                new Pair<>("Altura", Double.class)
        );

        Pair<Integer, Double[]> result = DialogUtils.showDialog(fields);

        if (result != null) {
            double base = result.getValue()[0];
            double altura = result.getValue()[1];
            System.out.println("A");
            // Define los 3 puntos del triángulo
            GraphPoint[] points = new GraphPoint[3];
            points[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P0");
            points[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P1");
            points[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P2");

            points[0].setXY(-base*0.5,0);
            points[1].setXY( base*0.5,0);
            points[2].setXY( 0,altura);

            for (GraphPoint point : points) {
                graphForm.addPoint(point);
            }

            // Conecta los puntos para formar el triángulo
            graphForm.addLine(((GraphLine)new GraphLine(points[0], points[1]).setMainGraph(graph)).setName("L0"));
            graphForm.addLine(((GraphLine)new GraphLine(points[1], points[2]).setMainGraph(graph)).setName("L1"));
            graphForm.addLine(((GraphLine)new GraphLine(points[2], points[0]).setMainGraph(graph)).setName("L2"));
        }
    }

    public static void createCirculo(Graph graph, GraphForm graphForm){

        List<Pair<String, Class<?>>> fields = List.of(
                new Pair<>("PointCount", Integer.class),
                new Pair<>("Radio", Double.class)
        );

        Pair<Integer, Double[]> result = DialogUtils.showDialog(fields);

        if (result != null) {
            int point_count      = result.getKey();
            double r1            = result.getValue()[0];
            GraphPoint lastPoint = null;

            for (int i = 0; i < point_count; i++) {
                GraphPoint point = ((GraphPoint) new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P" + i);
                double ang = ((double) i / point_count) * 360;
                double x = r1 * Utils.cosDegToRad(ang);
                double y = r1 * Utils.sinDegToRad(ang);
                point.setXY(x, y);

                if (lastPoint != null) {
                    GraphLine line = ((GraphLine) new GraphLine(lastPoint, point).setMainGraph(graph)).setName("L" + (i - 1));
                    graphForm.addLine(line);
                }
                lastPoint = point;
                graphForm.addPoint(point);
            }
            GraphLine line = ((GraphLine) new GraphLine(lastPoint, graphForm.getPoints().get(0)).setMainGraph(graph)).setName("L" + (point_count));
            graphForm.addLine(line);
        }
    }

    public static void createElipse(Graph graph,GraphForm graphForm){

        List<Pair<String, Class<?>>> fields = List.of(
                new Pair<>("PointCount", Integer.class),
                new Pair<>("Radio1", Double.class),
                new Pair<>("Radio2", Double.class)
        );

        Pair<Integer, Double[]> result = DialogUtils.showDialog(fields);

        if (result != null) {
            int point_count = result.getKey();
            Double[] params = result.getValue();
            double r1 = params[0];
            double r2 = params[1];
            GraphPoint lastPoint = null;

            for (int i = 0; i < point_count; i++) {
                GraphPoint point = ((GraphPoint) new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P" + i);
                double ang = ((double) i / point_count) * 360;
                double x = r1 * Utils.cosDegToRad(ang);
                double y = r2 * Utils.sinDegToRad(ang);
                point.setXY(x, y);

                if (lastPoint != null) {
                    GraphLine line = ((GraphLine) new GraphLine(lastPoint, point).setMainGraph(graph)).setName("L" + (i - 1));
                    graphForm.addLine(line);
                }
                lastPoint = point;
                graphForm.addPoint(point);
            }
            GraphLine line = ((GraphLine) new GraphLine(lastPoint, graphForm.getPoints().get(0)).setMainGraph(graph)).setName("L" + (point_count));
            graphForm.addLine(line);
        }
    }

    public static void createArco(Graph graph,GraphForm graphForm){

        List<Pair<String, Class<?>>> fields = List.of(
                new Pair<>("PointCount", Integer.class),
                new Pair<>("Radio1", Double.class),
                new Pair<>("Radio2", Double.class),
                new Pair<>("Angle", Double.class)
        );

        Pair<Integer, Double[]> result = DialogUtils.showDialog(fields);

        if (result != null) {
            int point_count = result.getKey();
            Double[] params = result.getValue();

            double r1    = params[0];
            double r2    = params[1];
            double ang_s = params[2];

            GraphPoint lastPoint = null;

            for (int i = 0; i < point_count; i++) {
                GraphPoint point = ((GraphPoint) new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P" + i);
                double ang = ang_s + ((double) i / (point_count - 1)) * 180 - 90;
                double x = r1 * Utils.cosDegToRad(ang);
                double y = r2 * Utils.sinDegToRad(ang);
                point.setXY(x, y);

                if (lastPoint != null) {
                    GraphLine line = ((GraphLine) new GraphLine(lastPoint, point).setMainGraph(graph)).setName("L" + (i - 1));
                    graphForm.addLine(line);
                }
                lastPoint = point;
                graphForm.addPoint(point);
            }
        }

    }

    public static void createLinea(Graph graph, GraphForm graphForm){
        List<Pair<String, Class<?>>> fields = List.of(
                new Pair<>("PointCount", Integer.class),
                new Pair<>("X Inicial" , Double .class),
                new Pair<>("Y Inicial" , Double .class),
                new Pair<>("X Final"   , Double .class),
                new Pair<>("Y Final"   , Double .class)
        );

        Pair<Integer, Double[]> result = DialogUtils.showDialog(fields);

        if (result != null) {
            Double[] params = result.getValue();

            int point_count = result.getKey();

            double start_x = params[0];
            double start_y = params[1];

            double end_x = params[2];
            double end_y = params[3];

            GraphPoint lastPoint = null;

            for (int i = 0; i < point_count; i++) {
                GraphPoint point = ((GraphPoint) new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P" + i);
                double factor = ((double) (i) / point_count);

                double x = Utils.lerp(factor, start_x, end_x);
                double y = Utils.lerp(factor, start_y, end_y);

                point.setXY(x, y);

                if (lastPoint != null) {
                    GraphLine line = ((GraphLine) new GraphLine(lastPoint, point).setMainGraph(graph)).setName("L" + (i - 1));
                    graphForm.addLine(line);
                }
                lastPoint = point;
                graphForm.addPoint(point);
            }
        }

    }

    public static void createCartAbs(Graph graph, GraphForm graphForm){

        GraphPoint[] points = new GraphPoint[8];
        points[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P0");
        points[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P1");
        points[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P2");
        points[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P3");
        points[4] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P4");
        points[5] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P5");
        points[6] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P6");
        points[7] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P7");

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

    public static void createPolAbs(Graph graph, GraphForm graphForm){

        GraphPoint[] points = new GraphPoint[8];
        points[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P0");
        points[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P1");
        points[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P2");
        points[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P3");
        points[4] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P4");
        points[5] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P5");
        points[6] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P6");
        points[7] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P7");

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

    public static void createCartRel(Graph graph, GraphForm graphForm){

        GraphPoint[] points = new GraphPoint[8];
        points[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P0");
        points[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P1");
        points[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P2");
        points[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P3");
        points[4] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P4");
        points[5] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P5");
        points[6] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P6");
        points[7] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P7");

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

    public static void createPolRel(Graph graph, GraphForm graphForm){

        GraphPoint[] points = new GraphPoint[8];
        points[0] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P0");
        points[1] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P1");
        points[2] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P2");
        points[3] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P3");
        points[4] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P4");
        points[5] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P5");
        points[6] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P6");
        points[7] = ((GraphPoint)new GraphPoint(0, 0).setMainGraph(graph)).setOrigin(graphForm.getOrigin()).setName("P7");

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

}
