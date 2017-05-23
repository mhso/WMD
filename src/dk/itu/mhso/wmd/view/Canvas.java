package dk.itu.mhso.wmd.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

import dk.itu.mhso.wmd.model.Level;

public class Canvas extends JPanel {
	private Level level;
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
