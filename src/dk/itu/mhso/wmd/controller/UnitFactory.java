package dk.itu.mhso.wmd.controller;

import dk.itu.mhso.wmd.model.SoldierArmored;
import dk.itu.mhso.wmd.model.SoldierBasic;
import dk.itu.mhso.wmd.model.Unit;
import dk.itu.mhso.wmd.model.UnitType;

public class UnitFactory {
	public static Unit createUnit(String unitName) {
		if(UnitType.SOLDIER_BASIC.toString().equals(unitName)) return new SoldierBasic();
		else if(UnitType.SOLDIER_ARMORED.toString().equals(unitName)) return new SoldierArmored();
		return null;
	}
}
