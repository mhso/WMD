package dk.itu.mhso.wmd.view.windows;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.view.Style;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;

public class WindowUnitUpgrade extends JPopupMenu implements ChangeListener {
	private final Ally ally;
	private JLabel labelEnemiesKilled;
	private JLabel labelGoldEarned;
	
	private JButton buttonUpgRange;
	private JButton buttonUpgDamage;
	private JLabel labelDamage;
	private JLabel labelRange;
	private JLabel labelAOEsize;
	private JButton buttonUpgAOEsize;
	private JButton buttonUpgFireRate;
	private JLabel labelFireRate;
	private JButton buttonSell;
	
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
		labelHeader.setForeground(Style.UPGRADE_MENU_HEADER);
		labelHeader.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelHeader.setHorizontalAlignment(SwingConstants.CENTER);
		panelTop.add(labelHeader, BorderLayout.NORTH);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		add(panelCenter);
		panelCenter.setLayout(new BorderLayout(0, 5));
		
		JPanel panelIcons = new JPanel();
		panelIcons.setOpaque(false);
		panelCenter.add(panelIcons, BorderLayout.NORTH);
		panelIcons.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelStats = new JPanel();
		panelStats.setOpaque(false);
		panelCenter.add(panelStats, BorderLayout.CENTER);
		panelStats.setLayout(new GridLayout(0, 2, 10, 0));
		
