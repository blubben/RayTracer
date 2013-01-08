package org.lubbenb.raytracer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lubbenb.math.Point3D;
import org.lubbenb.raytracer.objects.Rectangle;
import org.lubbenb.raytracer.objects.Triangle;
import org.lubbenb.raytracer.objects.Object;
import org.lubbenb.raytracer.shaders.Lambert;
import org.lubbenb.raytracer.shaders.Material;
import org.lubbenb.raytracer.shaders.Phong;
import org.lubbenb.raytracer.shaders.Texture;


public class OBJLoader {
	private Material m = new Material(Color.RED, 0);
	public OBJLoader(){
		
	}
	
	public ArrayList<Object> load(String fileName){
		ArrayList<Object> r = new ArrayList<Object>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			ArrayList<Point3D> points = new ArrayList<Point3D>();
			int a=0;
			boolean first = true;
			while((line=reader.readLine())!=null){
				if(line.startsWith("v ")){
					//if(first)
					//	points.clear();
					first = false;
					String[] i = line.split(" +");
					points.add(new Point3D(Double.parseDouble(i[1]),
							Double.parseDouble(i[2]),
							Double.parseDouble(i[3])));
				}
				
				if(line.startsWith("f")){
					first = true;
					String[] i = line.split(" +");
					for(int x=1;x<i.length;x++)
						if(i[x].indexOf("/")!=-1)
							i[x] = i[x].substring(0, i[x].indexOf("/"));
					a++;
					Object o = null;
					Object o2 = null;
					if(i.length==5){
						o = new Triangle(points.get(Integer.parseInt(i[1])-1), points.get(Integer.parseInt(i[2])-1), points.get(Integer.parseInt(i[3])-1), m);
						o2 = new Triangle(points.get(Integer.parseInt(i[1])-1), points.get(Integer.parseInt(i[3])-1), points.get(Integer.parseInt(i[4])-1), m);
						//o = new Rectangle(points.get(Integer.parseInt(i[1])-1), points.get(Integer.parseInt(i[2])-1), points.get(Integer.parseInt(i[3])-1), points.get(Integer.parseInt(i[4])-1), m);
					}else if(i.length==4)
						o = new Triangle(points.get(Integer.parseInt(i[1])-1), points.get(Integer.parseInt(i[2])-1), points.get(Integer.parseInt(i[3])-1), m);
					else
						System.err.println("Error: "+i.length);
					if(o!=null)
						r.add(o);
					if(o2!=null)
						r.add(o2);
					//System.out.println("Rectangle "+a+": {"+points.get(Integer.parseInt(i[1])-1)+", "+points.get(Integer.parseInt(i[2])-1)+", "+ points.get(Integer.parseInt(i[3])-1)+", "+points.get(Integer.parseInt(i[4])-1)+"}");
					//System.out.println("Normal: "+rec.getNormal(0, 0, 0));
				}
			}
			System.out.println("Faces: " + (a+1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
}
