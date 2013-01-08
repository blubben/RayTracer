package org.lubbenb.raytracer.scene;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lubbenb.raytracer.lights.Light;
import org.lubbenb.raytracer.objects.Camera;
import org.lubbenb.raytracer.objects.Object;
import org.lubbenb.raytracer.shaders.Shader;


public class Scene {
    private List<Object> c;

    private List<Light> l;
    
    private List<Shader> shaders;

    private Color background;
    
    private Camera camera;
    
    public Scene(){
        c = new ArrayList<Object>();
        l = new ArrayList<Light>();
        shaders = new ArrayList<Shader>();
    }
    
    public void addObject(Object o, Shader s){
        c.add(o);
        shaders.add(s);
    }
    
    public void addLight(Light light){
        l.add(light);
    }
    
    public void setCamera(Camera c){
        camera = c;
    }
    
    public void setBackground(Color b){
        background = b;
    }
    
    public List<Object> getObjects(){
        return c;
    }
    
    public List<Shader> getShaders(){
        return shaders;
    }
    
    public List<Light> getLights(){
        return l;
    }
    
    public Color getBackground(){
        return background;
    }
    
    public Camera getCamera(){
        return camera;
    }
}
