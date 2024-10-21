package com.graficador.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.graficador.color.ConfigColor;

import java.io.*;
import java.nio.file.Paths;

public class Configuration {

    public static final String config_route = "/com/graficador/config/config.json";
    private ConfigColor background_color;
    private Boolean     color_auto      ;

    public ConfigColor getBackgroundColor() {
        return background_color;
    }

    public void setBackgroundColor(ConfigColor background_color) {
        this.background_color = background_color;
    }

    public Boolean getColorAuto() {
        return color_auto;
    }

    public void setColorAuto(Boolean color_auto) {
        this.color_auto = color_auto;
    }

    public boolean saveConfiguration(String rutaArchivo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File configFile = new File(rutaArchivo);
        configFile.getParentFile().mkdirs(); // Ensure directories exist

        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(this, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Configuration loadConfiguration(String rutaArchivo) {
        Gson gson = new Gson();
        File configFile = new File(rutaArchivo);

        // Check if file exists
        if (!configFile.exists()) {
            System.out.println("Archivo de configuración no encontrado. Creando un archivo nuevo con valores predeterminados...");

            Configuration defaultConfig = new Configuration();
            defaultConfig.setBackgroundColor(new ConfigColor().setH(0.5).setS(0.5).setV(0.5));
            defaultConfig.setColorAuto(true);

            // Save default config
            if (defaultConfig.saveConfiguration(rutaArchivo)) {
                System.out.println("Archivo de configuración creado en: " + rutaArchivo);
                return defaultConfig;
            } else {
                System.err.println("Error al crear el archivo de configuración.");
                return null;
            }
        }

        try (FileReader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getJarDir() {
        try {
            return Paths.get(Configuration.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
