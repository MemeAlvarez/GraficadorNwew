package com.graficador.utils;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DialogUtils {

    public static Pair<Integer, Double[]> showDialog(List<Pair<String, Class<?>>> fields) {
        Dialog<Pair<Integer, Double[]>> dialog = new Dialog<>();
        dialog.setTitle("Input Dialog");
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Crear un contenedor para los campos de entrada
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Lista para almacenar los TextFields
        List<TextField> textFields = new ArrayList<>();

        for (Pair<String, Class<?>> field : fields) {
            String label = field.getKey();
            TextField textField = new TextField();
            grid.add(new Label(label), 0, grid.getChildren().size());
            grid.add(textField, 1, grid.getChildren().size() - 1);
            textFields.add(textField); // Guardamos el TextField
        }

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == okButtonType) {
                // Depuración: Verificar el valor del primer campo
                System.out.println("Valor del primer campo (punto): " + textFields.get(0).getText());

                // Intentar convertir el valor del primer campo
                Integer pointCount;
                try {
                    pointCount = Integer.parseInt(textFields.get(0).getText());
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir el primer campo a Integer");
                    return null;
                }

                Double[] results = new Double[fields.size() - 1];

                for (int i = 1; i < fields.size(); i++) {
                    String text = textFields.get(i).getText();
                    System.out.println("Valor del campo " + i + ": " + text); // Depuración: Verificar los valores de cada campo
                    try {
                        results[i - 1] = Double.parseDouble(text);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir el campo " + i + " a Double");
                        return null;
                    }
                }

                // Mostrar los resultados obtenidos
                System.out.println("Resultados obtenidos: " + Arrays.toString(results));

                return new Pair<>(pointCount, results);
            }
            return null;
        });

        // Mostrar el diálogo y esperar el resultado
        Optional<Pair<Integer, Double[]>> result = dialog.showAndWait();
        return result.orElse(null);
    }


}
