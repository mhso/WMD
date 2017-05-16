package dk.itu.mhso.wmd.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

public class Game {
	private List<Level> levels;

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
