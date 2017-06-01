package dk.itu.mhso.wmd.model.enemyunits;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import dk.itu.mhso.wmd.model.Enemy;

public class SoldierBasic extends Enemy {
	public SoldierBasic(String name) {
		super(name);
		maxHealth = 1;
		currentHealth = maxHealth;
	}
}
