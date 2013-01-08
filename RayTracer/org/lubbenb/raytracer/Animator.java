package org.lubbenb.raytracer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.lubbenb.raytracer.lights.Light;
import org.lubbenb.raytracer.lights.Point;
import org.lubbenb.raytracer.objects.Camera;
import org.lubbenb.raytracer.objects.Plane;
import org.lubbenb.raytracer.objects.Sphere;
import org.lubbenb.raytracer.scene.Scene;
import org.lubbenb.raytracer.shaders.Lambert;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Animator {

    public Animator(){

    }
    
    public void animate(){
        Scene scene = new Scene();
        
        scene.setCamera(new Camera(0, 0, -500, new Dimension(500, 500), -15, -15, 0, 500));
        
       // scene.addObject(new Plane(-1, 0, 0, 200, new Lambert(.5), Color.BLUE, 0));
       // scene.addObject(new Plane(1, 0, 0, 200, new Lambert(.5), Color.BLUE, 0));
       // scene.addObject(new Plane(0, 1, 0, 200, new Lambert(.5), Color.BLUE, 0));
        //scene.addObject(new Plane(0, -1, 0, 200, new Lambert(.5), Color.BLUE, 0));
       // scene.addObject(new Plane(0, 0, -1, 1000, new Lambert(.5), Color.BLUE, 0));
        
        //Object circle = new Sphere(-100, 0, 200, 100, new Lambert(.5), Color.YELLOW, 0);
       // scene.addObject(circle);
       // int circleLoc = scene.getObjects().indexOf(circle);
        //c.add(new Circle(100, 0, 700, 100, new Lambert(.5), Color.YELLOW, 0));
        //c.add(new Circle(0, 0, 500, 50, new Lambert(.5), Color.RED, 0));

        scene.addLight(new Point(-50, 0, 0, 1));
        scene.addLight(new Point(50, 0, 0, 1));
        //l.add(new Light(0, 0, -200));
        //l.add(new Light(-100, -100, 100));
        //l.add(new Light(100,100,500));

        scene.setBackground(Color.BLACK);
        
        RayTracer rayTracer = new RayTracer(new Dimension(500, 500));
        
        for(int i=-100;i<100;i=i+10){
            BufferedImage image = rayTracer.renderScene(scene);
            
            FileOutputStream fos;
            try {
                fos = new FileOutputStream("out"+((i/10)+10)+".jpg");
                JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(fos);
                jpeg.encode(image);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //scene.getObjects().set(circleLoc,new Sphere(i, 0, 200, 100, new Lambert(.5), Color.YELLOW, 0));
            System.out.println("Completed frame " + ((i/10)+10));
        }
    }
    
    public static void main(String args[]){
        Animator a = new Animator();
        a.animate();
    }
}
