package dk.itu.mhso.wmd.controller;

import java.awt.Point;

import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Trap;

public class UnitCreator {
	public static boolean createUnit(Ally unit, Point location) {
		if(unit instanceof Trap) {
			if(!Game.isWithinMainPath(location)) return false;
		}
		else if(unit instanceof Ally) {
			if(Game.isWithinMainPath(location)) return false;
		}
		
		Game.addAlly(unit);
		Game.decrementMoney(unit.getCost());
		unit.setLocation(location);
		unit.createUpgradeWindow();
		return true;
	}
}
