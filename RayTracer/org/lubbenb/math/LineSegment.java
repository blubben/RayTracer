package org.lubbenb.math;

import org.lubbenb.raytracer.Ray;

public class LineSegment extends Ray {
	private Point3D endPoint;
	
	public LineSegment(){
		super();
	}
	
	public void setEnd(double x, double y, double z){
		if(x<getOriginX()){
			double temp = x;
			x = getOriginX();
			setOrigen(temp, getOriginY(), getOriginZ());
		}
		
		if(y<getOriginY()){
			double temp = y;
			y = getOriginY();
			setOrigen(getOriginX(), y, getOriginZ());
		}
		
		if(z<getOriginZ()){
			double temp = z;
			z = getOriginZ();
			setOrigen(getOriginX(), getOriginY(), z);
		}
		endPoint = new Point3D(x, y, z);
	}
	
	public Point3D getEnd(){
		return endPoint;
	}
}
