package org.lubbenb.raytracer.lights;

import java.util.Random;

import org.lubbenb.math.Point3D;
import org.lubbenb.raytracer.Ray;


public class Area extends Light {

    private int length;
    private int width;
    private int height;
    private Random generator;
    
    public Area(double xx, double yy, double zz, int l, int w, int h, double i) {
        super(xx, yy, zz, i);
        generator = new Random(System.currentTimeMillis());
        length = l;
        width = w;
        height = h;
    }

    public int getNumberOfSamples() {
        return 100;
    }

    public Point3D getSample() {
        return new Point3D((getX()-length/2)+generator.nextInt(length), 
                (getY()-width/2)+generator.nextInt(width), 
                (getZ()-height/2)+generator.nextInt(height));
    }

    public double getIntensity(Ray lightRay) {
        return intensity;
    }

}
