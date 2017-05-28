package dk.itu.mhso.wmd.view;

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
	
	public WindowGame(Level level) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new OverlayLayout(contentPane));
		
		canvas = new Canvas();
		canvas.setLevel(level);
		contentPane.add(canvas);
		
		setPreferredSize(WMDConstants.WINDOW_SIZE_BASE);
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void stateChanged(ChangeEvent ce) {
		GameEvent ge = (GameEvent) ce;
		canvas.setCurrentEnemies(ge.getCurrentEnemies());
		canvas.repaint();
	}
}
