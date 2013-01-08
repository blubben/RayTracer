package org.lubbenb.raytracer.shaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	private BufferedImage image;

	public Texture(String fileName) {
		try {
			// Read from a file
			File file = new File(fileName);
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Cannot Read Image");
		}
	}
	
	public Dimension getSize(){
		return new Dimension(image.getWidth(), image.getHeight());
	}

	public Color getColor(double u, double v) {
		int width = image.getWidth();
		int height = image.getHeight();
		int ui = ((int)(u*width))%width;
		int vi = ((int)(v*height))%height;
		if(ui<0)
			ui = 0;
		if(vi<0)
			vi = 0;

		return new Color(image.getRGB(ui, vi));
	}
}
