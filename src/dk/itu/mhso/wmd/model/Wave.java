package dk.itu.mhso.wmd.model;

import java.util.List;

public class Wave {
	private int currentEnemy;
	private List<Enemy> enemies;
	
	public Wave(List<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public void spawnEnemy() {
		
	}
	
	public boolean isEmpty() {
		return currentEnemy == enemies.size();
	}
	
	public Enemy getNextEnemy() {
		if(currentEnemy == enemies.size()) return null;
		Enemy enemy = enemies.get(currentEnemy);
		currentEnemy++;
		return enemy;
	}
}
