package org.lubbenb.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.lubbenb.raytracer.Intersection;
import org.lubbenb.raytracer.ObjectContainer;
import org.lubbenb.raytracer.Ray;
import org.lubbenb.raytracer.objects.Object;
import org.lubbenb.raytracer.objects.Sphere;

/**
 * 
 * KDTree
 *
 * @author Brian Lubben
 * @date Feb 19, 2009
 *
 */
public class KDTree implements ObjectContainer {
	
	/**
	 * points field of type Point3D[].
	 */
	private Point3D[] points = new Point3D[2];
	
	/**
	 * mainNode field of type Node. Contains the first node
	 * of the kd-tree
	 */
	private Node mainNode;
	
	/**
	 * objects field of type List<Object>.
	 */
	private List<Object> objects;
	
	/**
	 * epsilon field of type double.
	 */
	private double epsilon = .0000000000001;
	
	/**
	 * Constructor for KDTree object.
	 * Creates empty tree.
	 */
	public KDTree(){
		this(new ArrayList<Object>());
	}
	
	/**
	 * Constructor for KDTree object.
	 * 
	 * @param l				List of objects.
	 */
	public KDTree(List<Object> l){
		objects = l;
		mainNode = new Node();
	}
	
	/**
	 * Constructor for KDTree object.
	 * 
	 * @param epsilon		Epsilon value.
	 */
	public KDTree(double epsilon){
		this();
		this.epsilon = epsilon;
	}
	
	/**
	 * Constructor for KDTree object.
	 * 
	 * @param l				List of objects in the tree.
	 * @param epsilon		Epsilon value.
	 */
	public KDTree(List<Object> l, double epsilon){
		this(l);
		this.epsilon = epsilon;
	}

	/**
	 * This function adds an object to the tree.
	 * 
	 * @param o			List of objects in the tree.
	 */
	public void add(Object o) {
		objects.add(o);
	}
	
	/**
	 * This function removes an object to the tree.
	 * 
	 * @param o
	 */
	public void remove(Object o){
		objects.remove(o);
	}

	/**
	 * Function that creates the tree.
	 * 
	 * @return void
	 */
	public void build() {
		// Set box
		double minX = objects.get(0).getAABB()[0].getX(), minY = objects.get(0).getAABB()[0].getY(), minZ = objects.get(0).getAABB()[0].getZ();
		double maxX = objects.get(0).getAABB()[0].getX(), maxY = objects.get(0).getAABB()[0].getY(), maxZ = objects.get(0).getAABB()[0].getZ();
		for(Object o : objects){
			if(o.getAABB()[0].getX()<minX)
				minX = o.getAABB()[0].getX();
			
			if(o.getAABB()[0].getY()<minY)
				minY = o.getAABB()[0].getY();
			
			if(o.getAABB()[0].getZ()<minZ)
				minZ = o.getAABB()[0].getZ();
			
			if(o.getAABB()[1].getX()>maxX)
				maxX = o.getAABB()[1].getX();
			
			if(o.getAABB()[1].getY()>maxY)
				maxY = o.getAABB()[1].getY();
			
			if(o.getAABB()[1].getZ()>maxZ)
				minZ = o.getAABB()[1].getZ();
		}
		
		points[0] = new Point3D(minX, minY, minZ);
		points[1] = new Point3D(maxX, maxY, maxZ);
		System.out.println(points[0]+", "+points[1]);

		mainNode.setObjectList(objects);
		int depth = 0;
		splitNode(mainNode, depth, objects, points[0], points[1]);
	}

