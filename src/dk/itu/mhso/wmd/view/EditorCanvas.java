package dk.itu.mhso.wmd.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dk.itu.mhso.wmd.Main;
import dk.itu.mhso.wmd.WMDConstants;

public class EditorCanvas extends JPanel {
	public static final int PENCIL_TOOL = 0;
	public static final int LINE_TOOL = 1;
	
	private int currentTool = 1;
	private Color currentDrawColor = Color.BLACK;
	private WindowLevelEditor window;
	private BufferedImage bgImage;
	private Line2D line;
	private Path2D unitPath;
	private List<Path2D> completedPaths = new ArrayList<>();
	private List<Path2D> completedAreas = new ArrayList<>();
	private Path2D currentArea;
	private Point firstPoint;
	
	public EditorCanvas(WindowLevelEditor window) {
		this.window = window;
		setFocusable(true);
		setCursor(CursorImage.getDrawCursor(Color.BLACK));
		new LevelEditorMouseController();
	}
	
	public void setBGImage(BufferedImage bgImage) {
		this.bgImage = bgImage;
	}
	
	public void pathCompleted() {
		Path2D wall1 = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		Path2D wall2 = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		PathIterator pit = unitPath.getPathIterator(new AffineTransform());
		
		double[] points = new double[6];
		pit.currentSegment(points);
		
		Point[] pArr = getPerpendicularPoints(points);
		Point p1 = pArr[0];
		Point p2 = pArr[1];
		
		wall1.moveTo(p1.getX(), p1.getY());
		wall2.moveTo(p2.getX(), p2.getY());
		
		wall2.lineTo(p1.getX(), p1.getY());
		wall2.moveTo(p2.getX(), p2.getY());
		
		pit.next();
		
		while(!pit.isDone()) {
			points = new double[6];
			pit.currentSegment(points);
			
			pArr = getPerpendicularPoints(points);
			p1 = pArr[0];
			p2 = pArr[1];
			
			wall1.lineTo(p1.getX(), p1.getY());
			wall2.lineTo(p2.getX(), p2.getY());
			
			pit.next();
		}
		
		wall1.lineTo(p2.getX(), p2.getY());
		wall1.append(wall2, true);
		
		currentArea = wall1;
		
		completedPaths.add(unitPath);
		completedAreas.add(wall1);
		unitPath = null;
		line = null;
	}
	
	private Point[] getPerpendicularPoints(double[] points) {
		double x1 = points[0];
		double x2 = points[4];
		double y1 = points[1];
		double y2 = points[5];
		double dx = x1-x2;
		double dy = y1-y2;
		double dist = Math.sqrt(dx*dx + dy*dy);
		dx /= dist;
		dy /= dist;
		int x3 = (int)(x1 + (WMDConstants.ENEMY_AREA_WIDTH/2)*dy);
		int y3 = (int)(y1 - (WMDConstants.ENEMY_AREA_WIDTH/2)*dx);
		int x4 = (int)(x1 - (WMDConstants.ENEMY_AREA_WIDTH/2)*dy);
		int y4 = (int)(y1 + (WMDConstants.ENEMY_AREA_WIDTH/2)*dx);
		return new Point[]{new Point(x3, y3), new Point(x4, y4)};
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		window.repaint();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		if(bgImage != null) drawBGImage(g2d);
		if(line != null) g2d.draw(line);
		if(unitPath != null) g2d.draw(unitPath);
		if(!completedPaths.isEmpty()) {
			g2d.setColor(Color.RED);
			for(Path2D path : completedPaths) g2d.draw(path);
			g2d.setColor(Color.BLACK);
		}
	}
	
	private void drawBGImage(Graphics2D g2d) {
		g2d.drawImage(bgImage, 0, 0, null);
	}
	
	public BufferedImage getBGImage() {
		return bgImage;
	}
	
	public Point getFirstPoint() {
		return firstPoint;
	}
	
	public BufferedImage getMainImage() {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		BufferedImage bgBackup = bgImage;
		bgImage = null;
		paintComponent(image.createGraphics());
		bgImage = bgBackup;
		if(Main.DEBUG) {
			try {
				ImageIO.write(image, "png", new File("resources/test.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}
	
	public void setCurrentTool(int tool) {
		currentTool = tool;
		setCursor(CursorImage.getDrawCursor(currentDrawColor));
	}

	public Path2D getCurrentPath() {
		return unitPath;
	}
	
	public Path2D getCurrentArea() {
		return currentArea;
	}
	
	public void setCurrentDrawColor(Color color) {
		currentDrawColor = color;
	}
	
	private class LevelEditorMouseController extends MouseAdapter {
		private Point currentPoint;
		
		public LevelEditorMouseController() {
			addMouseListener(this);
			addMouseMotionListener(this);
			addMouseWheelListener(this);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			currentPoint = e.getPoint();
			if(firstPoint == null) firstPoint = currentPoint;
			if(unitPath != null) currentPoint = new Point((int)unitPath.getCurrentPoint().getX(), (int)unitPath.getCurrentPoint().getY());
			
			if(unitPath == null) {
				unitPath = new Path2D.Double(Path2D.WIND_EVEN_ODD);
				window.pathStarted();
			}
			unitPath.moveTo(currentPoint.x, currentPoint.y);
			if(currentTool == EditorCanvas.PENCIL_TOOL) unitPath.lineTo(e.getX(), e.getY());
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(line != null) unitPath.append(line, false);
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(currentTool == EditorCanvas.PENCIL_TOOL) {
				unitPath.lineTo(e.getX(), e.getY());
			}
			else if(currentTool == EditorCanvas.LINE_TOOL) {
				line = new Line2D.Double(currentPoint.x, currentPoint.y, e.getX(), e.getY());
			}
			repaint();
		}
	}
}