package org.lubbenb.raytracer.shaders;

import java.awt.Color;
import java.awt.Dimension;

import org.lubbenb.math.Vector3D;
import org.lubbenb.raytracer.Ray;




public class Material {
    private Color color;
    private double ri;
    private Texture texture;
    private Texture bumpMap;
    private double transparency;
    private double refractionIndex;
    
    public Material(Color c, double ref){
        color = c;
        ri = ref;
    }
    
    public Material(Texture t, double ref){
    	ri = ref;
    	texture = t;
    }
    
    public void setTransparency(double t){
    	transparency = t;
    }
    
    public void setRefractionIndex(double i){
    	refractionIndex = i;
    }
    
    public double getRefractionIndex(){
    	return refractionIndex;
    }
    
    public double getTransparency(){
    	return transparency;
    }
    
    public void setBumpMap(Texture bm){
    	bumpMap = bm;
    }
    
    public Color getColor(double u, double v){
    	if(texture==null)
    		return color;
    	else
    		return texture.getColor(u, v);
    }
    
    public Ray disturbNormal(double u, double v, Ray normal){
    	if(bumpMap==null){
    		return null;
    	} else {
    		Ray newNormal = new Ray(normal);
    		Dimension size = bumpMap.getSize();
    		Color dc = bumpMap.getColor(u, v);
    		Color du = bumpMap.getColor(u-1/size.getWidth(), v);
    		Color dv = bumpMap.getColor(u, v-1/size.getHeight());
    		double bumpFactor = 50;
    		Vector3D uVector = new Vector3D(1, 0, -((double)(du.getRed()-dc.getRed()))/((double)bumpMap.getSize().width)*bumpFactor);
    		Vector3D vVector = new Vector3D(0, -1, -((double)(dv.getRed()-dc.getRed()))/((double)bumpMap.getSize().height)*bumpFactor);
    		Vector3D disturbedVector = vVector.crossProduct(uVector).normalize();
    		Vector3D newDirection = new Vector3D(disturbedVector.getX()+normal.getDirectionX(),
    				disturbedVector.getY()+normal.getDirectionY(), 
    				1-disturbedVector.getZ()+normal.getDirectionZ());

    		return new Ray(normal.getOrigin(), newDirection);
    	}
    }
    
    public boolean hasBumpMap(){
    	if(bumpMap==null)
    		return false;
    	else
    		return true;
    }
    
    public double getReflections(){
        return ri;
    }
}
