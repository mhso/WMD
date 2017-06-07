package dk.itu.mhso.wmd.controller;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.Main;
import dk.itu.mhso.wmd.Resources;
import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Enemy;
import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.model.Projectile;
import dk.itu.mhso.wmd.model.Wave;
import dk.itu.mhso.wmd.view.windows.WindowGame;
import dk.itu.mhso.wmd.model.Explosion;

public class Game {
	public static WindowGame window;
	
	private static List<Level> levels = new ArrayList<>();
	private static Level currentLevel;
	private static int levelNr;
	private static List<Enemy> currentEnemies = new ArrayList<>();
	private static Wave currentWave;
	private static List<Ally> allies = new ArrayList<>();
	private static List<Projectile> activeProjectiles = new ArrayList<>();
	private static List<Explosion> explosions = new ArrayList<>();
	private static GameTimer gameTimer;
	private static Map<String, ChangeListener> changeListeners = new HashMap<>();
	private static int waveCountdown = 20;
	
	private static int money;
	private static int lives;
	
	public static void addLevel(Level level) {
		levels.add(level);
	}
	
	public static void loadLevels() {
		try {
			for(Iterator<Path> it = Files.list(Paths.get(Resources.getLevelsPath())).iterator(); it.hasNext(); ) {
				levels.add(new Level(it.next().toString()));
			}
			Resources.loadExplosionImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Level> getLevels() {
		return levels;
	}
	
	public static void startGame() {
		startGame(0);
	}
	
	public static void startGame(int level) {
		levelNr = level;
		currentLevel = levels.get(level);
		money = currentLevel.getLevelInfo().getStartingMoney();
		if(Main.CHEAT_MODE) money = 100_000;
		lives = currentLevel.getLevelInfo().getLives();
		window = new WindowGame(levels.get(level));
		addChangeListener("window", window);
		gameTimer = new GameTimer(1000/WMDConstants.DEFAULT_TICKRATE);
	}
	
	public static void setPaused(boolean paused) {
		if(paused) gameTimer.stop();
		else gameTimer.start();
	}
	
	public static void addChangeListener(String name, ChangeListener l) {
		changeListeners.put(name, l);
	}
	
	public static List<Explosion> getExplosions() {
		return explosions;
	}
	
	public static List<Ally> getAllies() {
		return allies;
	}
	
	public static void addAlly(Ally ally) {	allies.add(ally); }
	
	public static void removeAlly(Ally ally) { allies.remove(ally); }
	
	public static void decrementMoney(int amount) {
		money -= amount;
		if(money < 0) money = 0;
		updateOverlayAndMenu();
	}
	
	public static void incrementMoney(int amount) {
		money += amount;
		updateOverlayAndMenu();
	}
	
	private static void enemyKilled(Enemy enemy) {
		incrementMoney(10 * enemy.getMaxHealth());
		updateOverlayAndMenu();
	}
	
	private static void updateOverlayAndMenu() {
		setChanged(Game.class, "menu");
		setChanged(Game.class, "overlay");
	}
	
	public static boolean isWaveOver() {
		return currentWave.isEmpty() && currentEnemies.isEmpty();
	}
	
	public static boolean isWithinMainPath(Point point) {
		for(Path2D path : currentLevel.getMainPathAreas()) {
			if(path.contains(point)) return true;
		}
		return false;
	}
	
	public static void checkEnemiesInRange(Ally ally) {
		Iterator<Enemy> it = ally.getEnemiesInRange().iterator();
		while(it.hasNext()) {
			Enemy enemy = it.next();
			if(enemy == ally.getCurrentlyTargetedEnemy()) ally.setCurrentlyTargetedEnemy(null);
			it.remove();
		}
		
		for(Enemy enemy : currentEnemies) {
			if(!enemy.isVisible()) continue;
			if(ally.getRangeCircle().contains(new Point(enemy.getLocation().x, enemy.getLocation().y))) {
				if(!ally.getEnemiesInRange().contains(enemy)) ally.addEnemyInRange(enemy);
				if(ally.getCurrentlyTargetedEnemy() == null) ally.setCurrentlyTargetedEnemy(enemy);
			}
			else if(ally.getEnemiesInRange().contains(enemy)) {
				ally.removeEnemyFromInRange(enemy);
				if(ally.getCurrentlyTargetedEnemy() == enemy) ally.setCurrentlyTargetedEnemy(null);
			}
		}
		if(ally.getEnemiesInRange().isEmpty()) ally.setCurrentlyTargetedEnemy(null);
	}
	
	public static void toggleSpeed() {
		if(gameTimer.getDelay() == 1000/WMDConstants.DEFAULT_TICKRATE) gameTimer.setDelay(2);
		else gameTimer.setDelay(1000/WMDConstants.DEFAULT_TICKRATE);
	}
	
	public static void setChanged(Object source, String reciever) {
		ChangeListener listener = changeListeners.get(reciever);
		if(listener != null) listener.stateChanged(new ChangeEvent(source));
		else for(ChangeListener l : changeListeners.values()) l.stateChanged(new ChangeEvent(source));
	}
	
	public static int getMoneyAmount() { return money; }
	
	public static int getLivesAmount() { return lives; }
	
	public static int getWaveCountdown() { return waveCountdown; }
	
	public static void setWaveCountdown(int value) { waveCountdown = value; }
	
	public static List<Projectile> getCurrentProjectiles() { return activeProjectiles; }
	
	public static List<Enemy> getCurrentEnemies() { return currentEnemies; }

	public static Level getCurrentLevel() { return currentLevel; }
	
	public static int getCurrentLevelNr() { return levelNr+1; }
	
	public static int getCurrentWaveNr() { return currentLevel.getCurrentWaveNr(); }
	
	private static class GameTimer implements ActionListener {
		private Timer timer;
		private int gameTick;
		private final int ENEMY_SPAWN_MOD = 360;
		private final int PROJECTILE_MOVE_MOD = 20;
		
		public GameTimer(int delay) {
			timer = new Timer(delay, this);
			currentWave = currentLevel.getNextWave();
			timer.start();
		}
		
		public void setDelay(int delay) {
			timer.setDelay(delay);
		}
		
		public int getDelay() {
			return timer.getDelay();
		}
		
		public void stop() {
			timer.stop();
		}
		
		public void start() {
			timer.start();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!currentWave.isEmpty() && gameTick % ENEMY_SPAWN_MOD == 0) {
				for(int i = 0; i < currentLevel.getLevelInfo().getEnemiesPerSpawn(); i++) {
					Enemy nextEnemy = currentWave.getNextEnemy();
					if(nextEnemy != null) currentEnemies.add(nextEnemy);
					else break;
				}
				setChanged(this, "overlay");
			}
			gameTick++;
			if(gameTick < 0) gameTick = 0;
			setChanged(this, "window");
			
			if(!isWaveOver()) {
				if(gameTick % PROJECTILE_MOVE_MOD == 0) moveProjectiles();
				
				fireAllies();
				
				if(gameTick % (WMDConstants.ENEMY_MOVE_TICK_INVERSION-currentLevel.getLevelInfo().getEnemySpeed()) == 0) moveEnemies();
			}
			else {
				if(getDelay() != WMDConstants.DEFAULT_TICKRATE) setDelay(1000/WMDConstants.DEFAULT_TICKRATE);
				if(gameTick % 200 == 0) {
					updateOverlayAndMenu();
					if(waveCountdown == 0) {
						currentWave = currentLevel.getNextWave();
						waveCountdown = 20;
					}
					else waveCountdown--;
				}
			}
		}
		
		private void fireAllies() {
			Iterator<Ally> it = allies.iterator();
			while(it.hasNext()) {
				Ally ally = it.next();
				checkEnemiesInRange(ally);
				for(Enemy enemy : ally.getEnemiesInRange()) {
					if(ally.getCurrentlyTargetedEnemy() == null) {
						ally.setCurrentlyTargetedEnemy(enemy);
					}
				}
				if(ally.getCurrentlyTargetedEnemy() != null) {
					if(gameTick % (WMDConstants.FIRE_RATE_INVERTION-ally.getFireRate()) == 0) 
						activeProjectiles.add(ProjectileFactory.createProjectile(ally));
				}
				ally.getUpgradeWindow().stateChanged(new ChangeEvent(this));
			}
		}
		
		private void moveProjectiles() {
			Iterator<Projectile> it = activeProjectiles.iterator();
			while(it.hasNext()) {
				Projectile projectile = it.next();
				projectile.move();
				
				if(projectile.hasHit() || !currentEnemies.contains(projectile.getTarget())) projectile.setActive(false);
				
				if(!projectile.isActive()) {
					projectile.getTarget().decrementHealth(projectile.getAlly().getDamage());
					if(!projectile.getTarget().isActive()) {
						projectile.getAlly().incrementEnemiesKilled(1);
						projectile.getAlly().incrementGoldEarned(projectile.getTarget().getMaxHealth() * 10);
						currentEnemies.remove(projectile.getTarget());
						enemyKilled(projectile.getTarget());
					}
					
					if(projectile.getAlly().getAOEDamage() > 0) {
						int radius = projectile.getAlly().getAOERadius();
						explosions.add(new Explosion(Resources.getExplosionImages().get(radius), new Point(projectile.getTarget().getLocation().x - 
								Resources.getExplosionImages().get(radius)[0].getWidth()/2, 
								projectile.getTarget().getLocation().y - Resources.getExplosionImages().get(radius)[0].getHeight()/2)));
						Iterator<Enemy> itEnemy = currentEnemies.iterator();
						while(itEnemy.hasNext()) {
							Enemy enemy = itEnemy.next();
							if(enemy != projectile.getTarget() && new Ellipse2D.Double(projectile.getMiddlePoint().x - projectile.getAlly().getAOERadius(), 
									projectile.getMiddlePoint().y - projectile.getAlly().getAOERadius(), 
									projectile.getAlly().getAOERadius()*2, projectile.getAlly().getAOERadius()*2).contains(enemy.getLocation())) {
								enemy.decrementHealth(projectile.getAlly().getAOEDamage());
								if(!enemy.isActive()) {
									projectile.getAlly().incrementEnemiesKilled(1);
									projectile.getAlly().incrementGoldEarned(enemy.getMaxHealth() * 10);
									itEnemy.remove();
									enemyKilled(enemy);
								}
							}
						}
					}
					it.remove();
				}
			}
		}
		
		private void moveEnemies() {
			Iterator<Enemy> it = currentEnemies.iterator();
			while(it.hasNext()) {
				Enemy enemy = it.next();
				enemy.move();
				
				if(enemy.hasEscaped()) {
					lives--;
					enemy.setActive(false);
					it.remove();
					updateOverlayAndMenu();
					continue;
				}
			}
		}
	}
}
