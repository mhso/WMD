package dk.itu.mhso.wmd.view.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import dk.itu.mhso.wmd.Main;
import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Enemy;
import dk.itu.mhso.wmd.model.Explosion;
import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.model.Projectile;
import dk.itu.mhso.wmd.model.Unit;

public class Canvas extends JPanel {
	private Level level;	
	private Ally highlightedUnit;
	private Ellipse2D rangeCircle;
	private int fps;
	private FPSMonitor fpsMonitor;
	private int fpsCounter;
	
	public Canvas() {
		setDoubleBuffered(true);
		fpsMonitor = new FPSMonitor();
		if(Main.SHOW_FPS) fpsMonitor.start();
	}
	
	public void toggleFPS() {
		if(!fpsMonitor.isRunning()) fpsMonitor.start();
		else fpsMonitor.stop();
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void checkHighlight(Point pos) {
		highlightedUnit = null;
		for(Ally ally : Game.getAllies()) {
			if(ally.contains(pos)) {
				highlightedUnit = ally;
			}
		}
	}
	
	public Ally getHighlighedUnit() {
		return highlightedUnit;
	}
	
	public boolean isDrawingUnitRange() {
		return rangeCircle != null;
	}
	
	public void drawExplosion(Point point, BufferedImage[] images) {
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.drawImage(WMDConstants.EXPLOSION_IMAGE, point.x, point.y, null);
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		drawLevel(g2d);
		drawAllies(g2d);
		drawEnemies(g2d);
		if(!Game.getExplosions().isEmpty()) drawExplosions(g2d);
		if(Main.SHOW_FPS) drawFPS(g2d);
	}
	
	private void drawFPS(Graphics2D g2d) {
		fpsCounter++;
		g2d.setColor(Color.WHITE);
		g2d.drawString("FPS: "+fps, 15, 55);
		g2d.setColor(Color.BLACK);
	}

	public void setUnitRangeCircle(Ellipse2D rangeCircle) {
		this.rangeCircle = rangeCircle;
	}
	
	private void drawLevel(Graphics2D g2d) {
		g2d.drawImage(level.getBGImage(), 0, 0, null);
		if(Main.DEBUG) {
			for(Path2D path : level.getMainPathAreas()) g2d.draw(path);
		}
	}
	
	public void eraseAllies() {
		
	}
	
	private void drawAllies(Graphics2D g2d) {
		for(Ally ally : Game.getAllies()) {
			if(highlightedUnit != ally) transformAndDrawImage(ally, ally.getIcon(),ally.getLocation(), g2d);
			else transformAndDrawImage(ally, ally.getHighlightedIcon(), ally.getLocation(), g2d);
			drawProjectiles(ally, g2d);
		}
		if(isDrawingUnitRange()) {
			g2d.setColor(new Color(230, 230, 230, 150));
			g2d.fill(rangeCircle);
			g2d.setColor(Color.BLACK);
		}
	}
	
	private void drawProjectiles(Ally ally, Graphics2D g2d) {
		if(ally.getCurrentProjectiles().isEmpty()) return;
		for(Projectile projectile : ally.getCurrentProjectiles()) {
			transformAndDrawImage(projectile, projectile.getIcon(), projectile.getLocation(), g2d);
			if(Main.DEBUG) g2d.drawLine(projectile.getLocation().x, projectile.getLocation().y,
					projectile.getTarget().getLocation().x, projectile.getTarget().getLocation().y);
		}
	}
	
	private void drawExplosions(Graphics2D g2d) {
		Iterator<Explosion> it = Game.getExplosions().iterator();
		while(it.hasNext()) {
			Explosion exp = it.next();
			Image image = exp.getNextImage();
			if(image == null) it.remove();
			else g2d.drawImage(image, exp.getLocation().x, exp.getLocation().y, null);
		}
	}
	
	private void drawEnemies(Graphics2D g2d) {
		if(Game.getCurrentEnemies().isEmpty()) return;
		for(Enemy enemy : Game.getCurrentEnemies()) {
			if(!enemy.isVisible()) continue;
			transformAndDrawImage(enemy, enemy.getIcon(), new Point(enemy.getLocation().x - enemy.getWidth()/2, 
					enemy.getLocation().y - enemy.getHeight()/2), g2d);
			
			drawHealth(g2d, enemy);
			
			if(Main.DEBUG) {
				String angleString = ""+Math.toDegrees(enemy.getAngle());
				if(angleString.length() > 4) angleString = angleString.substring(0, 5);
				g2d.drawString(angleString, enemy.getLocation().x+10, enemy.getLocation().y-10);
			}
		}
	}
	
	private void transformAndDrawImage(Unit unit, BufferedImage image, Point point, Graphics2D g2d) {
		g2d.drawImage(unit.transformIcon(image), point.x, point.y, null);
	}

	private void drawHealth(Graphics2D g2d, Enemy enemy) {
		g2d.draw(new Rectangle2D.Double(enemy.getLocation().getX()-(enemy.getWidth()/2), enemy.getLocation().getY()-12-(enemy.getHeight()/2), 
				enemy.getWidth(), 7));
		g2d.setColor(Color.RED);
		g2d.fill(new Rectangle2D.Double(enemy.getLocation().getX()+1-(enemy.getWidth()/2), enemy.getLocation().getY()-11-(enemy.getHeight()/2), 
				(enemy.getWidth()-1)*((double)enemy.getCurrentHealth()/(double)enemy.getMaxHealth()), 6));
		g2d.setColor(Color.BLACK);
	}
	
	private class FPSMonitor implements ActionListener {
		private final int averageSampleAmount = 20;
		
		private Timer timer;
		private List<Integer> fpsValues;
		
		public FPSMonitor() {
			fpsValues = new ArrayList<>();
			timer = new Timer(1000, this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			fpsValues.add(fpsCounter);
			if(fpsValues.size() > averageSampleAmount) fpsValues.remove(0);
			
			int sum = 0;
			for(int l : fpsValues) {
				sum += l;
			}
			fps = sum/fpsValues.size();
			fpsCounter = 0;
		}
		
		public void start() {
			timer.start();
		}
		
		public void stop() {
			timer.stop();
		}
		
		public boolean isRunning() {
			return timer.isRunning();
		}
	}
}
