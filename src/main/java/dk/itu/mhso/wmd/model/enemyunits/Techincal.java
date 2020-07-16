package dk.itu.mhso.wmd.model.enemyunits;

import dk.itu.mhso.wmd.model.Enemy;

public class Techincal extends Enemy {
	public Techincal(String name) {
		super(name);
		maxHealth = 10;
		currentHealth = maxHealth;
	}
}
