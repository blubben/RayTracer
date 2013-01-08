/**
 * 
 */
package org.lubbenb.raytracer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.lubbenb.math.Point2D;
import org.lubbenb.math.Vector3D;


/**
 * BlockRayTracer
 *
 * @author Brian Lubben
 * @date Dec 21, 2012
 *
 */
public class BlockRayTracerMultiThreaded extends RayTracer {
	private int threads = 4;
	
	/**
	 * Constructor for BlockRayTracer object.
	 */
	public BlockRayTracerMultiThreaded(Dimension d) {
		super(d);
	}
	
	/* (non-Javadoc)
	 * @see raytracer.RayTracer#render(java.awt.Graphics)
	 */
	@Override
	public void render(Graphics graphics) {
		final Graphics g = graphics;
		int blockw = 32;
		int blockh = 32;
		int blockwnormal = blockw;
		int blockhnormal = blockh;
		int w = (int) (size.width / blockw)+1;
		int h = (int) (size.height / blockh)+1;
		int extraw = size.width-(w-1)*blockw;
		int extrah = size.height-(h-1)*blockh;
		
		final BlockingQueue<RayPixel> out = new LinkedBlockingQueue<RayPixel>();
		final BlockingQueue<RayPixel> in = new LinkedBlockingQueue<RayPixel>(100);
		final ExecutorService es = Executors.newFixedThreadPool(threads);
		es.submit(new Runnable(){
			@Override
			public void run() {
				boolean isDone = false;
				final List<RayPixel> pixels = new LinkedList<RayPixel>();
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					int co = out.drainTo(pixels);
					if(!isDone && co==0)
						continue;				

					for(RayPixel p : pixels){
						g.setColor(p.getColor());
						g.drawLine(p.getX(), p.getY(), p.getX(), p.getY());
					}
					
					pixels.clear();
					
					if(isDone)
						break;
					
					if(es.isTerminated())
						isDone = true;
				}
			}			
		});
		
		for(int i=0; i<threads; i++)
			es.submit(new RTThread(in, out));
		
		for (int b1 = 0; b1 < w; b1++) {
			for (int b2 = 0; b2 < h; b2++) {
				if (b1 == (w-1))
					blockw = extraw;
				else
					blockw = blockwnormal;
				if (b2 == (h-1))
					blockh = extrah;
				else
					blockh = blockhnormal;

				for (int ix = 0; ix < blockw; ix++) {
					for (int iy = 0; iy < blockh; iy++) {				
						double x = (((b1 * blockwnormal + ix))/((double)size.width)
								* camera.getWidth()) -
								(((double)camera.getWidth())/2);

						double y = (((b2 * blockhnormal + iy))/((double)size.height)
								* camera.getHeight()) -
								(((double)camera.getHeight())/2);
						y=-y;
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

						try {
							in.put(new RayPixel(r, b1 * blockwnormal + ix, b2 * blockhnormal + iy));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		es.shutdown();
	}
	
	public class RTThread implements Runnable {
		private BlockingQueue<RayPixel> in;
		private BlockingQueue<RayPixel> out;
		
		public RTThread(BlockingQueue<RayPixel> in, BlockingQueue<RayPixel> out){
			this.out = out;
			this.in = in;
		}
		
		@Override
		public void run() {
			RayPixel rp;
			try {
				while((rp=in.take())!=null){
					Color c = rayTrace(rp.getRay(), 0);
					rp.setColor(c);
					out.add(rp);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class RayPixel {
		private Ray ray;
		private Point2D point;
		private Color color;
		
		public RayPixel(Point2D point, Ray ray) {
			super();
			this.point = point;
			this.ray = ray;
		}
		
		/**
		 * Constructor for RayPixel object.
		 */
		public RayPixel(Ray r, int i, int j) {
			this(new Point2D(i,j), r);
		}

		/** Getter for ray variable. 
		 *
		 * @return ray
		 */
		public Ray getRay() {
			return ray;
		}
		/** Setter for the ray variable
		 * 
		 * @param ray
		 */
		public void setRay(Ray ray) {
			this.ray = ray;
		}
		/** Getter for point variable. 
		 *
		 * @return point
		 */
		public Point2D getPoint() {
			return point;
		}
		
		public int getX(){
			return (int) point.getX();
		}
		
		public int getY(){
			return (int) point.getY();
		}
		
		/** Setter for the point variable
		 * 
		 * @param point
		 */
		public void setPoint(Point2D point) {
			this.point = point;
		}
		
		/** Getter for color variable. 
		 *
		 * @return color
		 */
		public Color getColor() {
			return color;
		}

		/** Setter for the color variable
		 * 
		 * @param color
		 */
		public void setColor(Color color) {
			this.color = color;
		}
	}
}
