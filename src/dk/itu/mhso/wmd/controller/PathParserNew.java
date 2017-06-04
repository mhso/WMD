package dk.itu.mhso.wmd.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import dk.itu.mhso.wmd.model.UnitPath;

public class PathParserNew {
	private static final int MOVE_RGB = Color.BLACK.getRGB();
	private static BufferedImage pathImage;
	
	public static List<Point> parsePath(BufferedImage image, Point start, Point end) {
		pathImage = image;
		List<Point> traversedPoints = new ArrayList<>();
		traversedPoints.add(start);
		Point nextPoint = start;
		boolean finished = false;
		while(!finished) {
			nextPoint = getNextPathPoint(nextPoint, traversedPoints, MOVE_RGB);
			traversedPoints.add(nextPoint);
			
			if(nextPoint.equals(end)) {
				finished = true;
			}
		}
		return traversedPoints;
	}
	
	private static Point getNextPathPoint(Point currentPoint, List<Point> traversedPoints, int SEARCH_RGB) {
		return getNextPathPoint(1, currentPoint, traversedPoints, SEARCH_RGB);
	}
	
	private static Point getNextPathPoint(int radius, Point currentPoint, List<Point> traversedPoints, int SEARCH_RGB) {
		Point result = null;
		for(int i = currentPoint.x-radius; i <= currentPoint.x+radius; i++) {
			for(int j = currentPoint.y-radius; j <= currentPoint.y+radius; j++) {
				if(!traversedPoints.contains(new Point(i, j)) && isWithinBounds(i, j) && 
						pathImage.getRGB(i, j) == SEARCH_RGB) result = new Point(i, j);
			}
		}
		if(result == null) return getNextPathPoint(radius+1, currentPoint, traversedPoints, SEARCH_RGB);
		return result;
	}
	
	private static boolean isWithinBounds(int x, int y) {
		return x >= 0 && x < pathImage.getWidth() && y >= 0 && y < pathImage.getHeight();
	}
}
