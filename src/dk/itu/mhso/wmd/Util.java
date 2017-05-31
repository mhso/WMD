package dk.itu.mhso.wmd;

import java.awt.Point;

public class Util {
	public static double calculateAngle(Point p1, Point p2) {
		int xDelta = p2.x-p1.x;
		int yDelta = p2.y-p1.y;
		return Math.atan2(yDelta, xDelta) + Math.toRadians(180);
	}
}
