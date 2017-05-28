package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.model.GameEvent;
import dk.itu.mhso.wmd.model.Level;

public class WindowGame extends JFrame implements ChangeListener {
	private Canvas canvas;
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
		gameOverlay.add(overlay);
		
		overlayPanel.add(gameOverlay);
		overlayPanel.add(canvas);
		
		add(overlayPanel);
		
		setPreferredSize(WMDConstants.WINDOW_SIZE_BASE);
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void stateChanged(ChangeEvent ce) {
		GameEvent ge = (GameEvent) ce;
		canvas.setCurrentEnemies(ge.getCurrentEnemies());
		repaint();
	}
}
