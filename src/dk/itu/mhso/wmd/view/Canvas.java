package dk.itu.mhso.wmd.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.util.List;

import javax.swing.JPanel;

import dk.itu.mhso.wmd.Main;
import dk.itu.mhso.wmd.model.Enemy;
import dk.itu.mhso.wmd.model.Level;

public class Canvas extends JPanel {
	private Level level;
	private List<Enemy> enemies;
	
	public Canvas() {
		setDoubleBuffered(true);
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void setCurrentEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	protected void paintComponent(Graphics g) {
		drawLevel((Graphics2D) g);
		drawAllies((Graphics2D) g);
		drawEnemies((Graphics2D) g);
	}
	
	private void drawLevel(Graphics2D g2d) {
		g2d.drawImage(level.getBGImage(), 0, 0, null);
	}
	
	private void drawAllies(Graphics2D g2d) {
		
	}
	
	private void drawEnemies(Graphics2D g2d) {
		if(enemies == null || enemies.isEmpty()) return;
		for(Enemy enemy : enemies) {
			double midX = enemy.getWidth()/2;
			double midY = enemy.getHeight()/2;
			AffineTransform transform = AffineTransform.getRotateInstance(enemy.getAngle(), midX, midY);
			AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			g2d.drawImage(op.filter(enemy.getIcon(), null), enemy.getPointOnPath().x-(enemy.getWidth()/2), 
					enemy.getPointOnPath().y-(enemy.getHeight()/2), null);
			
			drawHealth(g2d, enemy);
			
			if(Main.DEBUG) {
				String angleString = ""+Math.toDegrees(enemy.getAngle());
				if(angleString.length() > 4) angleString = angleString.substring(0, 5);
				g2d.drawString(angleString, enemy.getPointOnPath().x+10, enemy.getPointOnPath().y-10);
			}
		}
	}

	private void drawHealth(Graphics2D g2d, Enemy enemy) {
		g2d.draw(new Rectangle2D.Double(enemy.getPointOnPath().getX()-(enemy.getWidth()/2), enemy.getPointOnPath().getY()-12-(enemy.getHeight()/2), 
				enemy.getWidth(), 7));
		g2d.setColor(Color.RED);
		g2d.fill(new Rectangle2D.Double(enemy.getPointOnPath().getX()+1-(enemy.getWidth()/2), enemy.getPointOnPath().getY()-11-(enemy.getHeight()/2), 
				(enemy.getWidth()-1)*((double)enemy.getCurrentHealth()/(double)enemy.getMaxHealth()), 6));
		g2d.setColor(Color.BLACK);
	}
}
