package dk.itu.mhso.wmd.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	private Path2D path;
	
	public void setPath(Path2D path) {
		this.path = path;
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(path);
	}
}