	/** 
	 * This method is a recursive function that analyzes whether to
	 * split a node element in the kd-tree further.  It divides
	 * the nodes down the tree until it decides it no longer provides
	 * a benefit.
	 * @param curNode		Current node that is being split
	 * @param depth			Current depth of tree
	 * @param ob			Objects held by this node
	 * @param start			Point that holds the upper left extreme of the current rectangle
	 * @param end			Point that holds the lower right extreme of the current rectangle
	 * @return void
	 */
	private void splitNode(Node curNode, int depth, List<Object> ob, Point3D start, Point3D end) {
		int axis = depth % 3;
		int bestAxis = 0;
		double bestcost = Double.POSITIVE_INFINITY;
		double bestposition=0;

		// Search through possibilities
		
		//for(int axis=0; axis<3; axis++){
			int lnc;
			int rnc;
			ArrayList<Double> list = new ArrayList<Double>();
			for (Object o : ob) {
				list.add(o.getAABB()[0].get(axis));
				list.add(o.getAABB()[1].get(axis));
			}
			
			for (int i = 0; i < list.size(); i++) {
				lnc = 0;
				rnc = 0;
				double pos = list.get(i);
				/* old version
				for (Object o2 : ob) {
					double p1 = o2.getAABB()[0].get(axis);
					double p2 = o2.getAABB()[1].get(axis);
	
					if (p1 < pos && p2 <= pos) {
						lnc++;
					} else if (p1 >= pos && p2 > pos) {
						rnc++;
					} else {
						lnc++;
						rnc++;
					}
				}
				*/
				// new version
				for (Object o2 : ob) {
					double p1 = o2.getAABB()[0].get(axis);
					double p2 = o2.getAABB()[1].get(axis);
	
					if (p2 < pos) {
						lnc++;
					} else if (p1 > pos) {
						rnc++;
					} else {
						lnc++;
						rnc++;
					}
				}
				//done
				
				double one = end.get(axis)-pos;
				double two = end.get((axis+1)%3)-start.get((axis+1)%3);
				double three = end.get((axis+2)%3)-start.get((axis+2)%3);
				double one2 = pos-start.get(axis);
				double two2 = end.get((axis+1)%3)-start.get((axis+1)%3);
				double three2 = end.get((axis+2)%3)-start.get((axis+2)%3);
				double sa2 = 2*(one*two+one*three+two*three);
				double sa1 = 2*(one2*two2+one2*three2+two2*three2);
	
				//System.out.println(one2+", "+one);
				double cost = sa1*lnc+sa2*rnc;
				if(cost<bestcost&&cost>=0){
					bestcost = cost;
					bestposition = pos;
					bestAxis = axis;
				}
				//System.out.println(rnc);
				//System.out.println("Cost left: "+sa1*lnc+", Cost right: "+sa2*rnc);
				// add third dimension
			}
		//}

		//System.out.println(bestposition);
		axis = bestAxis;
		// average/=ob.size();
		double one = end.get(axis)-start.get(axis);
		double two = end.get((axis+1)%3)-start.get((axis+1)%3);
		double three = end.get((axis+2)%3)-start.get((axis+2)%3);
		double sa = 2*(one*two+one*three+two*three);
		double costOfNotSplitting = sa * ob.size();
		
		//System.out.println(bestcost+", "+costOfNotSplitting);
		if(bestcost<costOfNotSplitting) {	
		//if(depth<3){
			curNode.split(axis, bestposition);
			curNode.getLeftNode().setObjectList(new ArrayList<Object>());
			curNode.getRightNode().setObjectList(new ArrayList<Object>());
			/* old version
			for (Object o : ob) {
				if ((o.getAABB()[0].get(axis) < bestposition)
						&& (o.getAABB()[1].get(axis) <= bestposition)) {
					curNode.getLeftNode().getObjectList().add(o);
				} else if ((o.getAABB()[0].get(axis) >= bestposition)
						&& (o.getAABB()[1].get(axis) > bestposition)) {
					curNode.getRightNode().getObjectList().add(o);
				} else {
					curNode.getLeftNode().getObjectList().add(o);
					curNode.getRightNode().getObjectList().add(o);
				}
			}
*/
			// new version
			for (Object o : ob) {
				if (o.getAABB()[1].get(axis) < bestposition) {
					curNode.getLeftNode().getObjectList().add(o);
				} else if (o.getAABB()[0].get(axis) > bestposition) {
					curNode.getRightNode().getObjectList().add(o);
				} else {
					curNode.getLeftNode().getObjectList().add(o);
					curNode.getRightNode().getObjectList().add(o);
				}
			}
			
			Point3D end2 = new Point3D(axis==0? bestposition : end.getX(), axis==1? bestposition : end.getY(), axis==2? bestposition : end.getZ());
			Point3D start2 = new Point3D(axis==0? bestposition : start.getX(), axis==1? bestposition : start.getY(), axis==2? bestposition : start.getZ());

			splitNode(curNode.getLeftNode(), depth + 1, curNode.getLeftNode()
					.getObjectList(), start, end2);
			splitNode(curNode.getRightNode(), depth + 1, curNode.getRightNode()
					.getObjectList(), start2, end);
			/*
			System.out.println("Axis: " + axis+"   location: " + bestposition+" leaf?:" + curNode.isLeaf());
			System.out.println("left:");
			for (Object p : curNode.getLeftNode().getObjectList())
				System.out.println(p);
			System.out.println("right:");
			for (Object p2 : curNode.getRightNode().getObjectList())
				System.out.println(p2);			
			*/
		}
	}
	
