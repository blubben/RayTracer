package org.lubbenb.math;

import java.text.NumberFormat;

public class Point2D {
	private org.apache.commons.math3.geometry.euclidean.twod.Vector2D vector;
	
	public Point2D(double x, double y) {
		this.vector = new org.apache.commons.math3.geometry.euclidean.twod.Vector2D(x, y);
	}
	
	protected Point2D(org.apache.commons.math3.geometry.euclidean.twod.Vector2D vector) {
		this.vector = vector;
	}
	
	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.twod.Vector2D#getX()
	 */
	public double getX() {
		return vector.getX();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.twod.Vector2D#getY()
	 */
	public double getY() {
		return vector.getY();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.twod.Vector2D#toArray()
	 */
	public double[] toArray() {
		return vector.toArray();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.twod.Vector2D#hashCode()
	 */
	public int hashCode() {
		return vector.hashCode();
	}

	/**
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.twod.Vector2D#toString()
	 */
	public String toString() {
		return vector.toString();
	}

	/**
	 * @param format
	 * @return
	 * @see org.apache.commons.math3.geometry.euclidean.twod.Vector2D#toString(java.text.NumberFormat)
	 */
	public String toString(NumberFormat format) {
		return vector.toString(format);
	}
}