		labelDamage = new JLabel("Damage: " + ally.getDamage());
		labelDamage.setForeground(Color.WHITE);
		labelDamage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelStats.add(labelDamage);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setOpaque(false);
		add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new GridLayout(0, 1, 0, 0));
		
		if(ally.getUpgradeInfo() != null) {
			JButton upgradeButton = new JButton("Upgrade to Next Tier");
			upgradeButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
			upgradeButton.setForeground(Color.RED);
			upgradeButton.setBackground(getBackground());
			panelIcons.add(upgradeButton);
			
			buttonUpgDamage = new JButton("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentDamageCost()) +"$)</body></html>");
			buttonUpgDamage.addActionListener(e -> {
				int cost = ally.getUpgradeInfo().getCurrentDamageCost();
				ally.getUpgradeInfo().upgradeDamage();
				statUpdated(cost);
			});
			buttonUpgDamage.setBackground(getBackground());
			buttonUpgDamage.setForeground(Color.WHITE);
			buttonUpgDamage.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panelStats.add(buttonUpgDamage);
			
			labelFireRate = new JLabel("Fire Rate: " + ally.getFireRate());
			labelFireRate.setForeground(Color.WHITE);
			labelFireRate.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panelStats.add(labelFireRate);
			
			buttonUpgFireRate = new JButton("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentFireRateCost()) +"$)</body></html>");
			buttonUpgFireRate.addActionListener(e -> {
				int cost = ally.getUpgradeInfo().getCurrentFireRateCost();
				ally.getUpgradeInfo().upgradeFireRate();
				statUpdated(cost);
			});
			buttonUpgFireRate.setBackground(getBackground());
			buttonUpgFireRate.setForeground(Color.WHITE);
			buttonUpgFireRate.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panelStats.add(buttonUpgFireRate);
			
			labelRange = new JLabel("Range: " + ally.getRange());
			labelRange.setForeground(Color.WHITE);
			labelRange.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panelStats.add(labelRange);
			
			buttonUpgRange = new JButton("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentRangeCost()) +"$)</body></html>");
			buttonUpgRange.addActionListener(e -> {
				int cost = ally.getUpgradeInfo().getCurrentRangeCost();
				ally.getUpgradeInfo().upgradeRange();
				statUpdated(cost);
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
					int cost = ally.getUpgradeInfo().getCurrentAOERadiusCost();
					ally.getUpgradeInfo().upgradeAOERadius();
					statUpdated(cost);
				});
				buttonUpgAOEsize.setBackground(getBackground());
				buttonUpgAOEsize.setForeground(Color.WHITE);
				buttonUpgAOEsize.setFont(new Font("Tahoma", Font.PLAIN, 12));
				panelStats.add(buttonUpgAOEsize);
			}
			
			labelEnemiesKilled = new JLabel("Enemies Killed: " + ally.getEnemiesKilled());
			labelEnemiesKilled.setFont(new Font("Tahoma", Font.PLAIN, 12));
			labelEnemiesKilled.setForeground(Color.WHITE);
			panelSouth.add(labelEnemiesKilled);
			
			labelGoldEarned = new JLabel("Gold Earned: " + ally.getGoldEarned());
			labelGoldEarned.setFont(new Font("Tahoma", Font.PLAIN, 12));
			labelGoldEarned.setForeground(Color.WHITE);
			panelSouth.add(labelGoldEarned);
		}
			
		buttonSell = new JButton("Sell (+"+ (int)(ally.getWorth()*WMDConstants.SELL_RETURN_PERCENTAGE) +"$)");
		buttonSell.addActionListener(e -> sellUnit());
		buttonSell.setBackground(getBackground());
		buttonSell.setForeground(Color.WHITE);
		buttonSell.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonSell.setHorizontalAlignment(SwingConstants.LEFT);
		panelSouth.add(buttonSell);
		
		pack();
	}
	
	private void statUpdated(int cost) {
		Game.decrementMoney(cost);
		
		if(!ally.getUpgradeInfo().isDamageMaxed()) buttonUpgDamage.setText("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentDamageCost()) +"$)</body></html>");
		else {
			buttonUpgDamage.setText("Maxed");
			buttonUpgDamage.setEnabled(false);
		}
		
		if(!ally.getUpgradeInfo().isRangeMaxed()) buttonUpgRange.setText("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentRangeCost()) +"$)</body></html>");
		else {
			buttonUpgRange.setText("Maxed");
			buttonUpgRange.setEnabled(false);
		}
		
		if(!ally.getUpgradeInfo().isFireRateMaxed()) buttonUpgFireRate.setText("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentFireRateCost()) +"$)</body></html>");
		else {
			buttonUpgFireRate.setText("Maxed");
			buttonUpgFireRate.setEnabled(false);
		}
		
		if(ally.getAOEDamage() > 0) {
			if(!ally.getUpgradeInfo().isAOERadiusMaxed()) buttonUpgAOEsize.setText("<html><body>Upgrade<br>(" + (ally.getUpgradeInfo().getCurrentAOERadiusCost()) +"$)</body></html>");
			else {
				buttonUpgAOEsize.setText("Maxed");
				buttonUpgAOEsize.setEnabled(false);
			}
			
			labelAOEsize.setText("AOE Radius: " + ally.getAOERadius());
		}
		
		labelDamage.setText("Damage: " + ally.getDamage());
		labelRange.setText("Range: " + ally.getRange());
		labelFireRate.setText("Fire Rate: " + ally.getFireRate());
		
		buttonSell.setText("Sell (+"+ getSellValue() +"$)");
		
		WindowGame.canvas.setUnitRangeCircle(ally.getRangeCircle());
	}
	
	private Point getPos() {
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;
		int x = ally.getMiddlePoint().x - 40 - width;
		int y = ally.getMiddlePoint().y - width/2;
		if(ally.getMiddlePoint().x < Game.window.getContentPane().getWidth()/2) x = ally.getMiddlePoint().x + 40;
		if(y < 0) y = 0;
		else if(y > Game.window.getContentPane().getHeight() - width) y = Game.window.getContentPane().getHeight() - height;
		return new Point(x, y);
	}
	
	private void sellUnit() {
		Game.incrementMoney(getSellValue());
		WindowGame.canvas.setUnitRangeCircle(null);
		setVisible(false);
		Game.removeAlly(ally);
	}
	
	private int getSellValue() {
		return (int)(ally.getWorth()*WMDConstants.SELL_RETURN_PERCENTAGE);
	}
	
	public void showDropdown() {
		show(Game.window.getContentPane(), getPos().x, getPos().y);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(ally.getUpgradeInfo() == null) return;
		labelEnemiesKilled.setText("Enemies Killed: " + ally.getEnemiesKilled());
		labelGoldEarned.setText("Gold Earned: " + ally.getGoldEarned());
		
		if(!ally.getUpgradeInfo().isDamageMaxed()) {
			if(buttonUpgDamage.isEnabled() && Game.getMoneyAmount() < ally.getUpgradeInfo().getCurrentDamageCost()) buttonUpgDamage.setEnabled(false);
			else if(!buttonUpgDamage.isEnabled() && Game.getMoneyAmount() >= ally.getUpgradeInfo().getCurrentDamageCost()) buttonUpgDamage.setEnabled(true);
		}
		
		if(!ally.getUpgradeInfo().isFireRateMaxed()) {
			if(buttonUpgFireRate.isEnabled() && Game.getMoneyAmount() < ally.getUpgradeInfo().getCurrentFireRateCost()) buttonUpgFireRate.setEnabled(false);
			else if(!buttonUpgFireRate.isEnabled() && Game.getMoneyAmount() >= ally.getUpgradeInfo().getCurrentFireRateCost()) buttonUpgFireRate.setEnabled(true);
		}
		
		if(!ally.getUpgradeInfo().isRangeMaxed()) {
			if(buttonUpgRange.isEnabled() && Game.getMoneyAmount() < ally.getUpgradeInfo().getCurrentRangeCost()) buttonUpgRange.setEnabled(false);
			else if(!buttonUpgRange.isEnabled() && Game.getMoneyAmount() >= ally.getUpgradeInfo().getCurrentRangeCost()) buttonUpgRange.setEnabled(true);
		}
		
		if(ally.getAOEDamage() > 0 && !ally.getUpgradeInfo().isAOERadiusMaxed()) {
			if(buttonUpgAOEsize.isEnabled() && Game.getMoneyAmount() < ally.getUpgradeInfo().getCurrentAOERadiusCost()) buttonUpgAOEsize.setEnabled(false);
			else if(!buttonUpgAOEsize.isEnabled() && Game.getMoneyAmount() >= ally.getUpgradeInfo().getCurrentAOERadiusCost()) buttonUpgAOEsize.setEnabled(true);
		}
	}
}