package dk.itu.mhso.wmd.view;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.controller.Game;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;

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
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		topMainPanel.add(topPanel);
		topPanel.setBackground(Style.OVERLAY_MENU_MAIN);
		
		JLabel[] topPanelLabels = new JLabel[3];
		
		labelLevel = new JLabel("Level " + Game.getCurrentLevelNr() + ": " + Game.getCurrentLevel().getName());
		labelLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelLevel.setForeground(Color.WHITE);
		topPanelLabels[0] = labelLevel;
		
		labelWave = new JLabel("Wave " + Game.getCurrentWaveNr());
		labelWave.setForeground(Color.WHITE);
		labelWave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanelLabels[1] = labelWave;
		
		labelEnemiesRemaining = new JLabel("Enemies: " + Game.getCurrentEnemies().size());
		labelEnemiesRemaining.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelEnemiesRemaining.setForeground(Color.WHITE);
		labelEnemiesRemaining.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(Game.isWaveOver()) Game.setWaveCountdown(0);
			}		
		});
		topPanelLabels[2] = labelEnemiesRemaining;
		
		for(JLabel label : topPanelLabels) {
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setBorder(new LineBorder(Color.WHITE));
			panel.add(label);
			topPanel.add(panel);
		}
		
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
	
	private void updateEnenmyButtonCauseSwingSucks() {
		if(labelEnemiesRemaining.getPreferredSize() != labelEnemiesRemaining.getMinimumSize()) 
			labelEnemiesRemaining.setPreferredSize(labelEnemiesRemaining.getMinimumSize());
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(!Game.isWaveOver()) {
			labelWave.setText("Wave " + Game.getCurrentWaveNr());
			labelEnemiesRemaining.setText("Enemies: " + Game.getCurrentEnemies().size());
			labelLevel.setText("Level " + Game.getCurrentLevelNr() + ": " + Game.getCurrentLevel().getName());
		}
		else {
			labelEnemiesRemaining.setText("Click To Start");
			labelWave.setText("Next Wave Starting In: " + Game.getWaveCountdown());
		}
		labelMoney.setText("Money: " + Game.getMoneyAmount() + "$");
		labelLives.setText("Lives: " + Game.getLivesAmount());
	}
}
