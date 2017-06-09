package dk.itu.mhso.wmd.model;

import java.util.List;

public class Wave {
	private int currentEnemy;
	private List<Enemy> enemies;
	private String description;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
