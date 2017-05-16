package dk.itu.mhso.wmd.model;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Soldier extends Enemy {

	@Override
	Shape getTestShape() {
		return new Ellipse2D.Double(20, 20, 20, 20);
	}
}
