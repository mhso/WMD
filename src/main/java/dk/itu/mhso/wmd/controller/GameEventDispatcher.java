package dk.itu.mhso.wmd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.itu.mhso.wmd.controller.listeners.GameListener;
import dk.itu.mhso.wmd.controller.listeners.GameMoneyListener;
import dk.itu.mhso.wmd.controller.listeners.GameStateListener;
import dk.itu.mhso.wmd.controller.listeners.GameTickListener;
import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Enemy;
import dk.itu.mhso.wmd.model.Explosion;

public class GameEventDispatcher {
	private static Map<String, List<GameListener>> gameListeners = new HashMap<>();
	
	public static void addGameListener(String name, GameListener l) {
		if(gameListeners.get(name) != null) gameListeners.get(name).add(l);
		else {
			List<GameListener> listenerList = new ArrayList<>();
			listenerList.add(l);
			gameListeners.put(name, listenerList);
		}
	}
	
	public static void moneyChanged() {
		for(GameListener listener : gameListeners.get("moneyChanged")) {
			GameMoneyListener gsl = (GameMoneyListener) listener;
			gsl.onMoneyChanged();
		}
	}
	
	public static void livesChanged() {
		for(GameListener listener : gameListeners.get("livesChanged")) {
			GameStateListener gsl = (GameStateListener) listener;
			gsl.onLivesChanged();
		}
	}
	
	public static void enemySpawned() {
		for(GameListener listener : gameListeners.get("enemySpawned")) {
			GameStateListener gsl = (GameStateListener) listener;
			gsl.onEnemySpawned();
		}
	}
	
	public static void enemyDespawned() {
		for(GameListener listener : gameListeners.get("enemyDespawned")) {
			GameStateListener gsl = (GameStateListener) listener;
			gsl.onEnemyDespawned();
		}
	}
	
	public static void waveStarted() {
		for(GameListener listener : gameListeners.get("waveStarted")) {
			GameStateListener gsl = (GameStateListener) listener;
			gsl.onWaveStarted();
		}
	}
	
	public static void waveEnded() {
		for(GameListener listener : gameListeners.get("waveEnded")) {
			GameStateListener gsl = (GameStateListener) listener;
			gsl.onWaveEnded();
		}
	}
	
	public static void gameTick() {
		for(GameListener listener : gameListeners.get("gameTick")) {
			GameTickListener gsl = (GameTickListener) listener;
			gsl.onGameTick();
		}
	}
}