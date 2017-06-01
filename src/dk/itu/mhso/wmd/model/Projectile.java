package dk.itu.mhso.wmd.model;

import java.awt.Point;

import dk.itu.mhso.wmd.Util;

public class Projectile extends Unit {
	private Ally ally;
	private Point previousPoint;
	private Enemy targetEnemy;
	private int distPerTick = 5;
	private int pointsTraveled;
	private final int MAX_POINTS = 20;
	private final int MARGIN_FOR_ERROR = 20;
	
	public Projectile(String name, Ally ally, Enemy targetEnemy) {
		super(name);
		this.ally = ally;
		this.targetEnemy = targetEnemy;
		location = new Point(ally.getLocation().x + ally.getWidth()/2, ally.getLocation().y + ally.getHeight()/2);
		angle = Util.calculateAngle(targetEnemy.getLocation(), location);
		loadIcons(null);
	}
	
	public Ally getAlly() {
		return ally;
	}
	
	public Enemy getTarget() {
		return targetEnemy;
	}
	
	public void move() {
		previousPoint = location;
		
		int deltaX = targetEnemy.getLocation().x - location.x;
		int deltaY = targetEnemy.getLocation().y - location.y;
		
		double desiredX = location.x + (deltaX/MAX_POINTS)*pointsTraveled;
		double desiredY = location.y + (deltaY/MAX_POINTS)*pointsTraveled;
		pointsTraveled++;
		
		location = new Point((int)desiredX, (int)desiredY);
		
		angle = Util.calculateAngle(location, previousPoint);
	}
	
	public boolean hasHit() {
		return location.x > targetEnemy.getLocation().x-MARGIN_FOR_ERROR && location.x < targetEnemy.getLocation().x+MARGIN_FOR_ERROR
				&& location.y > targetEnemy.getLocation().y-MARGIN_FOR_ERROR && location.y < targetEnemy.getLocation().y+MARGIN_FOR_ERROR;
	}

	@Override
	public void loadIcons(String unitName) {
		icon = ally.getProjectileIcon();
	}
}
