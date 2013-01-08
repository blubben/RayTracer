package org.lubbenb.raytracer.lights;

import org.lubbenb.math.Point3D;
import org.lubbenb.raytracer.Ray;


public abstract class Light {
    
    private double x;
    private double y;
    private double z;
    protected double intensity;
    
    public Light(double xx, double yy, double zz, double i){
        x = xx;
        y = yy;
        z = zz;
        intensity = i;
    }
    
    public abstract int getNumberOfSamples();
    
    public abstract Point3D getSample();
    
    public abstract double getIntensity(Ray lightRay);
    
    public void setX(double xx){
        x = xx;
    }
    public void setY(double yy){
        y = yy;
    }
    public void setZ(double zz){
        z = zz;
    }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
}
