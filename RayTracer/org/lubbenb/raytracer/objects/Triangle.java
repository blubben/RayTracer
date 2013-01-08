package org.lubbenb.raytracer.objects;

import org.lubbenb.math.Point2D;
import org.lubbenb.math.Point3D;
import org.lubbenb.math.Vector3D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.shaders.Material;



public class Triangle extends Object {
	private Point3D x;
	private Point3D y;
	private Point3D z;
	
	private double epsilon = .0000000000001;

	public Triangle(Point3D p1, Point3D p2, Point3D p3, Material m) {
		x = p1;
		y = p2;
		z = p3;
		
		material = m;
		computeMaxMin();
	}
	
	public void computeMaxMin(){
		Point3D p1, p2, p3;
		p1 = x; p2 = y; p3 = z;
		
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
		
		boundingBox[0] = new Point3D(minX, minY, minZ);
		boundingBox[1] = new Point3D(maxX, maxY, maxZ);
	}

	public Ray normalAt(double xx, double yy, double zz) {
		Vector3D v = y.getVector().crossProduct(x.subtract(z).getVector());
		v.normalize();
		Ray normal = new Ray(new Vector3D(xx, yy, zz), new Vector3D(-v.getX(), -v.getY(), -v.getZ()));
		return normal;
	}

	public Point3D getX() {
		return x;
	}

	public Point3D getY() {
		return y;
	}

	public Point3D getZ() {
		return z;
	}

	public Intersection shootRay(Ray ray) {
		Point3D x2, y2, z2;
		x2 = new Point3D(0, 0, 0);
		y2 = new Point3D(y.getX()-x.getX(), y.getY()- x.getY(), y.getZ()-x.getZ());
		z2 = new Point3D(z.getX()-x.getX(), z.getY()- x.getY(), z.getZ()-x.getZ());
		Vector3D normal = y.getVector().crossProduct(x.subtract(z).getVector());

		Vector3D b = new Vector3D(z2.getX(), z2.getY(), z2.getZ());
		Vector3D c = new Vector3D(y2.getX(), y2.getY(), y2.getZ());
		
		double distance = -new Vector3D(ray.getOriginX()-x.getX(),
				ray.getOriginY()-x.getY(), ray.getOriginZ()-x.getZ()).dotProduct(normal)/
				new Vector3D(ray.getDirectionX(), ray.getDirectionY(), ray.getDirectionZ()).dotProduct(normal);
		
		int axis;
		if(Math.abs(normal.getX())>Math.abs(normal.getY()))
			if(Math.abs(normal.getX())>Math.abs(normal.getZ()))
				axis = 0;
			else
				axis = 2;
		else
			if(Math.abs(normal.getY())>Math.abs(normal.getZ()))
				axis = 1;
			else
				axis = 2;
		
		int uAxis = (axis+1)%3;
		int vAxis = (axis+2)%3;
		
		double pU = ray.getOrigen(uAxis)+distance*ray.getDirection(uAxis)-x.get(uAxis);
		double pV = ray.getOrigen(vAxis)+distance*ray.getDirection(vAxis)-x.get(vAxis);

		double a2 = (b.getDirection(uAxis) * pV - b.getDirection(vAxis) * pU) / (b.getDirection(uAxis) * c.getDirection(vAxis) - b.getDirection(vAxis) * c.getDirection(uAxis));
		if (a2 < -epsilon) return new Intersection(-1, 0, 0, 0);
		double a3 = (c.getDirection(vAxis) * pU - c.getDirection(uAxis) * pV) / (b.getDirection(uAxis) * c.getDirection(vAxis) - b.getDirection(vAxis) * c.getDirection(uAxis));
		if (a3 < -epsilon) return new Intersection(-1, 0, 0, 0);
		if ((a2 + a3) > (1+epsilon)) return new Intersection(-1, 0, 0, 0);


		return new Intersection(ray.getOriginX() + ray
				.getDirectionX()
				* distance, ray.getOriginY() + ray.getDirectionY() * distance, ray
				.getOriginZ()
				+ ray.getDirectionZ() * distance, distance);
	}

	@Override
	public Point2D getUV(double xx, double yy, double zz) {
		// TODO Auto-generated method stub
		return new Point2D(1.0, 1.0);
	}
}
