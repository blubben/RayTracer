package org.lubbenb.raytracer.shaders;

import java.awt.Color;

import org.lubbenb.math.Point2D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.objects.Object;



public class Phong extends Shader {

    private double diffuseConstant;
    private double specularConstant;
    private double specularPower;

    public Phong() {
        diffuseConstant = 1;
    }

    public Phong(double d, double sp, double sc) {
        diffuseConstant = d;
        specularConstant = sc;
        specularPower = sp;
    }
    
    public double[] addIntensity(double[] i1, double[] i2){        
        double[] result = null;
        if(i1 != null && i2 != null)
            result = new double[]{i1[0]+i2[0],i1[1]+i2[1]};
        else if(i2 == null && i1 != null)
            result = i1;
        else if(i2 != null && i1 == null)
            result = i2;
        return result;
    }
    
    public double[] calculateIntensity(Ray r1, double lightIntensity, Ray normal, Ray r3) {      
        // Calculate specular

        double angle = 2 * (-r1.getDirectionX() * normal.getDirectionX() - r1.getDirectionY()
                * normal.getDirectionY() - r1.getDirectionZ() * normal.getDirectionZ());
        
        Ray reflection = new Ray(normal.getOrigin(), normal.getDirection().scalarMultiply(angle).add(r1.getDirection()));
        
        double specularAngle = reflection.getDirectionX()*r3.getDirectionX()+reflection.getDirectionY()*r3.getDirectionY()+reflection.getDirectionZ()*r3.getDirectionZ();
        
        // Calculate diffuse
        double diffuseAngle = r1.getDirectionX()*normal.getDirectionX()+r1.getDirectionY()*normal.getDirectionY()+r1.getDirectionZ()*normal.getDirectionZ();
        
        if(specularAngle<0||Double.isNaN(specularAngle)) specularAngle=0;
        
        return new double[]{lightIntensity * diffuseConstant * diffuseAngle, lightIntensity * specularConstant * Math.pow(specularAngle, specularPower)};
    }

    public Color shade(Object o, Intersection i, double[] intensity,
            Color reflectionColor, Color transparencyColor) {
        
    	Material material = o.getMaterial();
    	
        if(intensity==null)
            intensity = new double[]{0,0};
        
        if (intensity[0] > 1) intensity[0] = 1;
        else if (intensity[0] < 0) intensity[0] = 0;
        
        if (intensity[1] > 1) intensity[1] = 1;
        else if (intensity[1] < 0) intensity[1] = 0;
        
        double lightIntensity = intensity[0];
        double specularIntensity = intensity[1];
        
        //System.out.println(specularIntensity);
        Point2D uv = o.getUV(i.getX(), i.getY(), i.getZ()); 	
        Color c = material.getColor(uv.getX(), uv.getY());

        if(reflectionColor!=null){
            int red = (int) (lightIntensity*(c.getRed() + material.getReflections()*reflectionColor.getRed()));
            int green = (int) (lightIntensity*(c.getGreen() + material.getReflections()*reflectionColor.getGreen()));
            int blue = (int) (lightIntensity*(c.getBlue() + material.getReflections()*reflectionColor.getBlue()));

            red = red+(int)(specularIntensity*255);
            green = green+(int)(specularIntensity*255);
            blue = blue+(int)(specularIntensity*255);

            if(red>255)
                red = 255;
            if(green>255)
                green = 255;
            if(blue>255)
                blue = 255;
            
            return new Color(red,green,blue);
        } else {
            int red = (int)(lightIntensity*c.getRed()+(specularIntensity*255));
            int green = (int)(lightIntensity*c.getGreen()+(specularIntensity*255));
            int blue = (int)(lightIntensity*c.getBlue()+(specularIntensity*255));
            
            if(red>255)
                red = 255;
            if(green>255)
                green = 255;
            if(blue>255)
                blue = 255;
            //System.out.println(red+" "+green+" "+blue);
            return new Color(red, green, blue);
        }
    }

    public double[] averageIntensity(double[] intensity, int n) {
        if(intensity!=null)
            return new double[]{intensity[0]/(double)n, intensity[1]/(double)n};
        else
            return null;
    }
}
