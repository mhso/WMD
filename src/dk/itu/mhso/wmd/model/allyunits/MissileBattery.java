package dk.itu.mhso.wmd.model.allyunits;

import dk.itu.mhso.wmd.model.Ally;

public class MissileBattery extends Ally {
	public MissileBattery() {
		cost = 1000;
		range = 250;
		fireRate = 250;
		damage = 5;
		aoeDamage = 2;
	}
}
