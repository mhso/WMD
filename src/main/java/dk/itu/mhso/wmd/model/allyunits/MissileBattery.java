package dk.itu.mhso.wmd.model.allyunits;

import dk.itu.mhso.wmd.model.Ally;

public class MissileBattery extends Ally {
	public MissileBattery(String name) {
		super(name);
		cost = 1000;
		worth = cost;
		range = 250;
		fireRate = 20;
		damage = 5;
		aoeDamage = 2;
		aoeRadius = 100;
	}
}
