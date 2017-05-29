package dk.itu.mhso.wmd.controller;

import dk.itu.mhso.wmd.model.Unit;
import dk.itu.mhso.wmd.model.UnitType;
import dk.itu.mhso.wmd.model.allyunits.Sniper;
import dk.itu.mhso.wmd.model.enemyunits.SoldierArmored;
import dk.itu.mhso.wmd.model.enemyunits.SoldierBasic;

public class UnitFactory {
	public static Unit createUnit(String unitName) {
		if(UnitType.SOLDIER_BASIC.toString().equals(unitName)) return initializeUnit(new SoldierBasic(), unitName);
		else if(UnitType.SOLDIER_ARMORED.toString().equals(unitName)) return initializeUnit(new SoldierArmored(), unitName);
		else if(UnitType.SNIPER.toString().equals(unitName)) return initializeUnit(new Sniper(), unitName);
		return null;
	}
	
	private static Unit initializeUnit(Unit unit, String unitName) {
		unit.loadIcons(unitName);
		return unit;
	}
}
