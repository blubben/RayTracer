package org.lubbenb.raytracer.objects;

import org.lubbenb.math.Point2D;
import org.lubbenb.math.Point3D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.shaders.Material;


public abstract class Object {

    protected Material material;
    protected Point3D[] boundingBox = new Point3D[2];
    protected int id;
    
    public Object(){

    }
    
    public void setMaterial(Material m){
    	material = m;
    }
    
    public void setID(int i){
    	id = i;
    }
    
    public int getID(){
    	return id;
    }
      
    public Material getMaterial(){
        return material;
    }
    
    public Point3D[] getAABB(){
    	return boundingBox;
    }
    
    public Ray getNormal(double xx, double yy, double zz){
    	Point2D uv = getUV(xx, yy, zz);
    	Ray normal = normalAt(xx, yy, zz);
    	
    	if(material.hasBumpMap())
    		normal = material.disturbNormal(uv.getX(), uv.getY(), normal);
    	
    	return normal;
    }
    
    abstract public Intersection shootRay(Ray ray);
    abstract protected Ray normalAt(double xx, double yy, double zz);
    abstract public Point2D getUV(double xx, double yy, double zz);
}
