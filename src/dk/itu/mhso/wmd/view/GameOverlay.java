package dk.itu.mhso.wmd.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Dimension;

public class GameOverlay extends JPanel {
	private TowersMenu towers;
	
	public GameOverlay() {
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		
		towers = new TowersMenu();
		
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		JPanel leftMainPanel = new JPanel();
		add(leftMainPanel, BorderLayout.WEST);
		leftMainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel deflatedLeftPanel = new JPanel();
		leftMainPanel.add(deflatedLeftPanel);
		
		JPanel inflatedLeftPanel = new JPanel();
		inflatedLeftPanel.setLayout(new BorderLayout(0, 10));
		
		JButton buttonInflateLeft = new JButton(">");
		buttonInflateLeft.addActionListener(e -> towers.showDropdown(leftMainPanel));
		deflatedLeftPanel.add(buttonInflateLeft);
		
		JPanel panel = new JPanel();
		inflatedLeftPanel.add(panel, BorderLayout.NORTH);
	}
}
