package dk.itu.mhso.wmd.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import dk.itu.mhso.wmd.Main;
import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Enemy;
import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.model.Projectile;
import dk.itu.mhso.wmd.model.Unit;

public class Canvas extends JPanel {
	private Level level;	
	private Ally highlightedUnit;
	private boolean drawUnitRange;
	
	public Canvas() {
		setDoubleBuffered(true);
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void checkHighlight(Point pos) {
		highlightedUnit = null;
		for(Ally ally : Game.getAllies()) {
			if(ally.contains(pos)) {
				ally.setHighlighted(true);
				highlightedUnit = ally;
			}
			else if(ally.isHighlighted()) ally.setHighlighted(false);
		}
		if(highlightedUnit == null) drawUnitRange = false;
	}
	
	public Ally getHighlighedUnit() {
		return highlightedUnit;
	}
	
	public boolean isDrawingUnitRange() {
		return drawUnitRange;
	}
	
	public void drawExplosion(Point point) {
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.drawImage(WMDConstants.EXPLOSION_IMAGE, point.x, point.y, null);
	}
	
	protected void paintComponent(Graphics g) {
		drawLevel((Graphics2D) g);
		drawAllies((Graphics2D) g);
		drawProjectiles((Graphics2D) g);
		drawEnemies((Graphics2D) g);
	}
	
	public void drawUnitRange(boolean drawRange) {
		drawUnitRange = drawRange;
	}
	
	private void drawLevel(Graphics2D g2d) {
		g2d.drawImage(level.getBGImage(), 0, 0, null);
		if(Main.DEBUG) g2d.draw(level.getMainPathArea());
	}
	
	private void drawAllies(Graphics2D g2d) {
		for(Ally ally : Game.getAllies()) {
			if(!ally.isHighlighted()) transformAndDrawImage(ally, ally.getIcon(), 0, 0, g2d);
			else transformAndDrawImage(ally, ally.getHighlightedIcon(), 0, 0, g2d);
		}
		if(drawUnitRange) {
			g2d.setColor(new Color(230, 230, 230, 150));
			g2d.fill(highlightedUnit.getRangeCircle());
			g2d.setColor(Color.BLACK);
		}
	}
	
	private void drawProjectiles(Graphics2D g2d) {
		if(Game.getCurrentProjectiles() == null || Game.getCurrentProjectiles().isEmpty()) return;
		for(Projectile projectile : Game.getCurrentProjectiles()) {
			transformAndDrawImage(projectile, projectile.getIcon(), 0, 0, g2d);
			if(Main.DEBUG) g2d.drawLine(projectile.getLocation().x, projectile.getLocation().y,
					projectile.getTarget().getLocation().x, projectile.getTarget().getLocation().y);
		}
	}
	
	private void drawEnemies(Graphics2D g2d) {
		if(Game.getCurrentEnemies() == null || Game.getCurrentEnemies().isEmpty()) return;
		for(Enemy enemy : Game.getCurrentEnemies()) {
			transformAndDrawImage(enemy, enemy.getIcon(), -(enemy.getWidth()/2), -(enemy.getHeight()/2), g2d);
			
			drawHealth(g2d, enemy);
			
			if(Main.DEBUG) {
				String angleString = ""+Math.toDegrees(enemy.getAngle());
				if(angleString.length() > 4) angleString = angleString.substring(0, 5);
				g2d.drawString(angleString, enemy.getLocation().x+10, enemy.getLocation().y-10);
			}
		}
	}
	
	private void transformAndDrawImage(Unit unit, BufferedImage image, int offsetX, int offsetY, Graphics2D g2d) {
		double midX = unit.getWidth()/2;
		double midY = unit.getHeight()/2;
		AffineTransform transform = AffineTransform.getRotateInstance(unit.getAngle(), midX, midY);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		g2d.drawImage(op.filter(image, null), unit.getLocation().x + offsetX, 
				unit.getLocation().y+offsetY, null);
	}

	private void drawHealth(Graphics2D g2d, Enemy enemy) {
		g2d.draw(new Rectangle2D.Double(enemy.getLocation().getX()-(enemy.getWidth()/2), enemy.getLocation().getY()-12-(enemy.getHeight()/2), 
				enemy.getWidth(), 7));
		g2d.setColor(Color.RED);
		g2d.fill(new Rectangle2D.Double(enemy.getLocation().getX()+1-(enemy.getWidth()/2), enemy.getLocation().getY()-11-(enemy.getHeight()/2), 
				(enemy.getWidth()-1)*((double)enemy.getCurrentHealth()/(double)enemy.getMaxHealth()), 6));
		g2d.setColor(Color.BLACK);
	}
}
