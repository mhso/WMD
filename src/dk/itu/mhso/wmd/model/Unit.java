package dk.itu.mhso.wmd.model;

import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Unit {
	protected BufferedImage icon;
	
	abstract Shape getTestShape();
	
	public BufferedImage getIcon() {
		return icon;
	}
	
	abstract void loadIcon(String unitName);
}
