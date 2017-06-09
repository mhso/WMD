package dk.itu.mhso.wmd.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.model.UnitPoint;

public class PathParser {
	private static final int MOVE_RGB = Color.BLACK.getRGB();
	private static final int INVIS_RGB = Color.GRAY.getRGB();
	private static BufferedImage pathImage;
	private static int addMod;
	
	public static List<UnitPoint> parsePath(BufferedImage image, Point start, Point end) {
		pathImage = image;
		List<UnitPoint> traversedPoints = new ArrayList<>();
		UnitPoint startUP = new UnitPoint(start);
		traversedPoints.add(startUP);
		UnitPoint nextPoint = startUP;
		boolean finished = false;
		if(end.equals(nextPoint)) finished = true;
		while(!finished) {
			addMod++;
			Point current = nextPoint;
			nextPoint = getNextPathPoint(nextPoint, traversedPoints);
			if(isDiagonalMod(current, nextPoint)) traversedPoints.add(nextPoint);
			
			if(nextPoint.equals(end)) {
				finished = true;
			}
		}
		return traversedPoints;
	}
	
	private static UnitPoint getNextPathPoint(UnitPoint currentPoint, List<UnitPoint> traversedPoints) {
		return getNextPathPoint(1, currentPoint, traversedPoints);
	}
	
	private static UnitPoint getNextPathPoint(int radius, UnitPoint currentPoint, List<UnitPoint> traversedPoints) {
		UnitPoint result = null;
		for(int i = currentPoint.x-radius; i <= currentPoint.x+radius; i++) {
			for(int j = currentPoint.y-radius; j <= currentPoint.y+radius; j++) {
				if(!traversedPoints.contains(new Point(i, j)) && isWithinBounds(i, j)) {
					if(pathImage.getRGB(i, j) == MOVE_RGB) result = new UnitPoint(i, j, true);
					else if(pathImage.getRGB(i, j) == INVIS_RGB) result = new UnitPoint(i, j, false);
				}
			}
		}
		if(result == null) return getNextPathPoint(radius+1, currentPoint, traversedPoints);
		return result;
	}
	
	private static boolean isDiagonalMod(Point currentPoint, Point newPoint) {
		if(isDiagonal(currentPoint, newPoint)) {
			return addMod % WMDConstants.PATH_POINT_SKIP_AMOUNT == 0;
		}
		return true;
	}
	
	private static boolean isDiagonal(Point currentPoint, Point newPoint) {
		return newPoint.x != currentPoint.x && newPoint.y != currentPoint.y;
	}
	
	private static boolean isWithinBounds(int x, int y) {
		return x >= 0 && x < pathImage.getWidth() && y >= 0 && y < pathImage.getHeight();
	}
}
