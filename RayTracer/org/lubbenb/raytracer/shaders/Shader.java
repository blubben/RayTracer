package org.lubbenb.raytracer.shaders;

import java.awt.Color;

import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.objects.Object;



public abstract class Shader {
    public Shader(){
        
    }
    
    abstract public double[] calculateIntensity(Ray lightRay, double lightIntensity, Ray normal, Ray view);
    abstract public Color shade(Object o, Intersection i, double[] intensity, Color reflectionColor, Color transparencyColor);
    abstract public double[] averageIntensity(double[] intensity, int n);
    abstract public double[] addIntensity(double[] i1, double[] i2);
}
