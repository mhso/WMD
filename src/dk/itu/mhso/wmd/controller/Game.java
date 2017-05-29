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
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Enemy;
import dk.itu.mhso.wmd.model.Exit;
import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.model.Projectile;
import dk.itu.mhso.wmd.model.Wave;
import dk.itu.mhso.wmd.view.WindowGame;

public class Game extends Observable {
	private static List<Level> levels = new ArrayList<>();
	private static Level currentLevel;
	private static List<Enemy> currentEnemies;
	private static List<Ally> allies = new ArrayList<>();
	private static List<Projectile> activeProjectiles = new ArrayList<>();
	private static GameTimer gameTimer;
	private static WindowGame window;
	
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
		currentLevel = levels.get(level);
		window = new WindowGame(levels.get(level));
		gameTimer = new GameTimer(10);
		
	}
	
	public static List<Ally> getAllies() {
		return allies;
	}
	
	public static void addAlly(Ally ally) {
		allies.add(ally);
	}
	
	public static void checkEnemiesInRange(Ally ally) {
		for(Enemy enemy : currentEnemies) {
			if(ally.getRangeCircle().contains(new Point(enemy.getLocation().x, enemy.getLocation().y))) {
				if(!ally.getEnemiesInRange().contains(enemy)) ally.addEnemyInRange(enemy);
			}
			else if(ally.getEnemiesInRange().contains(enemy)) ally.removeEnemyFromInRange(enemy);
		}
		if(ally.getEnemiesInRange().isEmpty()) ally.setCurrentlyTargetedEnemy(null);
	}
	
	public static List<Projectile> getCurrentProjectiles() { return activeProjectiles; }
	
	public static List<Enemy> getCurrentEnemies() { return currentEnemies; }

	public static Level getCurrentLevel() { return currentLevel; }
	
	private static class GameTimer implements ActionListener {
		private Timer timer;
		private Wave currentWave;
		private int enemyDelay;
		private final int ENEMY_SPAWN_MOD = 250;
		private final int ENEMY_MOVE_MOD = 2;
		
		public GameTimer(int delay) {
			timer = new Timer(delay, this);
			currentWave = currentLevel.getNextWave();
			currentEnemies = new ArrayList<>();
			timer.start();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(enemyDelay % ENEMY_SPAWN_MOD == 0) {
				Enemy nextEnemy = currentWave.getNextEnemy();
				if(nextEnemy != null) currentEnemies.add(nextEnemy);
			}
			enemyDelay++;
			if(enemyDelay < 0) enemyDelay = 0;
			window.stateChanged(new ChangeEvent(this));
			
			moveProjectiles();
			
			fireAllies();
			
			if(enemyDelay % ENEMY_MOVE_MOD == 0) moveEnemies();
		}
		
		private void fireAllies() {
			Iterator<Ally> it = allies.iterator();
			while(it.hasNext()) {
				Ally ally = it.next();
				checkEnemiesInRange(ally);
				for(Enemy enemy : ally.getEnemiesInRange()) {
					if(ally.getCurrentlyTargetedEnemy() == null || enemy.getCurrentHealth() < ally.getCurrentlyTargetedEnemy().getCurrentHealth()) {
						ally.setCurrentlyTargetedEnemy(enemy);
					}
				}
				if(ally.getCurrentlyTargetedEnemy() != null) {
					activeProjectiles.add(new Projectile(ally, ally.getCurrentlyTargetedEnemy()));
				}
			}
		}
		
		private void moveProjectiles() {
			
		}
		
		private void moveEnemies() {
			Iterator<Enemy> it = currentEnemies.iterator();
			while(it.hasNext()) {
				Enemy enemy = it.next();
				enemy.move();
				if(!enemy.isActive()) {
					it.remove();
					continue;
				}
				for(Exit exit : currentLevel.getExits()) if(exit.hasExited(enemy)) it.remove();
			}
		}
	}
}
