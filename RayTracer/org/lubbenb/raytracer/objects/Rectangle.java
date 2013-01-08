package org.lubbenb.raytracer.objects;

import java.awt.Color;
import java.awt.Point;

import org.lubbenb.math.Point2D;
import org.lubbenb.math.Point3D;
import org.lubbenb.math.Vector3D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.shaders.Material;
import org.lubbenb.raytracer.shaders.Shader;


public class Rectangle extends Object{
	private Point3D one;
	private Point3D two;
	private Point3D three;
	private Point3D four;

	public Rectangle(Point3D p1, Point3D p2, Point3D p3, Point3D p4, Material m) {
		one = p1;
		two = p2;
		three = p3;
		four = p4;
		material = m;
		
		computeMaxMin();
	}
	
	public void computeMaxMin(){
		Point3D p1, p2, p3, p4;
		p1 = one; p2 = two; p3 = three; p4 = four;
		
		double maxX = p1.getX();
		double minX = p1.getX();
		double maxY = p1.getY();
		double minY = p1.getY();
		double maxZ = p1.getZ();
		double minZ = p1.getZ();
		if(p2.getX()>maxX)
			maxX = p2.getX();
		if(p2.getY()>maxY)
			maxY = p2.getY();
		if(p2.getZ()>maxZ)
			maxZ = p2.getZ();
		
		if(p2.getX()<minX)
			minX = p2.getX();
		if(p2.getY()<minY)
			minY = p2.getY();
		if(p2.getZ()<minZ)
			minZ = p2.getZ();
		
		if(p3.getX()>maxX)
			maxX = p3.getX();
		if(p3.getY()>maxY)
			maxY = p3.getY();
		if(p3.getZ()>maxZ)
			maxZ = p3.getZ();
		
		if(p3.getX()<minX)
			minX = p3.getX();
		if(p3.getY()<minY)
			minY = p3.getY();
		if(p3.getZ()<minZ)
			minZ = p3.getZ();
		
		if(p4.getX()>maxX)
			maxX = p4.getX();
		if(p4.getY()>maxY)
			maxY = p4.getY();
		if(p4.getZ()>maxZ)
			maxZ = p4.getZ();
		
		if(p4.getX()<minX)
			minX = p4.getX();
		if(p4.getY()<minY)
			minY = p4.getY();
		if(p4.getZ()<minZ)
			minZ = p4.getZ();
		
		boundingBox[0] = new Point3D(minX, minY, minZ);
		boundingBox[1] = new Point3D(maxX, maxY, maxZ);
	}

	public Ray normalAt(double xx, double yy, double zz) {
		Vector3D v = two.cross(one, three);
		v.normalize();
		Ray normal = new Ray();
		normal.setOrigen(xx, yy, zz);

		normal.setDir(v.getX(), v.getY(), v.getZ());

		return normal;
	}

	public Point3D getOne() {
		return one;
	}

	public Point3D getTwo() {
		return two;
	}

	public Point3D getThree() {
		return three;
	}

	public Point3D getFour() {
		return four;
	}
	
	public Intersection shootRay(Ray ray) {
		Vector3D normal = two.cross(one, three);
		
		double d = normal.getX() * one.getX() + normal.getY() * one.getY() + normal.getZ()
				* one.getZ();

		double a = (double) (normal.getX() * ray.getOriginX() + normal.getY()
				* ray.getOriginY() + normal.getZ() * ray.getOriginZ() + -d);
		double b = (double) (normal.getX() * ray.getDirectionX() + normal.getY()
				* ray.getDirectionY() + normal.getZ() * ray.getDirectionZ());
		double intersect = -a / b;

		Intersection intersection = new Intersection(-1, (ray.getOriginX() + ray
				.getDirectionX()
				* intersect), ray.getOriginY() + ray.getDirectionY() * intersect, ray
				.getOriginZ()
				+ ray.getDirectionZ() * intersect, intersect);
		
		double epsilon = 1;

		if (intersect > 0) {
			Vector3D v1 = four.cross(one, new Point3D(intersection.getX(),
					intersection.getY(), intersection.getZ()));
			Vector3D v1b = new Point3D(intersection.getX(),
					intersection.getY(), intersection.getZ()).cross(one, four);
			if (v1.dot(normal) <= epsilon || v1b.dot(normal) >= -epsilon) {
				
				Vector3D v2 = two.cross(three, new Point3D(intersection.getX(),
						intersection.getY(), intersection.getZ()));
				Vector3D v2b = new Point3D(intersection.getX(),
						intersection.getY(), intersection.getZ()).cross(three, two);
				if (v2.dot(normal) <= epsilon || v2b.dot(normal) >= -epsilon ) {
					
					Vector3D v3 = one.cross(two, new Point3D(intersection.getX(),
							intersection.getY(), intersection.getZ()));
					Vector3D v3b = new Point3D(intersection.getX(),
							intersection.getY(), intersection.getZ()).cross(two, one);
					if (v3.dot(normal) <= epsilon || v3b.dot(normal) >= -epsilon ) {
						
						Vector3D v4 = three.cross(four, new Point3D(intersection.getX(),
								intersection.getY(), intersection.getZ()));
						Vector3D v4b = new Point3D(intersection.getX(),
								intersection.getY(), intersection.getZ()).cross(four, three);
						if (v4.dot(normal) <= epsilon || v4b.dot(normal) >= -epsilon) {
						} else {
							intersection.setIntersect(-1);
						}
					} else {
						intersection.setIntersect(-1);
					}
				} else {
					intersection.setIntersect(-1);
				}
			} else {
				intersection.setIntersect(-1);
			}
		}

		return intersection;
	}

	@Override
	public Point2D getUV(double xx, double yy, double zz) {
		// TODO Auto-generated method stub
		return new Point2D(1.0, 1.0);
	}
}
