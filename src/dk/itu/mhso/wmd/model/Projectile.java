package dk.itu.mhso.wmd.model;

import java.awt.Point;

public class Projectile extends Unit {
	private Ally ally;
	private Point previousPoint;
	private Enemy targetEnemy;
	private int distPerTick = 5;
	
	public Projectile(Ally ally, Enemy targetEnemy) {
		this.ally = ally;
		this.targetEnemy = targetEnemy;
		location = new Point(ally.getLocation().x + ally.getWidth()/2, ally.getLocation().y + ally.getHeight()/2);
		loadIcons(null);
	}
	
	public void move() {
		previousPoint = location;
		
		int deltaX = targetEnemy.getLocation().x - location.x;
		
		if(deltaX == 0) {
			active = false;
			return;
		}
		if(deltaX < 0) distPerTick *= -1;
		
		double ratioX = distPerTick/deltaX;
		double desiredX = location.x + distPerTick;
		double desiredY = location.y += location.y * ratioX;
		location = new Point((int)desiredX, (int)desiredY);
		
		calculateAngle(previousPoint, location);
	}
	
	public boolean hasHit() {
		return location.x > targetEnemy.getLocation().x-3 && location.x < targetEnemy.getLocation().x+3
				&& location.y > targetEnemy.getLocation().y-3 && location.y < targetEnemy.getLocation().y+3;
	}

	@Override
	public void loadIcons(String unitName) {
		icon = ally.getProjectileIcon();
	}
}
