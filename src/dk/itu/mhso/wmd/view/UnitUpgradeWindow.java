package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Ally;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;

public class UnitUpgradeWindow extends JPopupMenu implements ChangeListener {
	private Ally ally;
	private JLabel labelEnemiesKilled;
	private JLabel labelGoldEarned;
	
	private final int WIDTH = 140;
	private final int HEIGHT = 180;
	
	public UnitUpgradeWindow(Ally ally) {
		this.ally = ally;
		setLayout(new BorderLayout(0, 0));
		setBackground(Style.OVERLAY_MENU_MAIN);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(false);
		
		JPanel panelTop = new JPanel();
		panelTop.setOpaque(false);
		add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new BorderLayout(0, 0));
		
		JLabel labelHeader = new JLabel(ally.getName());
		labelHeader.setForeground(Color.WHITE);
		labelHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelHeader.setHorizontalAlignment(SwingConstants.CENTER);
		panelTop.add(labelHeader, BorderLayout.NORTH);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		add(panelCenter);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel panelIcons = new JPanel();
		panelIcons.setOpaque(false);
		panelCenter.add(panelIcons, BorderLayout.NORTH);
		panelIcons.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton upgradeButton1 = new JButton("Upg 1");
		upgradeButton1.setForeground(Color.WHITE);
		upgradeButton1.setBackground(getBackground());
		panelIcons.add(upgradeButton1);
		
		JButton upgradeButton2 = new JButton("Upg 2");
		upgradeButton2.setForeground(Color.WHITE);
		upgradeButton2.setBackground(getBackground());
		panelIcons.add(upgradeButton2);
		
		JPanel panelExplanations = new JPanel();
		panelExplanations.setOpaque(false);
		panelCenter.add(panelExplanations, BorderLayout.CENTER);
		panelExplanations.setLayout(new GridLayout(0, 2, 0, 0));
		
		JTextArea updateDescription1 = new JTextArea("Description 1");
		updateDescription1.setBackground(getBackground());
		updateDescription1.setLineWrap(true);
		updateDescription1.setEditable(false);
		updateDescription1.setForeground(Color.WHITE);
		panelExplanations.add(updateDescription1);
		
		JTextArea updateDescription2 = new JTextArea("Description 2");
		updateDescription2.setBackground(getBackground());
		updateDescription2.setLineWrap(true);
		updateDescription2.setEditable(false);
		updateDescription2.setForeground(Color.WHITE);
		panelExplanations.add(updateDescription2);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setOpaque(false);
		add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new GridLayout(0, 1, 0, 0));
		
		labelEnemiesKilled = new JLabel("Enemies Killed: " + ally.getEnemiesKilled());
		labelEnemiesKilled.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelEnemiesKilled.setForeground(Color.WHITE);
		panelSouth.add(labelEnemiesKilled);
		
		labelGoldEarned = new JLabel("Gold Earned: " + ally.getGoldEarned());
		labelGoldEarned.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelGoldEarned.setForeground(Color.WHITE);
		panelSouth.add(labelGoldEarned);
		
		pack();
	}
	
	private Point getPos() {
		int x = ally.getMiddlePoint().x - 40 - WIDTH;
		int y = ally.getMiddlePoint().y - HEIGHT/2;
		if(ally.getMiddlePoint().x < Game.window.getContentPane().getWidth()/2) x = ally.getMiddlePoint().x + 40;
		if(y < 0) y = 0;
		else if(y > Game.window.getContentPane().getHeight() - HEIGHT) y = Game.window.getContentPane().getHeight() - HEIGHT;
		return new Point(x, y);
	}
	
	public void showDropdown() {
		show(Game.window.getContentPane(), getPos().x, getPos().y);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		labelEnemiesKilled.setText("Enemies Killed: " + ally.getEnemiesKilled());
		labelGoldEarned.setText("Gold Earned: " + ally.getGoldEarned());
	}
}
