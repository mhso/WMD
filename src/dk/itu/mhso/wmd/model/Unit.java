package dk.itu.mhso.wmd.model;

import java.awt.Shape;
import java.awt.image.BufferedImage;

public abstract class Unit {
	protected BufferedImage icon;
	
	abstract Shape getTestShape();
}
