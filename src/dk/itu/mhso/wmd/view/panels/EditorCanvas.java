package dk.itu.mhso.wmd.view.panels;

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
import dk.itu.mhso.wmd.controller.PathParser;
import dk.itu.mhso.wmd.model.UnitPath;
import dk.itu.mhso.wmd.view.CursorImage;
import dk.itu.mhso.wmd.view.windows.WindowLevelEditor;

public class EditorCanvas extends JPanel {
	private static final long serialVersionUID = -1238851349734038022L;
	public static final int PENCIL_TOOL = 0;
	public static final int LINE_TOOL = 1;
	
	private int currentTool = 1;
	private Color currentDrawColor = Color.BLACK;
	private transient WindowLevelEditor window;
	private transient BufferedImage bgImage;
	private Line2D line;
	private Path2D path;
	private Path2D completedParts;
	private List<Path2D> completedPaths = new ArrayList<>();
	private List<Path2D> completedAreas = new ArrayList<>();
	private Path2D currentArea;
	private UnitPath unitPath;
	
	public EditorCanvas(WindowLevelEditor window) {
		this.window = window;
		setBackground(Color.WHITE);
		setFocusable(true);
		setCursor(CursorImage.getDrawCursor(Color.BLACK));
		new LevelEditorMouseController();
	}
	
	public void setWindow(WindowLevelEditor window) {
		this.window = window;
	}
	
	public void setBGImage(BufferedImage bgImage) {
		this.bgImage = bgImage;
	}
	
	public int paths() {
		return completedPaths.size();
	}
	
	public void pathCompleted() {
		Path2D wall1 = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		Path2D wall2 = new Path2D.Double(Path2D.WIND_EVEN_ODD);
		PathIterator pit = completedParts.getPathIterator(new AffineTransform());
		
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
		
		completedPaths.add(completedParts);
		completedAreas.add(wall1);
		completedParts = null;
		unitPath = null;
		path = null;
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
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(currentDrawColor);
		if(bgImage != null) drawBGImage(g2d);
		if(!completedPaths.isEmpty()) {
			g2d.setColor(Color.RED);
			for(Path2D path : completedPaths) g2d.draw(path);
			g2d.setColor(currentDrawColor);
		}
		if(completedParts != null) {
			g2d.setColor(Color.GREEN);
			g2d.draw(completedParts);
			g2d.setColor(currentDrawColor);
		}
		if(line != null) g2d.draw(line);
		if(path != null) g2d.draw(path);
	}
	
	private void drawBGImage(Graphics2D g2d) {
		g2d.drawImage(bgImage, 0, 0, null);
	}
	
	public BufferedImage getBGImage() {
		return bgImage;
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
		return path;
	}
	
	public UnitPath getUnitPath() {
		return unitPath;
	}
	
	public Path2D getCurrentArea() {
		return currentArea;
	}
	
	public void toggleInvisTool() {
		if(currentDrawColor == Color.BLACK) currentDrawColor = Color.GRAY;
		else currentDrawColor = Color.BLACK;
	}
	
	public void setCurrentDrawColor(Color color) {
		currentDrawColor = color;
	}
	
	public void undo() {
		
	}
	
	public void redo() {
		
	}
	
	private void checkEndPoint(Path2D currentPath, Point point) {
		for(int y = 0; y <= getHeight(); y+= getHeight()) {
			for(int x = 0; x < getWidth(); x++) {
				if(currentPath.intersects(x, y, 1, 1)) {
					point.setLocation(x, y);
				}
			}
		}
		for(int x = 0; x <= getWidth(); x += getWidth()) {
			for(int y = 0; y < getHeight(); y++) {
				if(currentPath.intersects(x, y, 1, 1)) {
					point.setLocation(x, y);
				}
			}
		}
	}
	
	private class LevelEditorMouseController extends MouseAdapter {
		private Point currentPoint;
		private Point endPoint;
		
		public LevelEditorMouseController() {
			addMouseListener(this);
			addMouseMotionListener(this);
			addMouseWheelListener(this);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			currentPoint = e.getPoint();
			if(unitPath == null) unitPath = new UnitPath();
			
			if(completedParts != null && window.isSnappingToLine()) currentPoint = endPoint;
			if(completedParts == null) completedParts = new Path2D.Double(Path2D.WIND_EVEN_ODD);
			
			path = new Path2D.Double(Path2D.WIND_EVEN_ODD);
			
			path.moveTo(currentPoint.x, currentPoint.y);
			if(currentTool == EditorCanvas.PENCIL_TOOL) path.lineTo(e.getX(), e.getY());
			window.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getPoint().equals(currentPoint) && currentTool == LINE_TOOL) {
				currentPoint = null;
				completedParts = null;
				return;
			}
			if(line != null) {
				path.append(line, false);
			}
			window.pathStarted();
			endPoint = e.getPoint();
			checkEndPoint(path, endPoint);
			unitPath.addAllPoints(PathParser.parsePath(getMainImage(), currentPoint, endPoint));
			completedParts.append(path, false);
			path = null;
			line = null;
			
			window.repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(currentTool == EditorCanvas.PENCIL_TOOL) path.lineTo(e.getX(), e.getY());
			else if(currentTool == EditorCanvas.LINE_TOOL) {
				line = new Line2D.Double(currentPoint.x, currentPoint.y, e.getX(), e.getY());
			}
			window.repaint();
		}
	}
}
