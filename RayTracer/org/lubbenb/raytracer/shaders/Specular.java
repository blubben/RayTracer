package org.lubbenb.raytracer.shaders;

import java.awt.Color;

import org.lubbenb.math.Point2D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.objects.Object;



public class Specular extends Shader {

    private double diffuse;
    private double specular;

    public Specular() {
        diffuse = 1;
    }

    public Specular(double d, double s) {
        diffuse = d;
        specular = s;
    }

    public void setDiffuse(double d) {
        diffuse = d;
    }

    public void setSpecular(double s) {
        specular = s;
    }
    
    public double[] calculateIntensity(Ray r1, double lightIntensity, Ray normal, Ray r3) {      
        Ray reflection = new Ray();

        reflection.setOrigen(normal.getOriginX(), normal.getOriginY(), normal
                .getOriginZ());

        double angle = 2 * (-r1.getDirectionX() * normal.getDirectionX() - r1.getDirectionY()
                * normal.getDirectionY() - r1.getDirectionZ() * normal.getDirectionZ());

        reflection.setDir(angle * normal.getDirectionX() + r1.getDirectionX(),
                angle * normal.getDirectionY() + r1.getDirectionY(), angle
                        * normal.getDirectionZ() + r1.getDirectionZ());
        
        double a = reflection.getDirectionX()*r3.getDirectionX()+reflection.getDirectionY()*r3.getDirectionY()+reflection.getDirectionZ()*r3.getDirectionZ();
        //System.out.println(a*(180/Math.PI));
        if(a<0)
           a=0;
        return new double[]{Math.pow(a, specular)};
    }

    public Color shade(Object o, Intersection i, double[] intensity,
            Color reflectionColor, Color transparencyColor) {
        
    	Material material = o.getMaterial();
    	
        if(intensity==null)
            intensity = new double[]{0};
        
        if (intensity[0] > 1) intensity[0] = 1;
        else if (intensity[0] < 0) intensity[0] = 0;
        
        Point2D uv = o.getUV(i.getX(), i.getY(), i.getZ());
        Color c = material.getColor(uv.getX(), uv.getY());
        if(reflectionColor!=null){
            int red = (int) ((c.getRed()+(material.getReflections()*reflectionColor.getRed())));
            int green = (int) ((c.getGreen()+(material.getReflections()*reflectionColor.getGreen())));
            int blue = (int) ((c.getBlue()+(material.getReflections()*reflectionColor.getBlue())));
            if(red>255)
                red = 255;
            if(green>255)
                green = 255;
            if(blue>255)
                blue = 255;
            return new Color((int)(intensity[0]*red),(int)(intensity[0]*green),(int)(intensity[0]*blue));
        } else {
            return new Color((int)(intensity[0]*255),(int)(intensity[0]*255),(int)(intensity[0]*255));
        }
    }

    public double[] averageIntensity(double[] intensity, int n) {
        return new double[]{intensity[0]/n, intensity[1]/n};
    }

    public double[] addIntensity(double[] i1, double[] i2) {
        double[] result = null;
        if(i1 != null && i2 != null)
            result = new double[]{i1[0]+i2[0],i1[1]+i2[1]};
        else if(i2 == null && i1 != null)
            result = i1;
        else if(i2 != null && i1 == null)
            result = i2;
        return result;
    }

}
