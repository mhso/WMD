package dk.itu.mhso.wmd.model;

import java.awt.Point;

public class Projectile extends Unit {
	private Ally ally;
	private Point previousPoint;
	private Enemy targetEnemy;
	private final int DIST_PER_TICK = 5;
	
	public Projectile(Ally ally, Enemy targetEnemy) {
		this.ally = ally;
		this.targetEnemy = targetEnemy;
		location = new Point(ally.getLocation().x + ally.getWidth()/2, ally.getLocation().y + ally.getHeight()/2);
		loadIcons(null);
	}
	
	public void move() {
		previousPoint = location;
		double deltaX = location.x - previousPoint.x;
		double deltaY = location.y - previousPoint.y;
		
		double ratioX = deltaX/DIST_PER_TICK;
		double desiredX = location.x + DIST_PER_TICK;
		double desiredY = location.y * ratioX;
		location = new Point((int)desiredX, (int)desiredY);
		angle = Math.atan2(deltaY, deltaX) + Math.toRadians(180);
	}

	@Override
	public void loadIcons(String unitName) {
		icon = ally.getProjectileIcon();
	}
}
