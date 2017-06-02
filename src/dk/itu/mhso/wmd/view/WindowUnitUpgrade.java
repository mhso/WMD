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

import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Ally;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;

public class WindowUnitUpgrade extends JPopupMenu implements ChangeListener {
	private Ally ally;
	private JLabel labelEnemiesKilled;
	private JLabel labelGoldEarned;
	
	private final int WIDTH = 200;
	private final int HEIGHT = 200;
	private JButton buttonUpgRange;
	private JButton buttonUpgDamage;
	private JLabel labelDamage;
	private JLabel labelRange;
	private JLabel labelAOEsize;
	private JButton buttonUpgAOEsize;
	
	public WindowUnitUpgrade(Ally ally) {
		this.ally = ally;
		setLayout(new BorderLayout(0, 10));
		setBackground(Style.OVERLAY_MENU_MAIN);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true), 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
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
		panelIcons.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton upgradeButton = new JButton("Upgrade to Next Tier");
		upgradeButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		upgradeButton.setForeground(Color.RED);
		upgradeButton.setBackground(getBackground());
		panelIcons.add(upgradeButton);
		
		JPanel panelStats = new JPanel();
		panelStats.setOpaque(false);
		panelCenter.add(panelStats, BorderLayout.CENTER);
		panelStats.setLayout(new GridLayout(0, 2, 0, 0));
		
		labelDamage = new JLabel("Damage: " + ally.getDamage());
		labelDamage.setForeground(Color.WHITE);
		labelDamage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelStats.add(labelDamage);
		
		buttonUpgDamage = new JButton("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentDamageCost()) +"$)</body></html>");
		buttonUpgDamage.addActionListener(e -> {
			ally.getUpgradeInfo().upgradeDamage();
			statUpdated();
		});
		buttonUpgDamage.setBackground(getBackground());
		buttonUpgDamage.setForeground(Color.WHITE);
		buttonUpgDamage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelStats.add(buttonUpgDamage);
		
		labelRange = new JLabel("Range: " + ally.getRange());
		labelRange.setForeground(Color.WHITE);
		labelRange.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelStats.add(labelRange);
		
		buttonUpgRange = new JButton("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentRangeCost()) +"$)</body></html>");
		buttonUpgRange.addActionListener(e -> {
			ally.getUpgradeInfo().upgradeRange();
			statUpdated();
		});
		buttonUpgRange.setBackground(getBackground());
		buttonUpgRange.setForeground(Color.WHITE);
		buttonUpgRange.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelStats.add(buttonUpgRange);
		
		if(ally.getAOEDamage() > 0) {
			labelAOEsize = new JLabel("AOE Radius: " + ally.getAOERadius());
			labelAOEsize.setForeground(Color.WHITE);
			labelAOEsize.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panelStats.add(labelAOEsize);
			
			buttonUpgAOEsize = new JButton("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentAOERadiusCost()) +"$)</body></html>");
			buttonUpgAOEsize.addActionListener(e -> {
				ally.getUpgradeInfo().upgradeAOERadius();
				statUpdated();
			});
			buttonUpgAOEsize.setBackground(getBackground());
			buttonUpgAOEsize.setForeground(Color.WHITE);
			buttonUpgAOEsize.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panelStats.add(buttonUpgAOEsize);
		}
		
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
	
	private void statUpdated() {
		buttonUpgDamage.setText("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentDamageCost()) +"$)</body></html>");
		buttonUpgRange.setText("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentRangeCost()) +"$)</body></html>");
		buttonUpgAOEsize.setText("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentAOERadiusCost()) +"$)</body></html>");
		labelDamage.setText("Damage: " + ally.getDamage());
		labelRange.setText("Range: " + ally.getRange());
		labelAOEsize.setText("AOE Radius: " + ally.getAOERadius());
		WindowGame.canvas.setUnitRangeCircle(ally.getRangeCircle());
	}
	
	private Point getPos() {
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;
		int x = ally.getMiddlePoint().x - 40 - width;
		int y = ally.getMiddlePoint().y - width/2;
		if(ally.getMiddlePoint().x < Game.window.getContentPane().getWidth()/2) x = ally.getMiddlePoint().x + 40;
		if(y < 0) y = 0;
		else if(y > Game.window.getContentPane().getHeight() - width) y = Game.window.getContentPane().getHeight() - width;
		return new Point(x, y);
	}
	
	public void showDropdown() {
		show(Game.window.getContentPane(), getPos().x, getPos().y);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		labelEnemiesKilled.setText("Enemies Killed: " + ally.getEnemiesKilled());
		labelGoldEarned.setText("Gold Earned: " + ally.getGoldEarned());
		
		if(buttonUpgDamage.isEnabled() && Game.getMoneyAmount() < ally.getUpgradeInfo().getCurrentDamageCost()) buttonUpgDamage.setEnabled(false);
		else if(!buttonUpgDamage.isEnabled() && Game.getMoneyAmount() >= ally.getUpgradeInfo().getCurrentDamageCost()) buttonUpgDamage.setEnabled(true);
		
		if(buttonUpgRange.isEnabled() && Game.getMoneyAmount() < ally.getUpgradeInfo().getCurrentRangeCost()) buttonUpgRange.setEnabled(false);
		else if(!buttonUpgRange.isEnabled() && Game.getMoneyAmount() >= ally.getUpgradeInfo().getCurrentRangeCost()) buttonUpgRange.setEnabled(true);
	}
}
