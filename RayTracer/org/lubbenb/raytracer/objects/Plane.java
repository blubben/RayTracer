package org.lubbenb.raytracer.objects;

import org.lubbenb.math.Point2D;
import org.lubbenb.math.Point3D;
import org.lubbenb.math.Vector3D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.shaders.Material;

public class Plane extends Object {
    private double x;
    private double y;
    private double z;
    private double d;
    
    public Plane(double xx, double yy, double zz, double dd, Material m){
    	boundingBox[0] = new Point3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    	boundingBox[1] = new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    	
        d = dd;
        x = xx;
        y = yy;
        z = zz;
        material = m;
    }

    public Ray getNormal(double xx, double yy, double zz){
        return new Ray(new Vector3D(xx, yy, zz), new Vector3D(x, y, z));
    }

    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getZ(){
        return z;
    }
    
    public Intersection shootRay(Ray ray){
        double a = (double)(x*ray.getOriginX()+y*ray.getOriginY()+z*ray.getOriginZ()+d);
        double b = (double)(x*ray.getDirectionX()+y*ray.getDirectionY()+z*ray.getDirectionZ());
        double intersect = -a/b;
        //System.out.println(ray.getOrgZ()+ray.getDirZ()*intersect);
        
        Intersection intersection = new Intersection(new Point3D(ray.getOrigin().add(ray.getDirection().scalarMultiply(intersect))), intersect);

        return intersection;
    }

	@Override
	public Point2D getUV(double xx, double yy, double zz) {
		return new Point2D(xx,yy);
	}

	@Override
	protected Ray normalAt(double xx, double yy, double zz) {
		// TODO Auto-generated method stub
		return null;
	}

}
