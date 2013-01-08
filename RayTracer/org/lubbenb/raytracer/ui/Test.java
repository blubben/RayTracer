package org.lubbenb.raytracer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.media.j3d.QuadArray;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3f;

import org.lubbenb.math.KDTree;
import org.lubbenb.math.Point3D;
import org.lubbenb.math.Vector3D;
import org.lubbenb.math.KDTree.Node;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.objects.Object;
import org.lubbenb.raytracer.objects.Sphere;
import org.lubbenb.raytracer.shaders.Lambert;
import org.lubbenb.raytracer.shaders.Material;
import org.lubbenb.raytracer.shaders.Phong;



public class Test extends JPanel implements MouseListener {

	private KDTree tree;
	private Ray ray;
	private boolean click;
	
	public Test(){
		setPreferredSize(new Dimension(500, 500));
		tree = new KDTree();
		Material m = new Material(Color.WHITE, 0);
		Random g = new Random();
		for(int i=0; i< 10; i++)
//			tree.add(new Sphere(g.nextInt(300)+50, g.nextInt(300)+50, 0, 25, m), new Lambert(.6));
		
		tree.build();

		ray = new Ray(new Vector3D(0, 0, 0), new Vector3D(.1,.05,1));

		addMouseListener(this);
	}
	
	public void paint(Graphics g){
		g.clearRect(0, 0, 500, 500);
		drawKD(g, tree);
		g.drawLine((int)ray.getOriginX(), (int)ray.getOriginY(), (int)(ray.getOriginX()+ray.getDirectionX()*1000), (int)(ray.getOriginY()+ ray.getDirectionY()*1000));
		//double d[] = tree.traverseTree(ray);
		//double a = d[0];
		//double b = d[1];
		//double c = d[2];
		//double e = d[3];
		//double f = d[4];
		//double h = d[5];
		g.setColor(Color.GREEN);
		//g.fillOval((int)(ray.getOrgX()+ray.getDirX()*a)-5, (int)(ray.getOrgY()+ray.getDirY()*a)-5, 10, 10);
		g.setColor(Color.BLUE);
		//g.fillOval((int)(ray.getOrgX()+ray.getDirX()*b)-5, (int)(ray.getOrgY()+ray.getDirY()*b)-5, 10, 10);
		for(Object o : tree.getObjects()){
			Sphere s = (Sphere)o;
			g.drawOval((int)(s.getX()-s.getRadius()), (int)(s.getY()-s.getRadius()), (int)s.getRadius()*2, (int)s.getRadius()*2);
		}
		
		/*
		g.setColor(Color.ORANGE);
		g.fillOval((int)(ray.getOrgX()+ray.getDirX()*c)-5, (int)(ray.getOrgY()+ray.getDirY()*c)-5, 10, 10);
		g.setColor(Color.RED);
		g.fillOval((int)(ray.getOrgX()+ray.getDirX()*e)-5, (int)(ray.getOrgY()+ray.getDirY()*e)-5, 10, 10);
		*/
		//System.out.println(f+", "+h);
		/*
		double b[] = tree.shootRay(ray);
		double a = b[0];
		double c = b[1];
		g.setColor(Color.RED);
		g.fillOval((int)(ray.getOrgX()+ray.getDirX()*a)-5, (int)(ray.getOrgY()+ray.getDirY()*a)-5, 10, 10);
		g.setColor(Color.GREEN);
		g.fillOval((int)(ray.getOrgX()+ray.getDirX()*c)-5, (int)(ray.getOrgY()+ray.getDirY()*c)-5, 10, 10);
		*/
		//g.drawLine(145, 310, 145, 310);
		//System.out.println((int)(ray.getOrgX()+ray.getDirX()*a));
	}
	
	public void drawKD(Graphics g, KDTree t){
		Point3D[] p = t.getPoints();
		g.drawRect((int)p[0].getX(), (int)p[0].getY(), (int)(p[1].getX()-p[0].getX()), (int)(p[1].getY()-p[0].getY()));
		if(!tree.getRootNode().isLeaf()){
			drawNode(g, tree.getRootNode(), p[0], p[1]);
		}
	}
	
	public void drawNode(Graphics g, Node n, Point3D p1, Point3D p2){	
		if(!n.isLeaf()){
			int axis = n.getAxis();
	    	QuadArray polygon1 = new QuadArray (4, QuadArray.COORDINATES);
	    	Point3D p3 = new Point3D(p1);
	    	Point3D p4 = new Point3D(p1);
	    	p3.set((axis+1)%3, p2.get((axis+1)%3));
	    	p3.set((axis+2)%3, p1.get((axis+2)%3));
	    	p4.set((axis+1)%3, p1.get((axis+1)%3));
	    	p4.set((axis+2)%3, p2.get((axis+2)%3));
	    	polygon1.setCoordinate (0, new Point3f ((float)p1.getX(), (float)p1.getY(), (float)p1.getZ()));
	    	polygon1.setCoordinate (1, new Point3f ((float)p3.getX(), (float)p3.getY(), (float)p3.getZ()));
	    	polygon1.setCoordinate (2, new Point3f ((float)p4.getX(), (float)p4.getY(), (float)p4.getZ()));
	    	polygon1.setCoordinate (3, new Point3f ((float)p2.getX(), (float)p2.getY(), (float)p2.getZ()));
			
			Point3D ap = new Point3D();
			Point3D ap2 = new Point3D();
			ap.set(axis, n.getLocation());
			ap.set(axis, p2.get(axis));
			ap2.set(axis, n.getLocation());
			ap2.set(axis, p1.get(axis));
			drawNode(g, n.getLeftNode(), p1, ap);
			drawNode(g, n.getRightNode(), ap2, p2);
		}
	}
	
	public static void main(String[] argV){
		JFrame f = new JFrame("Test");
		f.add(new Test());
		f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(click){
			ray.setDir(-(ray.getOriginX()-arg0.getX()), -(ray.getOriginY()-arg0.getY()), 0);
			ray.normalize();
			//tree.traverseTree(ray);
			repaint();
			System.out.println("----------------");
			Iterator i = tree.getIterator(ray);
			if(i!=null)
				while(i.hasNext()){
					ArrayList<Object> n = (ArrayList<Object>) i.next();
					for(int a=0; a<n.size(); a++)
						System.out.println(((Sphere)n.get(a)).getX() +", "+((Sphere)n.get(a)).getY());
				}
		} else {
			ray.setOrigen(arg0.getX(), arg0.getY(), 0);
		}
		click = !click;			
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
