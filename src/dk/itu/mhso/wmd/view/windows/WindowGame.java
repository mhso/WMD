package dk.itu.mhso.wmd.view.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.controller.GameTickListener;
import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.view.panels.Canvas;
import dk.itu.mhso.wmd.view.panels.GameOverlay;

public class WindowGame extends JFrame implements GameTickListener {
	public static Canvas canvas;
	private GameOverlay overlay;
	private boolean changed;
	
	public WindowGame(Level level) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel overlayPanel = new JPanel();
		overlayPanel.setLayout(new OverlayLayout(overlayPanel));
		overlayPanel.setPreferredSize(getPreferredSize());
		
		JPanel gameOverlay = new JPanel();
		gameOverlay.setOpaque(false);
		gameOverlay.setLayout(new BorderLayout());

		canvas = new Canvas();
		canvas.setLevel(level);
		
		overlay = new GameOverlay();
		overlay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Game.setPaused(true);
					new WindowPauseMenu();
				}
			}
		});
		
		gameOverlay.add(overlay);
		
		overlayPanel.add(gameOverlay);
		overlayPanel.add(canvas);
		
		add(overlayPanel);
		
		setPreferredSize(new Dimension(level.getBGImage().getWidth(), level.getBGImage().getHeight()));
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void showWaveText(String text, int durationMs) {
		overlay.showWaveLabel(text, durationMs);
	}
	
	@Override
	public void onGameTick() {
		if(changed)repaint();
	}
	
	@Override
	public void onGameChanged() {
		
	}

	@Override
	public void onAllyChanged() {
		
	}

	@Override
	public void onEnemyChanged() {
		
	}

	@Override
	public void onProjectileChanged() {
		
	}

	@Override
	public void onExplosion() {
		
	}

	@Override
	public void onAllyReset() {
		
	}

	@Override
	public void onEnemyReset() {
		
	}

	@Override
	public void onProjectileReset() {
		
	}
}
