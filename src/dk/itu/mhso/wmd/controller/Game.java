package dk.itu.mhso.wmd.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Enemy;
import dk.itu.mhso.wmd.model.Exit;
import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.model.Projectile;
import dk.itu.mhso.wmd.model.Wave;
import dk.itu.mhso.wmd.view.WindowGame;

public class Game {
	private static List<Level> levels = new ArrayList<>();
	private static Level currentLevel;
	private static int levelNr;
	private static List<Enemy> currentEnemies = new ArrayList<>();
	private static Wave currentWave;
	private static List<Ally> allies = new ArrayList<>();
	private static List<Projectile> activeProjectiles = new ArrayList<>();
	private static GameTimer gameTimer;
	private static WindowGame window;
	private static Map<String, ChangeListener> changeListeners = new HashMap<>();
	
	private static int money = 200;
	private static int lives = 20;
	
	public static void loadLevels() {
		try {
			for(Iterator<Path> it = Files.list(Paths.get("resources/levels")).iterator(); it.hasNext(); ) {
				levels.add(new Level(it.next().toString()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startGame() {
		startGame(0);
	}
	
	public static void startGame(int level) {
		levelNr = level;
		currentLevel = levels.get(level);
		window = new WindowGame(levels.get(level));
		addChangeListener("window", window);
		gameTimer = new GameTimer(10);
	}
	
	public static void addChangeListener(String name, ChangeListener l) {
		changeListeners.put(name, l);
	}
	
	public static List<Ally> getAllies() {
		return allies;
	}
	
	public static void addAlly(Ally ally) {
		allies.add(ally);
	}
	
	public static void decrementMoney(int amount) {
		money -= amount;
		if(money < 0) money = 0;
	}
	
	public static void incrementMoney(int amount) {
		money += amount;
	}
	
	private static void enemyDead(Enemy enemy) {
		setChanged(Game.class, "menu");
		setChanged(Game.class, "overlay");
	}
	
	public static boolean isWithinMainPath(Point point) {
		return currentLevel.getMainPathArea().contains(point);
	}
	
	public static void checkEnemiesInRange(Ally ally) {
		Iterator<Enemy> it = ally.getEnemiesInRange().iterator();
		while(it.hasNext()) {
			Enemy enemy = it.next();
			if(enemy == ally.getCurrentlyTargetedEnemy()) ally.setCurrentlyTargetedEnemy(null);
			it.remove();
		}
		
		for(Enemy enemy : currentEnemies) {
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
	
	public static void setChanged(Object source, String reciever) {
		ChangeListener listener = changeListeners.get(reciever);
		if(listener != null) listener.stateChanged(new ChangeEvent(source));
		else for(ChangeListener l : changeListeners.values()) l.stateChanged(new ChangeEvent(source));
	}
	
	public static int getMoneyAmount() { return money; }
	
	public static int getLivesAmount() { return lives; }
	
	public static List<Projectile> getCurrentProjectiles() { return activeProjectiles; }
	
	public static List<Enemy> getCurrentEnemies() { return currentEnemies; }

	public static Level getCurrentLevel() { return currentLevel; }
	
	public static int getCurrentLevelNr() { return levelNr+1; }
	
	public static int getCurrentWaveNr() { return currentLevel.getCurrentWaveNr(); }
	
	private static class GameTimer implements ActionListener {
		private Timer timer;
		private int enemyDelay;
		private final int ENEMY_SPAWN_MOD = 200;
		private final int ENEMY_MOVE_MOD = 2;
		private final int PROJECTILE_MOVE_MOD = 10;
		
		public GameTimer(int delay) {
			timer = new Timer(delay, this);
			currentWave = currentLevel.getNextWave();
			timer.start();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!currentWave.isEmpty() && enemyDelay % ENEMY_SPAWN_MOD == 0) {
				Enemy nextEnemy = currentWave.getNextEnemy();
				if(nextEnemy != null) currentEnemies.add(nextEnemy);
				setChanged(this, "overlay");
			}
			enemyDelay++;
			if(enemyDelay < 0) enemyDelay = 0;
			setChanged(this, "window");
			
			if(enemyDelay % PROJECTILE_MOVE_MOD == 0) moveProjectiles();
			
			fireAllies();
			
			if(enemyDelay % ENEMY_MOVE_MOD == 0) moveEnemies();
		}
		
		private void fireAllies() {
			Iterator<Ally> it = allies.iterator();
			while(it.hasNext()) {
				Ally ally = it.next();
				checkEnemiesInRange(ally);
				for(Enemy enemy : ally.getEnemiesInRange()) {
					if(ally.getCurrentlyTargetedEnemy() == null || enemy.getCurrentHealth() > ally.getCurrentlyTargetedEnemy().getCurrentHealth()) {
						ally.setCurrentlyTargetedEnemy(enemy);
					}
				}
				if(ally.getCurrentlyTargetedEnemy() != null) {
					if(enemyDelay % ally.getFireRate() == 0) 
						activeProjectiles.add(new Projectile(ally, ally.getCurrentlyTargetedEnemy()));
				}
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
					if(projectile.getAlly().getAOEDamage() > 0) {
						for(Enemy enemy : currentEnemies) {
							if(new Ellipse2D.Double(projectile.getMiddlePoint().x - WMDConstants.AOE_RADIUS, 
									projectile.getMiddlePoint().y - WMDConstants.AOE_RADIUS, 
									WMDConstants.AOE_RADIUS*2, WMDConstants.AOE_RADIUS*2).contains(enemy.getLocation())) {
								enemy.decrementHealth(projectile.getAlly().getAOEDamage());
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
				
				if(!enemy.isActive()) {
					incrementMoney(10 * enemy.getMaxHealth());
					enemyDead(enemy);
					it.remove();
				}
				else {
					for(Exit exit : currentLevel.getExits()) if(exit.hasExited(enemy)) {
						lives--;
						enemy.setActive(false);
						enemyDead(enemy);
						it.remove();
						continue;
					}
				}
			}
		}
	}
}
