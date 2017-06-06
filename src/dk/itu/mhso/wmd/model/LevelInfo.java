package dk.itu.mhso.wmd.model;

import java.io.Serializable;

public class LevelInfo implements Serializable {
	private static final long serialVersionUID = -3088775100952839677L;
	
	private int enemiesPerSpawn = 1;
	private int enemySpeed = 4;
	private int lives = 20;
	private int startingMoney = 200;
	
	public void setEnemiesPerSpawn(int enemies) {
		enemiesPerSpawn = enemies;
	}
	
	public int getEnemiesPerSpawn() {
		return enemiesPerSpawn;
	}
	
	public void setEnemiesSpeed(int speed) {
		enemySpeed = speed;
	}
	
	public int getEnemySpeed() {
		return enemySpeed;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getStartingMoney() {
		return startingMoney;
	}

	public void setStartingMoney(int startingMoney) {
		this.startingMoney = startingMoney;
	}
}
