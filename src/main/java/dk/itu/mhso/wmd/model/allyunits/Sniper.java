package dk.itu.mhso.wmd.model.allyunits;

import dk.itu.mhso.wmd.model.Ally;

public class Sniper extends Ally {
	public Sniper(String name) {
		super(name);
		cost = 100;
		worth = cost;
		range = 300;
		fireRate = 35;
		damage = 1;
	}
}
