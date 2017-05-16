package dk.itu.mhso.wmd.controller;

import java.util.ArrayList;
import java.util.List;

import dk.itu.mhso.wmd.model.Level;

public class Game {
	private List<Level> levels = new ArrayList<>();
	
	public void addLevel(Level l) {
		levels.add(l);
	}
	
	public void startGame() {
		
	}
	
	public void startGame(int level) {
		
	}
}
