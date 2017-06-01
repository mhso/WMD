package dk.itu.mhso.wmd.model.enemyunits;

import dk.itu.mhso.wmd.model.Enemy;

public class TankLight extends Enemy {
	public TankLight(String name) {
		super(name);
		maxHealth = 20;
		currentHealth = maxHealth;
	}
}
