package org.lubbenb.raytracer.lights;

import org.lubbenb.math.Point3D;
import org.lubbenb.raytracer.Ray;

public class Point extends Light {
    public Point(double xx, double yy, double zz, double i) {
        super(xx, yy, zz, i);
    }

    public int getNumberOfSamples() {
        return 1;
    }

    public Point3D getSample() {
        return new Point3D(getX(),getY(),getZ());
    }

    public double getIntensity(Ray lightRay) {
        return intensity;
    }

}
