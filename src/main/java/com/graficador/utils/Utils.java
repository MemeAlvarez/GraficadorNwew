package com.graficador.utils;

public class Utils {

    public static double clamp(double v, double x1, double x2){
        if (v <= x1){
            return x1;
        }else if (v >= x2){
            return x2;
        }else{
            return v;
        }
    }

    public static double lerp(double v, double x1, double x2){
        return x1 + (x2-x1)*v;
    }

    public static double degToRad(double deg){
        return deg * Math.PI /180;
    }

    public static double radToDeg(double rad){
        return  rad * 180 / Math.PI;
    }

    public static double cosDegToRad(double deg){
        return Math.cos(degToRad(deg));
    }

    public static double sinDegToRad(double deg){
        return Math.sin(degToRad(deg));
    }

    public static double cosRadToDeg(double deg){
        return Math.cos(radToDeg(deg));
    }

    public static double sinRadToDeg(double deg){
        return Math.sin(radToDeg(deg));
    }
}
