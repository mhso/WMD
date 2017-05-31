package dk.itu.mhso.wmd.view;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.controller.Game;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverlay extends JPanel implements ChangeListener {
	private TowersMenu towers;
	private JLabel labelLives;
	private JLabel labelMoney;
	private JLabel labelEnemiesRemaining;
	private JLabel labelLevel;
	private JLabel labelWave;
	
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
		Game.addChangeListener("overlay", this);
		
		JPanel leftMainPanel = new JPanel();
		leftMainPanel.setOpaque(false);
		add(leftMainPanel, BorderLayout.WEST);
		leftMainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel deflatedLeftPanel = new JPanel();
		deflatedLeftPanel.setOpaque(false);
		leftMainPanel.add(deflatedLeftPanel);
		
		JPanel inflatedLeftPanel = new JPanel();
		inflatedLeftPanel.setBackground(Style.OVERLAY_MENU_MAIN);
		inflatedLeftPanel.setLayout(new BorderLayout(0, 10));
		
		JButton buttonInflateLeft = new JButton(">");
		buttonInflateLeft.setFont(new Font(buttonInflateLeft.getFont().getName(), Font.PLAIN, 15));
		buttonInflateLeft.setForeground(Color.WHITE);
		buttonInflateLeft.setBackground(Style.OVERLAY_MENU_MAIN);
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
		
		labelLevel = new JLabel("Level " + Game.getCurrentLevelNr() + ": " + Game.getCurrentLevel().getName());
		labelLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelLevel.setForeground(Color.WHITE);
		topPanel.add(labelLevel);
		
		labelWave = new JLabel("Wave " + Game.getCurrentWaveNr());
		labelWave.setForeground(Color.WHITE);
		labelWave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanel.add(labelWave);
		
		labelEnemiesRemaining = new JLabel("Enemies: " + Game.getCurrentEnemies().size());
		labelEnemiesRemaining.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelEnemiesRemaining.setForeground(Color.WHITE);
		topPanel.add(labelEnemiesRemaining);
		
		JPanel southMainPanel = new JPanel();
		southMainPanel.setOpaque(false);
		centerPanel.add(southMainPanel, BorderLayout.SOUTH);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(9, 40, 71));
		southMainPanel.add(southPanel);
		
		labelMoney = new JLabel("Money: " + Game.getMoneyAmount() + "$");
		labelMoney.setForeground(Color.WHITE);
		labelMoney.setFont(new Font("Tahoma", Font.PLAIN, 14));
		southPanel.add(labelMoney);
		
		labelLives = new JLabel("Lives: " + Game.getLivesAmount());
		labelLives.setForeground(Color.WHITE);
		labelLives.setFont(new Font("Tahoma", Font.PLAIN, 14));
		southPanel.add(labelLives);
		
		JPanel panel = new JPanel();
		inflatedLeftPanel.add(panel, BorderLayout.NORTH);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		labelLevel.setText("Level " + Game.getCurrentLevelNr() + ": " + Game.getCurrentLevel().getName());
		labelEnemiesRemaining.setText("Enemies: " + Game.getCurrentEnemies().size());
		labelMoney.setText("Money: " + Game.getMoneyAmount() + "$");
		labelLives.setText("Lives: " + Game.getLivesAmount());
		labelWave.setText("Wave " + Game.getCurrentWaveNr());
	}
}
