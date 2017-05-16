package dk.itu.mhso.wmd.model;

import java.awt.Point;

public class Exit extends Passage {
	public Exit(Point start, Point end) {
		super(start, end);
	}
	
	public String toString() {
		return "Exit [" + super.toString() + "]";
	}
}
