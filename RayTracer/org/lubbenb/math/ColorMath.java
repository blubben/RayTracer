package org.lubbenb.math;

import java.awt.Color;

import org.lubbenb.raytracer.lights.IntensityVector;


public class ColorMath {
    public static Color addColors(Color a, Color b){
        int red = a.getRed()+b.getRed();
        int green = a.getGreen()+b.getGreen();
        int blue = a.getBlue()+b.getBlue();
        return new Color(red, green, blue);   
    }
    
    public static Color multilpyColors(Color a, Color b){
        int red = a.getRed()*b.getRed();
        int green = a.getGreen()*b.getGreen();
        int blue = a.getBlue()*b.getBlue();
        return new Color(red, green, blue);   
    }
    
    public static Color multiplyIntensity(Color a, IntensityVector b){
        int red = (int)(a.getRed()*b.getRedIntensity());
        int green = (int)(a.getGreen()*b.getGreenIntensity());
        int blue = (int)(a.getBlue()*b.getBlueIntensity());
        return new Color(red, green, blue);   
    }
}
