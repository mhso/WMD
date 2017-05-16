package dk.itu.mhso.wmd.model;

import java.awt.Point;

public class Entrance extends Passage {
	public Entrance(Point start, Point end) {
		super(start, end);
	}
	
	public String toString() {
		return "Entrance [" + super.toString() + "]";
	}
}
