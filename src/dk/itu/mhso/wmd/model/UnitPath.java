package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dk.itu.mhso.wmd.Util;

public class UnitPath implements Serializable {
	private static final long serialVersionUID = -8430756851443973969L;

	private final int ANGLE_AVERAGE_AMOUNT = 10;
	
	private List<Point> points = new ArrayList<>();
	
	public void addPoint(Point point) { points.add(point); }

	public int size() {
		return points.size();
	}
	
	public Point getPoint(int index) {
		if(index >= points.size()) return null;
		return points.get(index);
	}
	
	public double getCurrentAngle(int currentPoint) {
		int lastAverages = currentPoint -  ANGLE_AVERAGE_AMOUNT;
		if(lastAverages < 0) lastAverages = 0;
		double angleSum = 0;
		for(int i = lastAverages; i < currentPoint-1; i++) {
			angleSum += Util.calculateAngle(points.get(i+1), points.get(i));
		}
		return angleSum/(currentPoint-lastAverages);
	}
}
