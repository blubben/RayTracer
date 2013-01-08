package org.lubbenb.math;

import java.text.NumberFormat;

import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;

public class Point3D {
	private org.apache.commons.math3.geometry.euclidean.threed.Vector3D vector;

	public Point3D(double xx, double yy, double zz){
		this.vector = new org.apache.commons.math3.geometry.euclidean.threed.Vector3D(xx, yy, zz);
	}
	
	protected Point3D(org.apache.commons.math3.geometry.euclidean.threed.Vector3D vector){
		this.vector = vector;
	}
	
	public Point3D(Vector3D p){
		this(p.getX(), p.getY(), p.getZ());
	}
	
	public Point3D(Point3D p){
		this(p.getX(), p.getY(), p.getZ());
	}
	
	public Vector3D getVector(){
		return new Vector3D(vector);
	}
	
	public double get(int i){
		switch(i){
			case 0:
				return getX();
			case 1:
				return getY();
			case 2:
				return getZ();
		}
		
		return Double.NaN;
	}
	
	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getX()
	 */
	public double getX() {
		return vector.getX();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getY()
	 */
	public double getY() {
		return vector.getY();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getZ()
	 */
	public double getZ() {
		return vector.getZ();
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#add(org.apache.commons.math3.geometry.Vector)
	 */
	public Point3D add(Point3D v) {
		return new Point3D(vector.add(v.vector));
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#subtract(org.apache.commons.math3.geometry.Vector)
	 */
	public Point3D subtract(Point3D v) {
		return new Point3D(vector.subtract(v.vector));
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#distance(org.apache.commons.math3.geometry.Vector)
	 */
	public double distance(Vector<Euclidean3D> v) {
		return vector.distance(v);
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#toString()
	 */
	public String toString() {
		return vector.toString();
	}

	/**
	 * @param format
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#toString(java.text.NumberFormat)
	 */
	public String toString(NumberFormat format) {
		return vector.toString(format);
	}
}

