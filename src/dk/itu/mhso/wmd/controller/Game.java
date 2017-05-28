package dk.itu.mhso.wmd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;

import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.model.Wave;
import dk.itu.mhso.wmd.view.WindowGame;

public class Game extends Observable {
	private static List<Level> levels = new ArrayList<>();
	private static Level currentLevel;
	private static GameTimer gameTimer;
	private static WindowGame window;
	
	public static void loadLevels() {
		try {
			for(Iterator<Path> it = Files.list(Paths.get("resources/levels")).iterator(); it.hasNext(); ) {
				levels.add(new Level(it.next().toString()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startGame() {
		startGame(0);
	}
	
	public static void startGame(int level) {
		currentLevel = levels.get(level);
		window = new WindowGame(levels.get(level));
		gameTimer = new GameTimer(100);
		
	}

	public static Level getCurrentLevel() { return currentLevel; }
	
	private static class GameTimer implements ActionListener {
		private Timer timer;
		private Wave currentWave;
		
		public GameTimer(int delay) {
			timer = new Timer(delay, this);
			timer.start();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}
}
