package dk.itu.mhso.wmd.view.panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BoxLayout;

public class PanelChooseLevel extends JPanel {
	private int currentIndex;
	private JPanel cardPanel;
	private List<Level> levels;
	
	public PanelChooseLevel() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 15));
		
		JLabel labelHeader = new JLabel("Choose Level");
		labelHeader.setHorizontalAlignment(SwingConstants.CENTER);
		labelHeader.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(labelHeader, BorderLayout.NORTH);
		
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		add(cardPanel, BorderLayout.CENTER);
		
		JPanel panelLeft = new JPanel();
		add(panelLeft, BorderLayout.WEST);
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.X_AXIS));
		
		JButton buttonPrevious = new JButton("<");
		buttonPrevious.addActionListener(e -> showPreviousPanel());
		buttonPrevious.setBackground(getBackground());
		panelLeft.add(buttonPrevious);
		
		JPanel panelRight = new JPanel();
		add(panelRight, BorderLayout.EAST);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.X_AXIS));
		
		JButton buttonNext = new JButton(">");
		buttonNext.addActionListener(e -> showNextPanel());
		buttonNext.setBackground(getBackground());
		panelRight.add(buttonNext);
		
		JPanel panelBottom = new JPanel();
		add(panelBottom, BorderLayout.SOUTH);
		
		JButton buttonStartGame = new JButton("Start Game");
		if(Game.getLevels().isEmpty()) buttonStartGame.setEnabled(false);
		buttonStartGame.addActionListener(e -> Game.startGame(currentIndex));
		buttonStartGame.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelBottom.add(buttonStartGame, BorderLayout.SOUTH);
		
		createLevelPanels();
	}

	private void createLevelPanels() {
		levels = Game.getLevels();
		for(int i = 0; i < levels.size(); i++) {
			JPanel panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout(0, 10));
			
			JLabel labelImagePreview = new JLabel(new ImageIcon(levels.get(i).getBGImage().
					getScaledInstance(350, 200, Image.SCALE_SMOOTH)));
			panelCenter.add(labelImagePreview, BorderLayout.CENTER);
			
			JLabel labelLevelName = new JLabel("Level " + (i+1) + ": " + levels.get(i).getName());
			labelLevelName.setHorizontalAlignment(SwingConstants.CENTER);
			labelLevelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panelCenter.add(labelLevelName, BorderLayout.NORTH);
			
			cardPanel.add(panelCenter, "name_level" + (i+1));
		}
	}
	
	private void showPreviousPanel() {
		if(levels.size() == 1) return;
		CardLayout card = (CardLayout) cardPanel.getLayout();
		currentIndex--;
		if(currentIndex < 0) {
			currentIndex = levels.size() - 1;
			card.last(cardPanel);
		}
		else card.previous(cardPanel);
	}
	
	private void showNextPanel() {
		if(levels.size() == 1) return;
		CardLayout card = (CardLayout) cardPanel.getLayout();
		currentIndex++;
		if(currentIndex == levels.size()) {
			currentIndex = 0;
			card.first(cardPanel);
		}
		else card.next(cardPanel);
	}
}
