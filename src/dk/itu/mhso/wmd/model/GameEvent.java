package dk.itu.mhso.wmd.model;

import java.util.List;

import javax.swing.event.ChangeEvent;

public class GameEvent extends ChangeEvent {
	private List<Enemy> currentEnemies;

	public GameEvent(Object source, List<Enemy> currentEnemies) {
		super(source);
		this.currentEnemies = currentEnemies;
	}
	
	public List<Enemy> getCurrentEnemies() {
		return currentEnemies;
	}
}
