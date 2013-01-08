package org.lubbenb.raytracer.shaders;

import java.awt.Color;

import org.lubbenb.math.Point2D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.objects.Object;

public class Lambert extends Shader {
    private double diffuse;
    public Lambert(){
        diffuse = 1;
    }
    
    public Lambert(double d){
        diffuse = d;
    }
    
    public void setDiffuse(double d){
        diffuse = d; 
    }
    
    public double[] calculateIntensity(Ray r1, double lightIntensity, Ray r2, Ray r3){
        double a = r1.getDirectionX()*r2.getDirectionX()+r1.getDirectionY()*r2.getDirectionY()+r1.getDirectionZ()*r2.getDirectionZ();
        a = Math.abs(a);
        
        //if(a>1)
       //     a=1;
        //else if(a<0)
         //   a=0;
        return new double[]{lightIntensity * diffuse * a};
    }

    public Color shade(Object o, Intersection i, double[] intensity, Color reflectionColor, Color transparencyColor) {
    	
    	Material material = o.getMaterial();
    	if(intensity==null)
           intensity = new double[]{0};
        
        if (intensity[0] > 1) intensity[0] = 1;
        else if (intensity[0] < 0) intensity[0] = 0;
        
        Point2D uv = o.getUV(i.getX(), i.getY(), i.getZ());
        Color c = material.getColor(uv.getX(), uv.getY());

        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        
        if(reflectionColor!=null){
            red += (int) (material.getReflections()*reflectionColor.getRed());
            green += (int) (material.getReflections()*reflectionColor.getGreen());
            blue += (int) (material.getReflections()*reflectionColor.getBlue());
        }
        
        if(transparencyColor!=null){
            red += (int) (material.getTransparency()*transparencyColor.getRed());
            green += (int) (material.getTransparency()*transparencyColor.getGreen());
            blue += (int) (material.getTransparency()*transparencyColor.getBlue());
        }

        red *= intensity[0];
        green *= intensity[0];
        blue *= intensity[0];
        
        if(red>255)
            red = 255;
        if(green>255)
            green = 255;
        if(blue>255)
            blue = 255;
        
        return new Color(red, green, blue);
    }

    public double[] averageIntensity(double[] intensity, int n) {
        if(intensity!=null)
            return new double[]{intensity[0]/(double)n};
        else
            return null;
    }

    public double[] addIntensity(double[] i1, double[] i2) {
        double[] result = null;
        if(i1 != null && i2 != null)
            result = new double[]{i1[0]+i2[0]};
        else if(i2 == null && i1 != null)
            result = i1;
        else if(i2 != null && i1 == null)
            result = i2;
        return result;
    }
}
