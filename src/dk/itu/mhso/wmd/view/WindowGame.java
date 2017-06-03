package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Level;

public class WindowGame extends JFrame implements ChangeListener {
	public static Canvas canvas;
	private GameOverlay overlay;
	
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
	
	@Override
	public void stateChanged(ChangeEvent ce) {
		repaint();
	}
}
