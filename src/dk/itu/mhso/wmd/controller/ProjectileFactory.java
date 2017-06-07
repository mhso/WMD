package dk.itu.mhso.wmd.controller;

import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.BeamProjectile;
import dk.itu.mhso.wmd.model.Projectile;
import dk.itu.mhso.wmd.model.allyunits.ParticleCannon;

public class ProjectileFactory {
	public static Projectile createProjectile(Ally ally) {
		if(ally instanceof ParticleCannon) return new BeamProjectile(null, ally, ally.getCurrentlyTargetedEnemy());
		else return new Projectile(null, ally, ally.getCurrentlyTargetedEnemy());
	}
}
