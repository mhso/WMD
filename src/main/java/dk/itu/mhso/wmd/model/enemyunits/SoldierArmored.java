package dk.itu.mhso.wmd.model.enemyunits;

import dk.itu.mhso.wmd.model.Enemy;

public class SoldierArmored extends Enemy {
	public SoldierArmored(String name) {
		super(name);
		maxHealth = 5;
		currentHealth = maxHealth;
	}
}