	/**
	 * Terrible method I should fix.  Finds the t values of the main node
	 * the first two values of the array are the two that count
	 * @return double[]
	 */
	private double[] shootRay(Ray r) {
		// Test X plane intersection
		double x = points[0].getX();
		double a = (x - r.getOriginX()) / r.getDirectionX();
		double y = r.getDirectionY() * a + r.getOriginY();
		double z = r.getDirectionZ() * a + r.getOriginZ();
		if (y > points[1].getY() || y < points[0].getY()
				|| z > points[1].getZ() || z < points[0].getZ())
			a = Double.POSITIVE_INFINITY;

		x = points[1].getX();
		double b = (x - r.getOriginX()) / r.getDirectionX();
		y = r.getDirectionY() * b + r.getOriginY();
		z = r.getDirectionZ() * b + r.getOriginZ();
		if (y > points[1].getY() || y < points[0].getY()
				|| z > points[1].getZ() || z < points[0].getZ())
			b = Double.POSITIVE_INFINITY;

		// Test Y plane intersection
		y = points[0].getY();
		double c = (y - r.getOriginY()) / r.getDirectionY();
		x = r.getDirectionX() * c + r.getOriginX();
		z = r.getDirectionZ() * c + r.getOriginZ();
		if (x > points[1].getX() || x < points[0].getX()
				|| z > points[1].getZ() || z < points[0].getZ())
			c = Double.POSITIVE_INFINITY;

		y = points[1].getY();
		double d = (y - r.getOriginY()) / r.getDirectionY();
		x = r.getDirectionX() * d + r.getOriginX();
		z = r.getDirectionZ() * d + r.getOriginZ();
		if (x > points[1].getX() || x < points[0].getX()
				|| z > points[1].getZ() || z < points[0].getZ())
			d = Double.POSITIVE_INFINITY;

		// Test Z plane intersection
		z = points[0].getZ();
		double e = (z - r.getOriginZ()) / r.getDirectionZ();
		x = r.getDirectionX() * e + r.getOriginX();
		y = r.getDirectionY() * e + r.getOriginY();
		if (x > points[1].getX() || x < points[0].getX()
				|| y > points[1].getY() || y < points[0].getY())
			e = Double.POSITIVE_INFINITY;

		z = points[1].getZ();
		double f = (z - r.getOriginZ()) / r.getDirectionZ();
		x = r.getDirectionX() * f + r.getOriginX();
		y = r.getDirectionY() * f + r.getOriginY();
		if (x > points[1].getX() || x < points[0].getX()
				|| y > points[1].getY() || y < points[0].getY())
			f = Double.POSITIVE_INFINITY;

		double[] ret = new double[] { a, b, c, d, e, f };
		Arrays.sort(ret);

		if (ret[0] != Double.POSITIVE_INFINITY)
			return ret;
		else
			return null;
	}
	
	
	/**
	 * Traverses the node based on the ray and primes the stack
	 * 
	 * @return void
	 */
	private void traverseNode(Ray ray, Stack stack, Node n, double tMin, double tMax){
		if(!n.isLeaf()){
			double splitT;
			if(ray.getDirection(n.getAxis())!=0)
				splitT = (n.getLocation()-ray.getOrigen(n.getAxis()))/ray.getDirection(n.getAxis());
			else
				if(n.getLocation()>ray.getOrigen(n.getAxis())){
					splitT=tMin;
				} else {
					splitT=tMax;
				}
				
			Node near, far;

			if(ray.getDirection(n.getAxis())>0){
				near = n.getLeftNode();
				far = n.getRightNode();
			} else {
				near = n.getRightNode();
				far = n.getLeftNode();
			}
						
			if(splitT<=tMin){
				stack.push(far);
				stack.push(tMin);
				stack.push(tMax);
			} else if(splitT>=tMax){
				stack.push(near);
				stack.push(tMin);
				stack.push(tMax);
			} else {
				stack.push(far);
				stack.push(splitT);
				stack.push(tMax);
				
				stack.push(near);
				stack.push(tMin);
				stack.push(splitT);
			}
		}
	}
	
