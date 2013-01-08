package org.lubbenb.raytracer.objects;

import java.awt.Color;

import org.lubbenb.math.Point2D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.shaders.Material;
import org.lubbenb.raytracer.shaders.Shader;



public class Cylinder extends Object {
    private double x;
    private double y;
    private double z;
    private double radius;
    private Color color;
    
    public Cylinder(double xx, double yy, double zz, double ra, Material m){
        x = xx;
        y = yy;
        z = zz;
        radius = ra;
        material = m;
    }
    
    public Color getColor(){
        return color;
    }
    
    public Intersection shootRay(Ray ray){
        double dirx,diry,dirz,ox,oy,oz;
        dirx = ray.getDirectionX();
        diry = ray.getDirectionY()/2;
        dirz = ray.getDirectionZ();
        ox = ray.getOriginX();
        oy = ray.getOriginY();
        oz = ray.getOriginZ();
        double a = dirx*dirx+diry*diry+dirz*dirz;
        double b = 2*(dirx*(ox-x)+diry*(oy-y)+dirz*(oz-z));
        double c = (ox-x)*(ox-x)+(oy-y)*(oy-y)+(oz-z)*(oz-z)-(radius*radius);
        double d = (b*b) - 4*a*c;
        double intersect = (-b-Math.sqrt(d))/(2*a);
        
        Intersection intersection = new Intersection(0,(ray.getOriginX()+ray.getDirectionX()*intersect),
                ray.getOriginY()+ray.getDirectionY()*intersect,ray.getOriginZ()+ray.getDirectionZ()*intersect,intersect);
        
        return intersection;
    }

    public Ray getNormal(double xx, double yy, double zz){
        Ray normal = new Ray();
        normal.setOrigen(xx,yy,zz);
        normal.setDir(xx*2-x,yy-y,zz/2-z);
        return normal;
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

	@Override
	public Point2D getUV(double xx, double yy, double zz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Ray normalAt(double xx, double yy, double zz) {
		// TODO Auto-generated method stub
		return null;
	}

}
