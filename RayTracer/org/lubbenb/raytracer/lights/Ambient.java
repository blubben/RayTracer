package org.lubbenb.raytracer.lights;

import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.RayTracer;

public class Ambient extends Light {
    public Ambient(double xx, double yy, double zz, double i){
        super(xx,yy,zz, i);
    }
    
    public int getNumberOfSamples(){
        return 0;
    }
    
    public double[] getSample(){
        return null;
    }

    public double getIntensity(Ray lightRay) {
        return intensity;
    }
}
