package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Unit {
	protected BufferedImage icon;
	protected Point location;
	protected boolean active = true;
	protected double angle;
	
	public Point getLocation() {
		return location;
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
	
	public void calculateAngle(Point p1, Point p2) {
		int xDelta = p2.x-p1.x;
		int yDelta = p2.y-p1.y;
		angle = Math.atan2(yDelta, xDelta) + Math.toRadians(180);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public abstract void loadIcons(String unitName);
}
