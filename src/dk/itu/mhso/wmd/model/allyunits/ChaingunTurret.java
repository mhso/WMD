package dk.itu.mhso.wmd.model.allyunits;

import dk.itu.mhso.wmd.model.Ally;

public class ChaingunTurret extends Ally {
	public ChaingunTurret(String name) {
		super(name);
		cost = 600;
		worth = cost;
		range = 200;
		fireRate = 250;
		damage = 1;
	}
}
