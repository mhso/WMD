package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import dk.itu.mhso.wmd.Util;
import dk.itu.mhso.wmd.view.WindowUnitUpgrade;

public abstract class Ally extends Unit {
	protected int cost;
	protected int range;
	protected int damage;
	protected int fireRate;
	protected int aoeDamage;
	protected int aoeRadius;
	protected int upgradeLevel = 1;
	protected UpgradeInfo upgradeInfo;
	
	private int enemiesKilled;
	private int goldEarned;
	private BufferedImage highlightIcon;
	private List<Enemy> enemiesInRange = new ArrayList<>();
	private Enemy currentlyTargetedEnemy;
	private BufferedImage projectileIcon;
	private WindowUnitUpgrade upgradeWindow;
	
	public Ally(String name) {
		super(name);
	}
	
	public void loadIcons(String unitName) {
		try {
			icon = ImageIO.read(new File("resources/sprites/ally/"+unitName+".png"));
			highlightIcon = ImageIO.read(new File("resources/sprites/ally/"+unitName+"_highlight.png"));
			projectileIcon = ImageIO.read(new File("resources/sprites/ally/"+unitName+"_projectile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadUpgradeInfo(String fileName) { 
		upgradeInfo = (UpgradeInfo) Util.readObjectFromFile("resources/unitinfo/" + fileName + "_upginf.bin"); 
		if(upgradeInfo != null) upgradeInfo.setAlly(this);
	}
	
	public UpgradeInfo getUpgradeInfo() {
		return upgradeInfo;
	}
	
	public void upgrade() {
		upgradeLevel++;
	}
	
	public void createUpgradeWindow() {
		upgradeWindow = new WindowUnitUpgrade(this);
	}
	
	public WindowUnitUpgrade getUpgradeWindow() {
		return upgradeWindow;
	}
	
	public void showUpgradeWindow() {
		upgradeWindow.showDropdown();
	}
	
	public void incrementGoldEarned(int amount) {
		goldEarned += amount;
	}
	
	public void incrementEnemiesKilled(int amount) {
		enemiesKilled += amount;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getRange() {
		return range;
	}
	
	public void setRange(int range) {
		this.range = range;
	}
	
	public Ellipse2D getRangeCircle() {
		return new Ellipse2D.Double(location.x + getWidth()/2 - range/2, location.y + getHeight()/2 - range/2, 
				range, range);
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getAOEDamage() {
		return aoeDamage;
	}
	
	public int getAOERadius() {
		return aoeRadius;
	}
	
	public void setAOERadius(int radius) {
		aoeRadius = radius;
	}
	
	public void setAOEDamage(int aoeDamage) {
		this.aoeDamage = aoeDamage;
	}
	
	public BufferedImage getHighlightedIcon() {
		return highlightIcon;
	}
	
	public BufferedImage getProjectileIcon() {
		return projectileIcon;
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
		else angle = Util.calculateAngle(enemy.getLocation(), location);
		currentlyTargetedEnemy = enemy;
	}
	
	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	public int getGoldEarned() {
		return goldEarned;
	}
	
	public int getFireRate() {
		return fireRate;
	}
	
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}
	
	public Enemy getCurrentlyTargetedEnemy() {
		return currentlyTargetedEnemy;
	}
	
	public boolean contains(Point point) {
		return point.x >= location.x && point.x <= location.x + getWidth()
				&& point.y >= location.y && point.y <= location.y + getHeight();
	}
}
