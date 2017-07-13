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
import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.view.windows.WindowUnitUpgrade;

public abstract class Ally extends Unit {
	protected int cost;
	protected int range;
	protected int damage;
	protected int fireRate;
	protected int aoeDamage;
	protected int aoeRadius;
	protected int maxProjectiles;
	protected int upgradeLevel = 1;
	protected int worth;
	protected UpgradeInfo upgradeInfo;
	
	private int enemiesKilled;
	private int goldEarned;
	private int tick = -1;
	private BufferedImage highlightIcon;
	private List<Enemy> enemiesInRange = new ArrayList<>();
	private List<Projectile> activeProjectiles = new ArrayList<>();
	private Enemy currentlyTargetedEnemy;
	private BufferedImage projectileIcon;
	private WindowUnitUpgrade upgradeWindow;
	
	public Ally(String name) {
		super(name);
	}
	
	public void loadIcons(String unitName) {
		try {
			loadMainIcon(unitName);
			loadHighlightedIcon(unitName);
			loadProjectileIcon(unitName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void loadMainIcon(String unitName) throws IOException {
		icon = ImageIO.read(new File("resources/sprites/ally/"+unitName+".png"));
	}
	
	protected void loadHighlightedIcon(String unitName) throws IOException {
		highlightIcon = ImageIO.read(new File("resources/sprites/ally/"+unitName+"_highlight.png"));
	}
	
	protected void loadProjectileIcon(String unitName) throws IOException {
		projectileIcon = ImageIO.read(new File("resources/sprites/ally/"+unitName+"_projectile.png"));
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
	
	public void addProjectile(Projectile projectile) {
		activeProjectiles.add(projectile);
	}
	
	public List<Projectile> getCurrentProjectiles() { return activeProjectiles; }
	
	public int getMaxProjectiles() { return maxProjectiles; }
	
	public boolean hasCapacity() {
		return maxProjectiles == 0 || activeProjectiles.size() < maxProjectiles;
	}
	
	public void incrementTick() {
		if(hasCapacity()) tick++;
		if(tick < 0) tick = 0;
	}
	
	public boolean checkFireCooldown() {
		return tick % (WMDConstants.FIRE_RATE_INVERTION/fireRate) == 0;
	}
	
	public boolean canProjectilesMove() {
		return tick % WMDConstants.PROJECTILE_MOVE_MOD == 0;
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
	
	public void incrementWorth(int amount) {
		worth += amount;
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
		else angle = Util.calculateAngle(location, enemy.getLocation());
		currentlyTargetedEnemy = enemy;
	}
	
	public int getEnemiesKilled() {
		return enemiesKilled;
	}

	public int getGoldEarned() {
		return goldEarned;
	}
	
	public int getWorth() {
		return worth;
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
	
	public String toString() {
		return "Ally " + super.toString() + ", cost=" + cost + ", range=" + range + "damage=" + damage + ", fireRate=" + 
				fireRate + ", aoeDamage=" + aoeDamage + ", aoeRadius=" + aoeRadius + ", maxProjectiles=" + maxProjectiles + ", upgradeLevel="
				+ upgradeLevel + ", worth=" + worth + ", upgradeInfo=" + upgradeInfo + ", enemiesKilled=" + enemiesKilled + ", goldEarned=" 
				+ goldEarned + ", tick=" + tick + ", enemiesInRange=" + enemiesInRange + ", activeProjectiles=" + activeProjectiles + 
				", currentlyTargetedEnemy=" + currentlyTargetedEnemy;
	}
}
