package org.lubbenb.raytracer;

import org.lubbenb.math.Point3D;
import org.lubbenb.math.Vector3D;

public class Ray {
	private Vector3D directionVector;
	private Vector3D originVector;

    public Ray(Ray r){
    	directionVector = new Vector3D(r.getDirectionX(), r.getDirectionY(), r.getDirectionZ());
    	originVector = new Vector3D(r.getOriginX(), r.getOriginY(), r.getOriginZ());
    }
    
    public Ray(Vector3D origin, Vector3D direction){
    	directionVector = direction.normalize();
    	originVector = origin;
    }
    
    public Ray negate(){
    	return new Ray(originVector, directionVector.negate());
    }
    
    public Vector3D getDirection(){
    	return directionVector;
    }
    
    public Vector3D getOrigin(){
    	return originVector;
    }
    
    public double getDirectionX(){
        return directionVector.getX();
    }
    
    public double getDirectionY(){
        return directionVector.getY();
    }
    
    public double getDirectionZ(){
        return directionVector.getZ();
    }
    
    public double getOriginX(){
        return originVector.getX();
    }
    
    public double getOriginY(){
        return originVector.getY();
    }
    
    public double getOriginZ(){
        return originVector.getZ();
    }
    
    public double getOrigen(int i){
    	return originVector.getDirection(i);
    }
    
    public double getDirection(int i){
    	return directionVector.getDirection(i);
    }
    
    public String toString(){
    	return "{"+directionVector.getX()+", "+directionVector.getY()+", "+directionVector.getZ()+"}";
    }
    
    public static Ray createRay(Point3D point1, Point3D point2){
		Vector3D direction = point1.subtract(point2).getVector().normalize();
		Vector3D origin = point1.getVector();
		
		return new Ray(origin, direction);
    }
}