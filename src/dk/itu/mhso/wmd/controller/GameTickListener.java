package dk.itu.mhso.wmd.controller;

public interface GameTickListener extends GameListener {
	void onGameTick();
	
	void onGameChanged();
	
	void onAllyChanged();
	
	void onEnemyChanged();
	
	void onProjectileChanged();
	
	void onExplosion();
	
	void onAllyReset();
	
	void onEnemyReset();
	
	void onProjectileReset();
}
