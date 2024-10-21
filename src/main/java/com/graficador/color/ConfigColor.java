package com.graficador.color;

import com.graficador.utils.Utils;
import javafx.scene.paint.Color;
import jdk.jshell.execution.Util;

import java.time.temporal.ValueRange;

public class ConfigColor {
    double h;
    double s;
    double v;

    public double getH() {
        return h;
    }

    public double getS() {
        return s;
    }

    public double getV() {
        return v;
    }


    public ConfigColor setH(double h) {
        this.h = h;
        return this;
    }

    public ConfigColor setS(double s) {
        this.s = s;
        return this;
    }

    public ConfigColor setV(double v) {
        this.v = v;
        return this;
    }


    public String getAsHSB(){
        return String.format("hsb(%d, %d%%, %d%%)",Math.round(Math.round(getH()*360)),Math.round(getS()*100),Math.round(getV()*100));
    }

    public Color getAsRGB() {
        double r = 0, g = 0, b = 0;

        if (s == 0) {
            r = g = b = v; // If saturation is 0, return the value as grayscale
        } else {
            int i = (int) Math.floor(h * 6);
            double f = h * 6 - i;
            double p = v * (1 - s);
            double q = v * (1 - f * s);
            double t = v * (1 - (1 - f) * s);
            i = i % 6;

            switch (i) {
                case 0: r = v; g = t; b = p; break;
                case 1: r = q; g = v; b = p; break;
                case 2: r = p; g = v; b = t; break;
                case 3: r = p; g = q; b = v; break;
                case 4: r = t; g = p; b = v; break;
                case 5: r = v; g = p; b = q; break;
            }
        }

        // Return a JavaFX Color object with r, g, b in the range [0, 1]
        return Color.color(r, g, b);
    }

    public Color getAsRGB(double h, double s, double v) {
        double r = 0, g = 0, b = 0;

        if (s == 0) {
            r = g = b = v; // If saturation is 0, return the value as grayscale
        } else {
            int i = (int) Math.floor(h * 6);
            double f = h * 6 - i;
            double p = v * (1 - s);
            double q = v * (1 - f * s);
            double t = v * (1 - (1 - f) * s);
            i = i % 6;

            switch (i) {
                case 0: r = v; g = t; b = p; break;
                case 1: r = q; g = v; b = p; break;
                case 2: r = p; g = v; b = t; break;
                case 3: r = p; g = q; b = v; break;
                case 4: r = t; g = p; b = v; break;
                case 5: r = v; g = p; b = q; break;
            }
        }

        // Return a JavaFX Color object with r, g, b in the range [0, 1]
        return Color.color(r, g, b);
    }

    public Color getAsColor(){
        return getAsRGB();
    }

    public Color getAsColorVariance(double r, double g, double b){
        Color rgb = getAsRGB();

        r *= 2;
        g *= 2;
        b *= 2;

        double nr = Utils.clamp(rgb.getRed  ()*r,0,1);
        double ng = Utils.clamp(rgb.getGreen()*g,0,1);
        double nb = Utils.clamp(rgb.getBlue ()*b,0,1);

        return Color.color(nr, ng, nb);
    }

    public Color getAsColorV(){
        double  value  = Utils.lerp(getV(),1,0.0); // si value es cerca de 0 entonces el color de retorno es mas brillante de lo contrario es mas oscuro
        value -= 0.20;
        value  = Utils.clamp(value,0.0,1.0);
        return getAsRGB(getH(),getS(),value);
    }

    public Color getAsColorV(double brightness){
        brightness = 1.0 - brightness;
        double  value  = Utils.lerp(getV(),1,0.0); // si value es cerca de 0 entonces el color de retorno es mas brillante de lo contrario es mas oscuro
                value -= brightness;
                value  = Utils.clamp(value,0.0,1.0);
        return getAsRGB(getH(),getS(),value);
    }

    public Color getAsColorV(double brightness,double saturation){
        brightness = 1.0 - brightness;
        double  value  = Utils.lerp(getV(),1,0.0); // si value es cerca de 0 entonces el color de retorno es mas brillante de lo contrario es mas oscuro
        value -= brightness;
        value  = Utils.clamp(value,0.0,1.0);
        return getAsRGB(getH(),saturation,value);
    }

    @Override
    public String toString() {
        return "ColorHSV{" +
                "h=" + h +
                ", s=" + s +
                ", v=" + v +
                '}';
    }
}
