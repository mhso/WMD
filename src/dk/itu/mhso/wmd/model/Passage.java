package dk.itu.mhso.wmd.model;

import java.awt.Point;

public class Passage {
	protected Point start;
	protected Point end;
	
	public Passage(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	public Point getStartPoint() {
		return start;
	}
	
	public Point getEndPoint() {
		return end;
	}
	
	public Point getBetween() {
		int startX = (int)Math.min(start.getX(), end.getX());
		int startY = (int)Math.min(start.getY(), end.getY());
		int endX = (int)Math.max(start.getX(), end.getX());
		int endY = (int)Math.max(start.getY(), end.getY());
		
		return new Point(startX + ((endX-startX)/2), startY + ((endY-startY)/2));
	}
	
	public String toString() {
		return "startPoint="+start+", endPoint="+end;
	}
}
