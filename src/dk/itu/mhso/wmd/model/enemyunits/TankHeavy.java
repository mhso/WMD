package dk.itu.mhso.wmd.model.enemyunits;

import dk.itu.mhso.wmd.model.Enemy;

public class TankHeavy extends Enemy {
	public TankHeavy(String name) {
		super(name);
		maxHealth = 35;
		currentHealth = maxHealth;
	}
}
