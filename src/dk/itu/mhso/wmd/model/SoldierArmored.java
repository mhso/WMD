package dk.itu.mhso.wmd.model;

import java.awt.Shape;

public class SoldierArmored extends Enemy {
	public SoldierArmored() {
		maxHealth = 5;
		currentHealth = maxHealth;
	}
	
	@Override
	Shape getTestShape() {
		return null;
	}
}
