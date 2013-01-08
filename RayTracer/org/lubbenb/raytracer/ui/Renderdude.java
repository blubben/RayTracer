package org.lubbenb.raytracer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.lubbenb.math.Point3D;
import org.lubbenb.raytracer.BlockRayTracerMultiThreaded;
import org.lubbenb.raytracer.RayTracer;
import org.lubbenb.raytracer.lights.Area;
import org.lubbenb.raytracer.lights.Point;
import org.lubbenb.raytracer.objects.Camera;
import org.lubbenb.raytracer.objects.Sphere;
import org.lubbenb.raytracer.scene.Scene;
import org.lubbenb.raytracer.shaders.Material;
import org.lubbenb.raytracer.shaders.Phong;


public class Renderdude extends JFrame implements MouseListener{
    private static final long serialVersionUID = 1L;

    private RayTracer rayTracer;
    
    private boolean render;
    
    private Scene scene;
    
    private BufferedImage image;
    
    private JButton button;
    
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;
    
    private Dimension size;
    
    public Renderdude(final Scene s, Dimension d){
        super("Renderdude");
        
        size = d;
        //setSize(s.getCamera().getWidth(), s.getCamera().getHeight());
        scene = s;
        rayTracer = new BlockRayTracerMultiThreaded(d);
        image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        JToolBar tb = new JToolBar();
        button = new JButton("Render");
        button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
		        int w = size.width;     
		        int h = size.height;
		        double focalLength = Math.abs(0.5*w/Math.tan(67.380/2));
		        Camera c = s.getCamera();
				s.setCamera(new Camera(c.getX(),c.getY(),c.getZ(),new Dimension(500, 500), Double.parseDouble(textFieldX.getText()), Double.parseDouble(textFieldY.getText()),Double.parseDouble(textFieldZ.getText()), focalLength));
				try {
					render();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint();
			}
        });
        textFieldX = new JTextField("0");
        textFieldY = new JTextField("0");
        textFieldZ = new JTextField("0");
        getContentPane().setLayout(new BorderLayout());
        tb.add(textFieldX);
        tb.add(textFieldY);
        tb.add(textFieldZ);
        tb.add(button);
        tb.setMaximumSize(new Dimension(tb.getMaximumSize().width,tb.getPreferredSize().height));
        getContentPane().add(new JPanel(){
        	       	
        	public Dimension getPreferredSize(){
        		return size;
        	}
        	
            public void paint(Graphics g){
            	g.drawImage(image, 0, 0, size.width, size.height, null);
            }
        }, BorderLayout.CENTER);
        getContentPane().add(tb, BorderLayout.SOUTH);
        setVisible(true);
        addMouseListener(this);
    }
    
    public void render() throws InterruptedException{
        long l = System.currentTimeMillis();
        rayTracer.renderNOW(image.getGraphics(), scene);
        repaint();
        System.out.println((System.currentTimeMillis()-l)/1000.0+" seconds");
    }
    
    /*
    public void test(){
        rayTracer.renderNOW(image.getGraphics(), scene);
    	Ray r = new Ray();
    	r.setOrigen(0, 0, 0);
    	r.setDir(0, 0, 1);
    	long start = System.currentTimeMillis();
        for(int i=0; i<10000; i++){
        	rayTracer.getNearestObject(r, scene.getObjects());
        }
        System.out.println(System.currentTimeMillis()-start);
    }
    */

    public static void main(String args[]) throws InterruptedException {        
        Scene scene = new Scene();
        int w = 10;
        int h = 10;
        double focalLength = Math.abs(0.5*w/Math.tan(67.380/2));
        Camera camera = new Camera(0, 0, -50, new Dimension(w, h), 0, 0, 0, focalLength);
        camera.lookAt(new Point3D(0, 0, 0));
        scene.setCamera(camera);
        
       // scene.addObject(new Triangle(new Point3D(10,0,0), new Point3D(-10,0,0), new Point3D(10,10,0), new Material(Color.GREEN, 0)), new Lambert(1));
       // scene.addObject(new Triangle(new Point3D(10,0,0), new Point3D(-10,0,0), new Point3D(-10,-10,0), new Material(Color.GREEN, 0)), new Lambert(1));

        /*
        Material m = new Material(Color.WHITE, 0);
        scene.addObject(new Plane(-100, 0, 0, 500, m), new Lambert(.3));
        
        scene.addObject(new Plane(100, 0, 0, 500, m), new Lambert(.3));
        scene.addObject(new Plane(0, 100, 0, 500, m), new Lambert(.3));
        scene.addObject(new Plane(0, -100, 0, 500, m), new Lambert(.3));
        scene.addObject(new Plane(0, 0, -100, 1000, m), new Lambert(.3));
        scene.addObject(new Sphere(-5, 0, -25, 5, new Material(Color.GREEN, 0)), new Phong(.8, 5, .6));
        */
        /*
        Point3D p1 = new Point3D(-100,100,400);
        Point3D p2 = new Point3D(100,100,400);
        Point3D p3 = new Point3D(100,-100,400);
        Point3D p4 = new Point3D(-100,-100, 400);
        scene.addObject(new Triangle(p1, p2, p3, new Material(Color.CYAN, 0)), new Phong(.4,15,.6));
        /*
        Material m = new Material(Color.RED, .8);
        m.setBumpMap(new Texture("images/bumpmap2.jpg"));
        scene.addObject(new Sphere(90, 0, -25, 100, m), new Phong(.8, 5, .6));
        
        Material m2 = new Material(Color.BLUE, .8);
      //  m2.setBumpMap(new Texture("images/bumpmap2.jpg"));
        scene.addObject(new Sphere(-90, 0, 25, 100, m2), new Phong(.8, 5, .6));
*/
       // scene.addObject(new Sphere(-100, 100, 500, 100, new Phong(.4, 5, .6), Color.BLUE, 0));
        //scene.addObject(new Sphere(-100, -100, 500, 100, new Phong(.4, 5, .6), Color.RED, 0));
        
        //scene.addObject(new Sphere(100, 100, 500, 100, new Phong(.4,50, .6), Color.YELLOW, 0));
        
        //scene.addObject(new Sphere(0, 0, 0, 100, new Lambert(.4), Color.GREEN, 0));
        
/*
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++)
                scene.addObject(new Sphere(100-i*20,100-j*20,0,10,m), new Lambert(.4));
        
   
        Point3D p1 = new Point3D(-100,100,400);
        Point3D p2 = new Point3D(100,100,400);
        Point3D p3 = new Point3D(100,-100,400);
        Point3D p4 = new Point3D(-100,-100, 400);
        scene.addObject(new Triangle(p1, p2, p3, new Phong(.4,15,.6), Color.CYAN, 0));
        scene.addObject(new Rectangle(p1, p2, p3, p4, new Phong(.4,15,.6), Color.RED, 0));
        scene.addObject(new Sphere(0, 0, 400, 50, new Phong(.4,15,.6), Color.GREEN, 0));
        //scene.addObject(new Sphere(50, 0, 500, 50, new Phong(.4,15,.6), Color.RED, 0));
        */
        //scene.addObject(new Sphere(0, 0, 600, 100, new Lambert(.3), Color.RED, 0));
        /*
        OBJLoader l = new OBJLoader();
        ArrayList<Object> r = l.load("objects/ducky2.obj");
        Shader lam = new Lambert(.6);
        for(Object i : r){
        	scene.addObject(i, lam);
        }
        */
        //Material m = new Material(new Texture("earthmap.jpg"), 0);
        //Material m = new Material(Color.GREEN, .7);
       // Material m2 = new Material(Color.BLUE, .7);
    
       // m.setTransparency(1);
        //m.setRefractionIndex(1.33);
        //m2.setBumpMap(new Texture("bumpmap2.jpg"));
        //scene.addObject(new Sphere(0, 0, 0, 100, m), new Phong(.9,50, .6));
       // scene.addObject(new Sphere(-20, 0, 0, 10, new Phong(.4,50, .6), Color.YELLOW, 0));
        //scene.addObject(new Sphere(-300, 100, 500, 100, new Phong(.4,50, .6), Color.YELLOW, 0));

        Random r = new Random();
        for(int i=0;i<10;i++)
            for(int j=0;j<10;j++)
                scene.addObject(new Sphere(90-i*20,90-j*20,0,10,new Material(new Color(Math.abs(r.nextInt()%255), Math.abs(r.nextInt()%255), Math.abs(r.nextInt()%255)), .15)), new Phong(.8, 5, 2));
     
        //scene.addObject(new Sphere(199, 0, 0, 50, m2), new Lambert(.6));
        //scene.addObject(new Sphere(0, -50, 100, 50, m2), new Lambert(.6));
       // scene.addObject(new Sphere(0, 0, 0, 50, m), new Lambert(.6));
        //scene.addObject(new Sphere(0, -50, 100, 50, m2), new Lambert(.6)); 
        
       
        Point3D p5 = new Point3D(-200, 300, 200);
        Point3D p6 = new Point3D(-200, 300, -200);
        Point3D p7 = new Point3D(200, 300, 200);
   
        Material m3 = new Material(Color.BLUE, 0);
        //scene.addObject(new Triangle(p1, p2, p3, m3), new Lambert(.6));
       // scene.addObject(new Rectangle(p1, p2, p3, p4, m3), new Lambert(.6));
       // scene.addObject(new Rectangle(p6, p2, p1, p5, m3), new Lambert(.6));
       // scene.addObject(new Rectangle(p5, p1, p4, p7, m3), new Lambert(.6));
        
       //scene.addLight(new Point(0, 0, -200, 1));
        
        //scene.addLight(new Spot(200, 200, -300,.99, dir2, 25));
        //scene.addLight(new Spot(-500,0,200,.99, dir, 25));
       // scene.addLight(new Point(0, 0, -100, 1));
      //  scene.addLight(new Point(-100, 0, -300,.5));
       // scene.addLight(new Area(100, 0, -250, 10,10,10,.5));
        scene.addLight(new Area(-100, 0, -125, 10,10,10,.5));
       //scene.addLight(new Ambient(0, 0, 0, 1));
       //scene.addLight(new Area(100, 200, 100, 50,50,50,1));
       // scene.addLight(new Area(-200, 100, -200, 50,50,10,1));
        //scene.addLight(new Area(200, 200, -200, 50,50,50,0));
        
        scene.setBackground(Color.BLACK);
        Renderdude app = new Renderdude(scene, new Dimension(600, 600));
        app.pack();
        app.render();
        //app.test();


        app.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println(arg0.getX()+","+arg0.getY());		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
