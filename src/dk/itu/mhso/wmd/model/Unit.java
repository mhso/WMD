package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Unit {
	protected BufferedImage icon;
	protected Point location;
	protected boolean active = true;
	protected double angle;
	protected String name;
	
	public Unit(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public Point getMiddlePoint() {
		return new Point(location.x + getWidth()/2, location.y + getHeight()/2);
	}
	
	public void setLocation(Point point) {
		location = point;
	}
	
	public BufferedImage getIcon() {
		return icon;
	}
	
	public int getWidth() {
		return icon.getWidth();
	}
	
	public int getHeight() {
		return icon.getHeight();
	}
	
	public double getAngle() {
		return angle;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public abstract void loadIcons(String unitName);
}
