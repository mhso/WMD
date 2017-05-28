package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class UnitPath {
	private List<Point> points = new ArrayList<>();
	private int currentPoint;
	
	public void addPoint(Point point) { points.add(point); }

	public Point getPoint(int index) {
		if(index >= points.size()) return null;
		return points.get(index);
	}
	
	public Point getNextPoint() {
		if(currentPoint == points.size()) return null;
		Point point = points.get(currentPoint);
		currentPoint++;
		return point;
	}
}
