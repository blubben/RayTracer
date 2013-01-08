package org.lubbenb.raytracer.objects;

import org.lubbenb.math.Point2D;
import org.lubbenb.math.Point3D;
import org.lubbenb.math.Vector3D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.shaders.Material;

public class Sphere extends Object {
     
    private double x;
    private double y;
    private double z;
    private double radius;
    
    public Sphere(double xx, double yy, double zz, double ra){
    	boundingBox[0] = new Point3D(xx-ra, yy-ra, zz-ra);
    	boundingBox[1] = new Point3D(xx+ra, yy+ra, zz+ra);
    	
        x = xx;
        y = yy;
        z = zz;
        
        radius = ra;
    }
    
    public Sphere(double xx, double yy, double zz, double ra, Material m){
        this(xx, yy, zz, ra);
        material = m;
    }
    
    public Intersection shootRay(Ray ray){
        double dirx,diry,dirz,ox,oy,oz;
        dirx = ray.getDirectionX();
        diry = ray.getDirectionY();
        dirz = ray.getDirectionZ();
        ox = ray.getOriginX();
        oy = ray.getOriginY();
        oz = ray.getOriginZ();
        
        double a = dirx*dirx+diry*diry+dirz*dirz;
        double b = 2*(dirx*(ox-x)+diry*(oy-y)+dirz*(oz-z));
        double c = (ox-x)*(ox-x)+(oy-y)*(oy-y)+(oz-z)*(oz-z)-(radius*radius);
        double d = (b*b) - 4*a*c;
        double intersect = (-b-Math.sqrt(d))/(2*a);

        Intersection i = new Intersection(new Point3D(ray.getOrigin().add(ray.getDirection().scalarMultiply(intersect))), intersect);
        
        return i;
    }
    
    public Ray normalAt(double xx, double yy, double zz){
        return new Ray(new Vector3D(xx, yy, zz), new Vector3D(xx-x, yy-y, zz-z));
    }
    
    public Point2D getUV(double xx, double yy, double zz){
    	Vector3D vn = new Vector3D(0, 1, 0);
    	Vector3D vp = new Vector3D(x-xx, y-yy, z-zz).normalize();
     	Vector3D ve = new Vector3D(-1, 0, 0);
    	double r = Math.acos(-vn.dotProduct(vp));
    	double u, v = r/Math.PI;
    	double theta = (Math.acos(vp.dotProduct(ve)/Math.sin(r)))/(2*Math.PI);
    	Vector3D vc = vn.crossProduct(ve);
    	vc.normalize();
    	if(vc.dotProduct(vp)>0)
    		u = theta;
    	else
    		u = 1-theta;

    	return new Point2D(u, v);
    }
   
    public double getRadius(){
        return radius;
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
}
