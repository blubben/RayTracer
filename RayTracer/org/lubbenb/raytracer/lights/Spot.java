package org.lubbenb.raytracer.lights;

import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.RayTracer;

public class Spot extends Light {

    Ray direction;
    
    double angle;
    
    public Spot(double xx, double yy, double zz, double i, Ray d, double a) {
        super(xx, yy, zz, i);
        direction = d;
        angle = a;
    }

    public int getNumberOfSamples() {
        return 1;
    }

    public double[] getSample() {
        
        return new double[]{getX(),getY(),getZ(),};
    }

    public double getIntensity(Ray lightRay) {
        double a = (direction.getDirectionX() * lightRay.getDirectionX() + direction.getDirectionY()
                * lightRay.getDirectionY() + direction.getDirectionZ() * lightRay.getDirectionZ());
        double b = Math.acos(a) * (180.0 / Math.PI);
        //System.out.println(Math.abs(b)/angle);
        if(Math.abs(b)<=angle)
            return Math.acos(b/angle);
        else
            return 0;
    }

}
