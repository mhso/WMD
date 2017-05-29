package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class Ally extends Unit {
	protected int cost;
	protected int range;
	protected int damage;
	protected int fireRate;
	protected boolean isHighlighted;
	protected BufferedImage highlightIcon;
	protected List<Enemy> enemiesInRange = new ArrayList<>();
	protected Enemy currentlyTargetedEnemy;
	protected BufferedImage projectileIcon;
	
	public void loadIcons(String unitName) {
		try {
			icon = ImageIO.read(new File("resources/sprites/ally/"+unitName+".png"));
			highlightIcon = ImageIO.read(new File("resources/sprites/ally/"+unitName+"_highlight.png"));
			projectileIcon = ImageIO.read(new File("resources/sprites/ally/"+unitName+"_projectile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getRange() {
		return range;
	}
	
	public Ellipse2D getRangeCircle() {
		return new Ellipse2D.Double(location.x + getWidth()/2 - range/2, location.y + getHeight()/2 - range/2, 
				range, range);
	}
	
	public BufferedImage getHighlightedIcon() {
		return highlightIcon;
	}
	
	public BufferedImage getProjectileIcon() {
		return projectileIcon;
	}
	
	public boolean isHighlighted() {
		return isHighlighted;
	}
	
	public void setHighlighted(boolean highlight) {
		isHighlighted = highlight;
	}
	
	public List<Enemy> getEnemiesInRange() {
		return enemiesInRange;
	}
	
	public void addEnemyInRange(Enemy enemy) {
		enemiesInRange.add(enemy);
	}
	
	public void removeEnemyFromInRange(Enemy enemy) {
		enemiesInRange.remove(enemy);
	}
	
	public void setCurrentlyTargetedEnemy(Enemy enemy) {
		if(enemy == null) angle = 0;
		else calculateAngle(enemy.getLocation(), location);
		currentlyTargetedEnemy = enemy;
	}
	
	public Enemy getCurrentlyTargetedEnemy() {
		return currentlyTargetedEnemy;
	}
	
	public boolean contains(Point point) {
		return point.x >= location.x && point.x <= location.x + getWidth()
				&& point.y >= location.y && point.y <= location.y + getHeight();
	}
}
