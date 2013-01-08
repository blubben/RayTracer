/**
 * 
 */
package org.lubbenb.raytracer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import org.lubbenb.math.Vector3D;

/**
 * BlockRayTracer
 * 
 * @author Brian Lubben
 * @date Dec 21, 2012
 * 
 */
public class BlockRayTracer extends RayTracer {

	/**
	 * Constructor for BlockRayTracer object.
	 */
	public BlockRayTracer(Dimension d) {
		super(d);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see raytracer.RayTracer#render(java.awt.Graphics)
	 */
	@Override
	public void render(Graphics graphics) {
		final Graphics g = graphics;
		int blockw = 32;
		int blockh = 32;
		int blockwnormal = blockw;
		int blockhnormal = blockh;
		int w = (int) (size.width / blockw) + 1;
		int h = (int) (size.height / blockh) + 1;
		int extraw = size.width - (w - 1) * blockw;
		int extrah = size.height - (h - 1) * blockh;

		for (int b1 = 0; b1 < w; b1++) {
			for (int b2 = 0; b2 < h; b2++) {
				if (b1 == (w - 1))
					blockw = extraw;
				else
					blockw = blockwnormal;
				if (b2 == (h - 1))
					blockh = extrah;
				else
					blockh = blockhnormal;

				for (int ix = 0; ix < blockw; ix++) {
					for (int iy = 0; iy < blockh; iy++) {
						double x = (((b1 * blockwnormal + ix))
								/ ((double) size.width) * camera.getWidth())
								- (((double) camera.getWidth()) / 2);

						double y = (((b2 * blockhnormal + iy))
								/ ((double) size.height) * camera.getHeight())
								- (((double) camera.getHeight()) / 2);
						y = -y;
						double z = camera.getLensDistance();

						double xAngle = camera.getYAngle() * (Math.PI / 180);
						double yAngle = camera.getXAngle() * (Math.PI / 180);
						double zAngle = camera.getZAngle() * (Math.PI / 180);

						double sinX = Math.sin(xAngle);
						double sinY = Math.sin(yAngle);
						double sinZ = Math.sin(zAngle);

						double cosX = Math.cos(xAngle);
						double cosY = Math.cos(yAngle);
						double cosZ = Math.cos(zAngle);

						double tempY = y * cosY - z * sinY;
						double tempZ = y * sinY + z * cosY;
						double tempX = x * cosX - tempZ * sinX;

						z = x * sinX + tempZ * cosX;
						x = tempX * cosZ - tempY * sinZ;
						y = tempX * sinZ + tempY * cosZ;
						
						Ray r = new Ray(camera.getOrigin(), new Vector3D(x, y, z));
						
						Color c = rayTrace(r, 0);
						g.setColor(c);
						g.drawLine(b1 * blockwnormal + ix, b2 * blockhnormal + iy, b1 * blockwnormal + ix, 
								b2 * blockhnormal + iy);
					}
				}
			}
		}
	}

}
