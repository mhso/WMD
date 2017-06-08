package dk.itu.mhso.wmd.model.allyunits;

import dk.itu.mhso.wmd.model.Ally;

public class ParticleCannon extends Ally {
	public ParticleCannon(String name) {
		super(name);
		cost = 3000;
		worth = cost;
		range = 350;
		fireRate = 15;
		damage = 20;
		maxProjectiles = 1;
	}
}
