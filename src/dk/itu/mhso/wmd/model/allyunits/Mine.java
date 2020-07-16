package dk.itu.mhso.wmd.model.allyunits;

import dk.itu.mhso.wmd.model.Trap;

public class Mine extends Trap {
	public Mine(String name) {
		super(name);
		cost = 150;
		worth = cost;
		damage = 7;
		range = 40;
		aoeDamage = 2;
		aoeRadius = 125;
	}	
}
