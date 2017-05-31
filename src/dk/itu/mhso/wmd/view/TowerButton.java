package dk.itu.mhso.wmd.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Ally;

public class TowerButton extends JButton implements ChangeListener {
	private Ally ally;
	
	public TowerButton(Ally ally) {
		super(new ImageIcon(ally.getIcon()));
		setBackground(Style.TOWER_BUTTON_BG);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		setFocusPainted(false);
		this.ally = ally;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		String drawString = ""+ally.getCost()+"$";
		g.drawString(drawString, getWidth()-drawString.length()*8, getHeight()-5);
		g.setColor(Color.BLACK);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(isEnabled() && Game.getMoneyAmount() < ally.getCost()) setEnabled(false);
		else if(!isEnabled() && Game.getMoneyAmount() >= ally.getCost()) setEnabled(true);
	}
}
