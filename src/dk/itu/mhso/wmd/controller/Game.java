package dk.itu.mhso.wmd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

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
	
	private class GameTimer implements ActionListener {
		private Timer timer;
		
		public GameTimer(int delay) {
			timer = new Timer(delay, this);
			timer.start();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}
}
