package dk.itu.mhso.wmd.model;

import java.util.List;

public class Wave {
	private int currentEnemy;
	private List<Enemy> enemies;
	
	public Wave(List<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public boolean isEmpty() {
		return currentEnemy == enemies.size();
	}
	
	public Enemy getNextEnemy() {
		if(currentEnemy == enemies.size()) return null;
		return enemies.get(currentEnemy++);
	}
}
