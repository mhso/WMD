package dk.itu.mhso.wmd.view.panels;

import javax.swing.JPanel;

import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.controller.GameStateListener;
import dk.itu.mhso.wmd.view.Style;
import dk.itu.mhso.wmd.view.windows.WindowGame;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GameOverlay extends JPanel implements GameStateListener {
	private TowersMenu towers;
	private JLabel labelLives;
	private JLabel labelMoney;
	private JLabel labelEnemiesRemaining;
	private JLabel labelLevel;
	private JLabel labelWave;
	
	private final int FONT_SIZE = 15;
	private JButton buttonSpeedUp;
	private FancyLabel labelWaveDescription;
	
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
		Game.addGameListener("waveStarted", this);
		Game.addGameListener("waveEnded", this);
		Game.addGameListener("enemySpawned", this);
		Game.addGameListener("enemyDespawned", this);
		Game.addGameListener("moneyChanged", this);
		Game.addGameListener("livesChanged", this);
		
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
		buttonInflateLeft.setFont(new Font(buttonInflateLeft.getFont().getName(), Font.PLAIN, FONT_SIZE));
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
		labelLevel.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
		labelLevel.setForeground(Color.WHITE);
		topPanelLabels[0] = labelLevel;
		
		labelWave = new JLabel("Wave " + (Game.getCurrentWaveNr() + 1));
		labelWave.setForeground(Color.WHITE);
		labelWave.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
		topPanelLabels[1] = labelWave;
		
		labelEnemiesRemaining = new JLabel("Enemies: " + Game.getCurrentEnemies().size());
		labelEnemiesRemaining.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
		labelEnemiesRemaining.setForeground(Color.WHITE);
		labelEnemiesRemaining.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(Game.isWaveOver()) Game.setWaveCountdown(0);
			}		
		});
		topPanelLabels[2] = labelEnemiesRemaining;
		
		for(JLabel waveLabel : topPanelLabels) {
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setBorder(new LineBorder(Color.WHITE));
			panel.add(waveLabel);
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
		labelMoney.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
		southPanel.add(labelMoney);
		
		labelLives = new JLabel("Lives: " + Game.getLivesAmount());
		labelLives.setForeground(Color.WHITE);
		labelLives.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
		southPanel.add(labelLives);
		
		labelWaveDescription = new FancyLabel();
		labelWaveDescription.setFont(new Font("Tahoma", Font.PLAIN, 45));
		labelWaveDescription.setHorizontalAlignment(SwingConstants.CENTER);
		centerPanel.add(labelWaveDescription, BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		add(rightPanel, BorderLayout.EAST);
		
		buttonSpeedUp = new JButton(">>");
		buttonSpeedUp.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		buttonSpeedUp.setBackground(Style.OVERLAY_MENU_MAIN);
		buttonSpeedUp.setBorderPainted(false);
		buttonSpeedUp.setForeground(Color.WHITE);
		buttonSpeedUp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonSpeedUp.addActionListener(e -> {
			buttonSpeedUp.setBorderPainted(!buttonSpeedUp.isBorderPainted());
			Game.toggleSpeed();
		});
		rightPanel.add(buttonSpeedUp);
		
		JPanel panel = new JPanel();
		inflatedLeftPanel.add(panel, BorderLayout.NORTH);
	}

	public void showWaveLabel(String text, int durationMs) {
		labelWaveDescription.fadeIn(text);
		Timer timer = new Timer(durationMs, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				labelWaveDescription.fadeOut();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	@Override
	public void onWaveEnded() {
		buttonSpeedUp.setEnabled(false);
		buttonSpeedUp.setBorderPainted(false);
		labelEnemiesRemaining.setText("Click To Start");
		labelWave.setText("Next Wave Starting In: " + Game.getWaveCountdown());
	}
	
	@Override
	public void onWaveStarted() {
		labelWave.setText("Wave " + (Game.getCurrentWaveNr() + 1));
		labelEnemiesRemaining.setText("Enemies: " + Game.getCurrentEnemies().size());
		labelLevel.setText("Level " + Game.getCurrentLevelNr() + ": " + Game.getCurrentLevel().getName());
		buttonSpeedUp.setEnabled(true);
	}
	
	@Override
	public void onEnemyDespawned() {
		labelEnemiesRemaining.setText("Enemies: " + Game.getCurrentEnemies().size());
	}
	
	@Override
	public void onMoneyChanged() {
		labelMoney.setText("Money: " + Game.getMoneyAmount() + "$");
	}

	@Override
	public void onLivesChanged() {
		labelLives.setText("Lives: " + Game.getLivesAmount());
	}

	@Override
	public void onEnemySpawned() {
		labelEnemiesRemaining.setText("Enemies: " + Game.getCurrentEnemies().size());
	}
	
	private class FancyLabel extends JLabel {
		private String text;
		private int transparency;
		
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			FontRenderContext frc = g2d.getFontRenderContext();
			GlyphVector gv = new Font("Tahoma", Font.PLAIN, 50).createGlyphVector(frc, text);
			Rectangle bounds = gv.getOutline().getBounds();
			Shape s = gv.getOutline((getWidth()/2) - (int)(bounds.getWidth()/2), (getHeight()/2) - (int)(bounds.getHeight()/2));
			
			g2d.setColor(new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), transparency));
			g2d.fill(s);
			g2d.setColor(new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), transparency));
			g2d.draw(s);
			g2d.setClip(s);
		}
		
		public void setTransparency(int transparency) {
			this.transparency = transparency;
		}
		
		public int getTransparency() {
			return transparency;
		}
		
		public void fadeIn(String text) {
			this.text = text;
			new FadeTimer(true);
		}
		
		public void fadeOut() {
			new FadeTimer(false);
		}
	}
	
	private class FadeTimer implements ActionListener {
		private Timer timer;
		private int delta = 15;
		
		public FadeTimer(boolean in) {
			if(!in) delta = -15;
			timer = new Timer(1000/17, this);
			timer.start();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			labelWaveDescription.setTransparency(labelWaveDescription.getTransparency() + delta);
			labelWaveDescription.repaint();
			if(labelWaveDescription.getTransparency() >= 255 || labelWaveDescription.getTransparency() <= 0) timer.stop();;
		}
		
	}
}
