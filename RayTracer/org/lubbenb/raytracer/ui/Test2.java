package org.lubbenb.raytracer.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lubbenb.math.Point3D;
import org.lubbenb.math.Vector3D;
import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.objects.Triangle;
import org.lubbenb.raytracer.shaders.Material;
import org.lubbenb.raytracer.shaders.Phong;



public class Test2 extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Triangle t;
	
	public Test2(){
        Point3D p1 = new Point3D(50,200, 0);
        Point3D p2 = new Point3D(50,300, 0);
        Point3D p3 = new Point3D(150,200, 0);
        Material m = new Material(Color.ORANGE, 0);
		t = new Triangle(p1, p2, p3, m);
		addMouseListener(this);
	}
	
	public void paint(Graphics g){
		g.clearRect(0, 0, 500, 500);
		g.drawLine((int)t.getX().getX(), (int)t.getX().getY(), (int)t.getY().getX(), (int)t.getY().getY());
		g.drawLine((int)t.getY().getX(), (int)t.getY().getY(), (int)t.getZ().getX(), (int)t.getZ().getY());
		g.drawLine((int)t.getZ().getX(), (int)t.getZ().getY(), (int)t.getX().getX(), (int)t.getX().getY());
	}
	
	
	public static void main(String[] argV){
		JFrame f = new JFrame("Test");
		f.add(new Test2());
		f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println(arg0.getX()+", "+arg0.getY());

		Ray ray = new Ray();
		ray.setOrigen(arg0.getX(), arg0.getY(), -100);
		ray.setDir(0, 0, 1);
		System.out.println(t.shootRay(ray).getObjectID());
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
