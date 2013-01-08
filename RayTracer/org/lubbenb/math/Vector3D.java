package org.lubbenb.math;

import java.text.NumberFormat;

import org.apache.commons.math3.geometry.Space;

/**
 * Vector3D
 *
 * This class is a wrapper around the apache math vector3D.
 * 
 * I'm not sure if this is a good idea or not.  It gives me the power of the apache library but allows me to add
 * my own methods and possible change the underlying library if necessary later.
 *
 * @author Brian Lubben
 * @date Dec 27, 2012
 *
 */
public class Vector3D {
	private org.apache.commons.math3.geometry.euclidean.threed.Vector3D vector;

	protected Vector3D(org.apache.commons.math3.geometry.euclidean.threed.Vector3D vector){
		this.vector = vector;
	}
	
	public Vector3D(double xx, double yy, double zz){
		vector = new org.apache.commons.math3.geometry.euclidean.threed.Vector3D(xx, yy, zz);
	}
	
	public Vector3D(Vector3D t){
		this(t.getX(), t.getY(), t.getZ());
	}

    public double getDirection(int i){
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
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#toArray()
	 */
	public double[] toArray() {
		return vector.toArray();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getSpace()
	 */
	public Space getSpace() {
		return vector.getSpace();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getZero()
	 */
	public Vector3D getZero() {
		return new Vector3D(vector.getZero());
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getNorm1()
	 */
	public double getNorm1() {
		return vector.getNorm1();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getNorm()
	 */
	public double getNorm() {
		return vector.getNorm();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getNormSq()
	 */
	public double getNormSq() {
		return vector.getNormSq();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getNormInf()
	 */
	public double getNormInf() {
		return vector.getNormInf();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getAlpha()
	 */
	public double getAlpha() {
		return vector.getAlpha();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#getDelta()
	 */
	public double getDelta() {
		return vector.getDelta();
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#add(org.apache.commons.math3.geometry.Vector)
	 */
	public Vector3D add(Vector3D v) {
		return new Vector3D(vector.add(v.vector));
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#subtract(org.apache.commons.math3.geometry.Vector)
	 */
	public Vector3D subtract(Vector3D v) {
		return new Vector3D(vector.subtract(v.vector));
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#normalize()
	 */
	public Vector3D normalize() {
		try {
			return new Vector3D(vector.normalize());
		} catch(Exception e){
			return new Vector3D(vector);
		}
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#orthogonal()
	 */
	public Vector3D orthogonal() {
		return new Vector3D(vector.orthogonal());
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#negate()
	 */
	public Vector3D negate() {
		return new Vector3D(vector.negate());
	}

	/**
	 * @param a
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#scalarMultiply(double)
	 */
	public Vector3D scalarMultiply(double a) {
		return new Vector3D(vector.scalarMultiply(a));
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#isNaN()
	 */
	public boolean isNaN() {
		return vector.isNaN();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#isInfinite()
	 */
	public boolean isInfinite() {
		return vector.isInfinite();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#hashCode()
	 */
	public int hashCode() {
		return vector.hashCode();
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#dotProduct(org.apache.commons.math3.geometry.Vector)
	 */
	public double dotProduct(Vector3D v) {
		return vector.dotProduct(v.vector);
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#crossProduct(org.apache.commons.math3.geometry.Vector)
	 */
	public Vector3D crossProduct(Vector3D v) {
		return new Vector3D(vector.crossProduct(v.vector));
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#distance1(org.apache.commons.math3.geometry.Vector)
	 */
	public double distance1(Vector3D v) {
		return vector.distance1(v.vector);
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#distance(org.apache.commons.math3.geometry.Vector)
	 */
	public double distance(Vector3D v) {
		return vector.distance(v.vector);
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#distanceInf(org.apache.commons.math3.geometry.Vector)
	 */
	public double distanceInf(Vector3D v) {
		return vector.distanceInf(v.vector);
	}

	/**
	 * @param v
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.threed.Vector3D#distanceSq(org.apache.commons.math3.geometry.Vector)
	 */
	public double distanceSq(Vector3D v) {
		return vector.distanceSq(v.vector);
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
