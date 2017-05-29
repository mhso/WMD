package dk.itu.mhso.wmd.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverlay extends JPanel {
	private TowersMenu towers;
	
	public GameOverlay() {
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		setFocusable(true);
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				WindowGame.canvas.checkHighlight(e.getPoint());
			}
		});
		
		towers = new TowersMenu(this);
		
		JPanel leftMainPanel = new JPanel();
		leftMainPanel.setBackground(Style.OVERLAY_MENU_MAIN);
		add(leftMainPanel, BorderLayout.WEST);
		leftMainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel deflatedLeftPanel = new JPanel();
		deflatedLeftPanel.setBackground(Style.OVERLAY_MENU_MAIN);
		leftMainPanel.add(deflatedLeftPanel);
		
		JPanel inflatedLeftPanel = new JPanel();
		inflatedLeftPanel.setBackground(Style.OVERLAY_MENU_MAIN);
		inflatedLeftPanel.setLayout(new BorderLayout(0, 10));
		
		JButton buttonInflateLeft = new JButton(">");
		buttonInflateLeft.addActionListener(e -> towers.showDropdown(leftMainPanel));
		deflatedLeftPanel.add(buttonInflateLeft);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel topMainPanel = new JPanel();
		topMainPanel.setOpaque(false);
		centerPanel.add(topMainPanel, BorderLayout.NORTH);
		
		JPanel topPanel = new JPanel();
		topMainPanel.add(topPanel);
		topPanel.setBackground(Style.OVERLAY_MENU_MAIN);
		
		JLabel labelLevel = new JLabel("Level ");
		labelLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelLevel.setForeground(Color.WHITE);
		topPanel.add(labelLevel);
		
		JLabel labelEnemiesRemaining = new JLabel("Enemies: ");
		labelEnemiesRemaining.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelEnemiesRemaining.setForeground(Color.WHITE);
		topPanel.add(labelEnemiesRemaining);
		
		JPanel southMainPanel = new JPanel();
		southMainPanel.setOpaque(false);
		centerPanel.add(southMainPanel, BorderLayout.SOUTH);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(9, 40, 71));
		southMainPanel.add(southPanel);
		
		JLabel labelMoney = new JLabel("Money: ");
		labelMoney.setForeground(Color.WHITE);
		labelMoney.setFont(new Font("Tahoma", Font.PLAIN, 14));
		southPanel.add(labelMoney);
		
		JLabel labelLives = new JLabel("Lives: ");
		labelLives.setForeground(Color.WHITE);
		labelLives.setFont(new Font("Tahoma", Font.PLAIN, 14));
		southPanel.add(labelLives);
		
		JPanel panel = new JPanel();
		inflatedLeftPanel.add(panel, BorderLayout.NORTH);
	}
}
