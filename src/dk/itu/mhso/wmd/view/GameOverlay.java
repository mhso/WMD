package dk.itu.mhso.wmd.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Dimension;

public class GameOverlay extends JPanel {
	private JPanel leftMainPanel;

	public GameOverlay() {
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		leftMainPanel = new JPanel();
		add(leftMainPanel, BorderLayout.WEST);
		leftMainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel deflatedLeftPanel = new JPanel();
		leftMainPanel.add(deflatedLeftPanel);
		
		JPanel inflatedLeftPanel = new JPanel();
		inflatedLeftPanel.setLayout(new BorderLayout(0, 10));
		
		JButton buttonInflateLeft = new JButton(">");
		buttonInflateLeft.addActionListener(e -> showInflatedLeftPanel(inflatedLeftPanel));
		deflatedLeftPanel.add(buttonInflateLeft);
		
		JPanel panel = new JPanel();
		inflatedLeftPanel.add(panel, BorderLayout.NORTH);
		
		JButton buttonDeflateLeft = new JButton("<");
		buttonDeflateLeft.addActionListener(e -> showDeflatedLeftPanel(deflatedLeftPanel));
		panel.add(buttonDeflateLeft);
		buttonDeflateLeft.setPreferredSize(new Dimension(41, 23));
		buttonDeflateLeft.setEnabled(true);
		
		JPanel panelTowers = new JPanel();
		inflatedLeftPanel.add(panelTowers);
		panelTowers.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnNewButton = new JButton("Tower 1");
		panelTowers.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Tower 2");
		panelTowers.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Tower 3");
		panelTowers.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Tower 4");
		panelTowers.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Tower 5");
		panelTowers.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Tower 6");
		panelTowers.add(btnNewButton_5);
		
		showDeflatedLeftPanel(deflatedLeftPanel);
	}
	
	private void showInflatedLeftPanel(JPanel inflatedPanel) {
		leftMainPanel.removeAll();
    	leftMainPanel.add(inflatedPanel);
    }
	
	private void showDeflatedLeftPanel(JPanel deflatedPanel) {
    	leftMainPanel.removeAll();
    	leftMainPanel.add(deflatedPanel);
    }
}
