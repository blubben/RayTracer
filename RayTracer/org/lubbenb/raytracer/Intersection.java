package org.lubbenb.raytracer;

import org.lubbenb.math.Point3D;

public class Intersection {
    private Point3D intersection;
    private double intersect;
    private int objectID;
    
    public Intersection(int objectID, Point3D intersection, double intersect){
    	this.objectID = objectID;
    	this.intersection = intersection;
    	this.intersect = intersect;
    }
    
    public Intersection(double x, double y, double z){
    	this(-1, new Point3D(x,y,z), Double.MIN_VALUE);
    }
    
    public Intersection(double x, double y, double z, double i){
    	this(-1, new Point3D(x,y,z), i);
    }
    
    public Intersection(Intersection i){
        this(i.getObjectID(), i.getIntersection(), i.getIntersect());
    }
    
    /**
	 * Constructor for Intersection object.
	 */
	public Intersection(Point3D intersection, double intersect) {
		this(-1, intersection, intersect);
	}

	public Point3D getIntersection(){
    	return intersection;
    }
    
    public void setIntersection(Point3D intersection){
    	this.intersection = intersection;
    }
    
    public void setIntersect(double i){
    	intersect = i;
    }
    
    public void setObjectID(int id){
    	objectID = id;
    }
    
    public int getObjectID(){
        return objectID;
    }
    
    public double getX(){
        return intersection.getX();
    }
    
    public double getY(){
        return intersection.getY();
    }
    
    public double getZ(){
        return intersection.getZ();
    }
    
    public double getIntersect(){
        if(intersect==Double.NaN)
            System.err.println("Error");
        return intersect;
    }
}
