package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Enemy extends Unit {
	protected UnitPath path;
	protected int pointNr;
	protected Point pointOnPath;
	protected Point previousPoint;
	protected double angle;
	protected int maxHealth;
	protected int currentHealth;
	
	public void setUnitPath(UnitPath path) {
		this.path = path;
		pointOnPath = path.getPoint(pointNr);
	}
	
	public void loadIcon(String unitName) {
		try {
			icon = ImageIO.read(new File("resources/sprites/enemy/"+unitName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void incrementPathPoint() {
		previousPoint = pointOnPath;
		pointNr++;
		pointOnPath = path.getPoint(pointNr);
		int xDelta = pointOnPath.x-previousPoint.x;
		int yDelta = pointOnPath.y-previousPoint.y;
		angle = Math.atan2(yDelta, xDelta) + Math.toRadians(180);
	}
	
	public double getAngle() {
		return angle;
	}
	
	public Point getPointOnPath() {
		return pointOnPath;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public void setHealth(int health) {
		currentHealth = health;
	}
}
