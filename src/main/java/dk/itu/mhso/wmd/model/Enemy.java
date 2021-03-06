package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import dk.itu.mhso.wmd.Resources;

public abstract class Enemy extends Unit {
	protected UnitPath path;
	protected int pointNr;
	protected Point previousPoint;
	protected int maxHealth;
	protected int currentHealth;
	
	public Enemy(String name) {
		super(name);
	}
	
	public void setUnitPath(UnitPath path) {
		this.path = path;
		location = path.getPoint(pointNr);
	}
	
	public void setUnitPaths(UnitPath[] pathArr) {
		setUnitPath(pathArr[new Random().nextInt(pathArr.length)]);
	}
	
	public void loadIcons(String unitName) {
		try {
            var path = Resources.getSpritesPath() + "/enemy/" + unitName.toLowerCase() + ".png";
            var url = ClassLoader.getSystemResource(path);
			icon = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasEscaped() {
		return path.size() == pointNr;
	}
	
	public boolean isVisible() {
		return path.getPoint(pointNr).visible;
	}
	
	public void move() {
		previousPoint = location;
		pointNr++;
		location = path.getPoint(pointNr);
		angle = path.getCurrentAngle(pointNr);
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
	
	public void decrementHealth(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0) {
			currentHealth = 0;
			active = false;
		}
	}

	@Override
	public String toString() {
		return "Enemy " + super.toString() + ", path=" + path + ", pointNr=" + pointNr + ", previousPoint=" + previousPoint + ", maxHealth="
				+ maxHealth + ", currentHealth=" + currentHealth + "]";
	}
}
