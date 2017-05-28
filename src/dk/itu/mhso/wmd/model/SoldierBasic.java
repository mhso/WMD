package dk.itu.mhso.wmd.model;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class SoldierBasic extends Enemy {
	public SoldierBasic() {
		maxHealth = 1;
		currentHealth = maxHealth;
	}
	
	@Override
	Shape getTestShape() {
		return new Ellipse2D.Double(20, 20, 20, 20);
	}
}
