package dk.itu.mhso.wmd.controller;

public interface GameStateListener extends GameMoneyListener {
	void onLivesChanged();
	
	void onEnemySpawned();
	
	void onEnemyDespawned();
	
	void onWaveEnded();
	
	void onWaveStarted();
}
