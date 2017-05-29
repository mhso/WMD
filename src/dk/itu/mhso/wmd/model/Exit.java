package dk.itu.mhso.wmd.model;

import java.awt.Point;

public class Exit extends Passage {
	public Exit(Point start, Point end) {
		super(start, end);
	}
	
	public boolean hasExited(Enemy enemy) {
		if(start.x - end.x == 0) {
			if(start.x < 10) return enemy.getLocation().x < start.x+5;
			else return enemy.getLocation().x > start.x-5;
		}
		else {
			if(start.y < 10) return enemy.getLocation().y < start.y+5;
			else return enemy.getLocation().y > start.y-5;
		}
	}
	
	public String toString() {
		return "Exit [" + super.toString() + "]";
	}
}
