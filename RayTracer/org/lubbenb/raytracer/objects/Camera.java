package org.lubbenb.raytracer.objects;

import java.awt.Dimension;

import org.lubbenb.math.Point3D;
import org.lubbenb.math.Vector3D;


public class Camera {
	private Vector3D origin;
	private Vector3D angle;
    
    private double lensDistance;
    
    private Dimension lensDimensions; 
    
    public Camera(double x, double y, double z, Dimension d, double ax, double ay, double az, double l){
        this(new Vector3D(x, y, z), new Vector3D(ax, ay, ax), d, l);
    }
    
    public Camera(Vector3D origin, Vector3D angle, Dimension lensDimensions, double lensDistance) {
    	this.origin = origin;
    	this.angle = angle;
    	
        this.lensDistance = lensDistance;
        this.lensDimensions = lensDimensions;
    }
    
    public Vector3D getOrigin(){
    	return origin;
    }
    
    public void lookAt(Point3D p){
    	Vector3D look = origin.subtract(p.getVector()).normalize();
    //	double d = Math.sqrt(look.getY()*look.getY()+look.getZ()*look.getZ());
       	double d2 = Math.sqrt(look.getX()*look.getX()+look.getZ()*look.getZ());
    	double yAngle = Math.acos(-look.getZ()/d2)*(180/Math.PI);
    	double xAngle = Math.acos(d2)*(180/Math.PI);
    	if(Double.isNaN(yAngle))
    		yAngle = 0;
    	if(Double.isNaN(xAngle))
    		xAngle = 0;
    	if(look.getX()<0)
    		yAngle = -yAngle;
    	if(look.getY()<0)
    		xAngle = -xAngle;
    	
    	angle = new Vector3D(xAngle, yAngle, angle.getZ());
    }
    
    public double getX(){
        return origin.getX();
    }
    
    public double getY(){
        return origin.getY();
    }
    
    public double getZ(){
        return origin.getZ();
    }
    
    public double getXAngle(){
        return angle.getX();
    }
    
    public double getYAngle(){
        return angle.getY();
    }
    
    public double getZAngle(){
        return angle.getZ();
    }
    
    public double getLensDistance(){
        return lensDistance;
    }
    
    public Dimension getDimensions(){
        return lensDimensions;
    }
    
    public int getWidth(){
        return (int)lensDimensions.getWidth();
    }
    
    public int getHeight(){
        return (int)lensDimensions.getHeight();
    }
}
