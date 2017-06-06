package dk.itu.mhso.wmd.model;

import java.awt.Point;

public class UnitPoint extends Point {
	public boolean visible;
	
	public UnitPoint(int x, int y, boolean visible) {
		super(x, y);
		this.visible = visible;
	}
	
	public UnitPoint(Point point) {
		this(point.x, point.y, true);
	}
}
