package org.lubbenb.raytracer.lights;

public class IntensityVector {
    private double redIntensity;
    private double greenIntensity;
    private double blueIntensity;
    
    public IntensityVector(double r, double g, double b){
        redIntensity = r;
        greenIntensity = g;
        blueIntensity = b;
    }
    
    public double getRedIntensity(){
        return redIntensity;
    }
    
    public double getGreenIntensity(){
        return greenIntensity;
    }
    
    public double getBlueIntensity(){
        return blueIntensity;
    }
}