	/**
	 * Gets the nearest object utilizing the kd-tree.
	 * 
	 * @param r			ray by which to find the nearest intersection.
	 */
	public Intersection getNearestObject(Ray r) {
		Stack stack = new Stack();
		double[] values = shootRay(r);
		if(values!=null){
			stack.push(mainNode);
			stack.push(values[0]);
			stack.push(values[1]);
		}
		
		Intersection intersection = new Intersection(-1, 0, 0, 0);
		
		while(true){
			if(!stack.isEmpty()){
				double max = (Double) stack.pop();
				double min = (Double) stack.pop();
				Node node = (Node) stack.pop();
	
				if(node.isLeaf()){
					if(node.getObjectList()!=null)
						intersection = getNearestObjectInList(r, node.getObjectList());
					
					if(intersection.getObjectID()!=-1){
						int id = intersection.getObjectID();
						intersection.setObjectID(((Object)node.getObjectList().get(id)).getID());
						break;
					}
				} else {
					traverseNode(r, stack, node, min, max);
				}
			} else {
				return intersection;
			}
		}
		
		return intersection;		
	}

	
	/**
	 * Finds the nearest intersection of the objects in the scene from a light
	 * ray
	 * 
	 * @param r					Ray that is checked against objects.
	 * @param objects			List of object to check against.
	 * @return Intersection		Nearest intersection is returned
	 */
	private Intersection getNearestObjectInList(Ray r, List objects) {
		Intersection currentIntersection;
		Intersection nearestIntersection = new Intersection(-1, 0, 0, 0);
		double nearestDistance = Double.NEGATIVE_INFINITY;
		double currentDistance = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < objects.size(); i++) {
			currentIntersection = ((Object) objects.get(i)).shootRay(r);
			if (currentIntersection.getIntersect() > epsilon) {
				currentDistance = Math.sqrt((currentIntersection.getX() - r
						.getOriginX())
						* (currentIntersection.getX() - r.getOriginX())
						+ (currentIntersection.getY() - r.getOriginY())
						* (currentIntersection.getY() - r.getOriginY())
						+ (currentIntersection.getZ() - r.getOriginZ())
						* (currentIntersection.getZ() - r.getOriginZ()));

				// If the current object is closer (within a certain margin of
				// error)
				// replace the nearest intersection with that
				if ((nearestIntersection.getObjectID() == -1&&!Double.isNaN(currentDistance))
						|| ((currentDistance - nearestDistance) < epsilon)) {

					nearestIntersection = new Intersection(i, currentIntersection.getIntersection(), currentDistance);

					nearestDistance = currentDistance;
				}
			}
		}
		return nearestIntersection;
	}
	
	/** Getter for points variable. 
	 *
	 * @return points
	 */
	public Point3D[] getPoints() {
		return points;
	}

	/** Setter for the points variable
	 * 
	 * @param points
	 */
	public void setPoints(Point3D[] points) {
		this.points = points;
	}

	/** Getter for mainNode variable. 
	 *
	 * @return mainNode
	 */
	public Node getMainNode() {
		return mainNode;
	}

	/** Setter for the mainNode variable
	 * 
	 * @param mainNode
	 */
	public void setMainNode(Node mainNode) {
		this.mainNode = mainNode;
	}

	/** Getter for epsilon variable. 
	 *
	 * @return epsilon
	 */
	public double getEpsilon() {
		return epsilon;
	}

	/** Setter for the epsilon variable
	 * 
	 * @param epsilon
	 */
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}
	

	/** Getter for objects variable. 
	 *
	 * @return objects
	 */
	public List<Object> getObjects() {
		return objects;
	}

	/** Setter for the objects variable
	 * 
	 * @param objects
	 */
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	/**
	 * 
	 * Node
	 *
	 * @author Brian Lubben
	 * @date Feb 19, 2009
	 *
	 */
	public class Node {
		private Node left;
		private Node right;
		private int axis;
		private double location;
		private List<Object> objects;

		/**			
		 * This method splits the node into two subnodes
		 * 
		 * @param axis			Axis of split(0=x, 1=y, or 2=z)
		 * @param location		Location of split along specified axis
		 * @return void			Return nothing
		 */
		public void split(int axis, double location) {
			//System.out.println("split");
			this.axis = axis;
			this.location = location;
			left = new Node();
			right = new Node();
		}

		public void setObjectList(List<Object> ol) {
			objects = ol;
		}

		public List<Object> getObjectList() {
			return objects;
		}

		public void traverseNodes(Ray r, double tMin, double tMax) {
			if (!isLeaf()) {
				double splitT = (location - r.getOrigen(axis))
						/ r.getDirection(axis);
				Node near, far;
				if (r.getDirection(axis) > 0) {
					near = left;
					far = right;
				} else {
					near = right;
					far = left;
				}
				if (splitT <= tMin) {
					far.traverseNodes(r, tMin, tMax);
				} else if (splitT >= tMax) {
					near.traverseNodes(r, tMin, tMax);
				} else {
					near.traverseNodes(r, tMin, splitT);
					far.traverseNodes(r, splitT, tMax);
				}
			}
		}

		public double getLocation() {
			return location;
		}

		public int getAxis() {
			return axis;
		}

		public void setLeftNode(Node l) {
			left = l;
		}

		public void setRightNode(Node r) {
			right = r;
		}

		public Node getLeftNode() {
			return left;
		}

		public Node getRightNode() {
			return right;
		}

		public boolean isLeaf() {
			if (left == null || right == null)
				return true;
			else
				return false;
		}
	}

}


