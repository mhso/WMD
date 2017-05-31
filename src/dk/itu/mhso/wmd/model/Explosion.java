package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Explosion {
	private BufferedImage[] images;
	private int currentImage;
	private Point location;
	
	public Explosion(BufferedImage[] images, Point location) {
		this.images = images;
		this.location = location;
	}
	
	public BufferedImage getNextImage() {
		if(currentImage == images.length) return null;
		BufferedImage image = images[currentImage];
		currentImage++;
		return image;
	}
	
	public Point getLocation() {
		return location;
	}
}
