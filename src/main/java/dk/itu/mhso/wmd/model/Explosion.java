package dk.itu.mhso.wmd.model;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Explosion {
	private Image[] images;
	private int currentImage;
	private Point location;
	
	public Explosion(BufferedImage[] images, Point location) {
		this.images = images;
		this.location = location;
	}

	public Image getNextImage() {
		if(currentImage == images.length) return null;
		Image image = images[currentImage];
		currentImage++;
		return image;
	}
	
	public Point getLocation() {
		return location;
	}
}
