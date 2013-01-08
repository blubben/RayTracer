package org.lubbenb.raytracer;

import org.lubbenb.raytracer.objects.Object;

public interface ObjectContainer {
	public void add(Object o);
	public void remove(Object o);
	public void build();
	public Intersection getNearestObject(Ray r);
}
