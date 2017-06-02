package dk.itu.mhso.wmd.controller;

import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Unit;
import dk.itu.mhso.wmd.model.UnitType;
import dk.itu.mhso.wmd.model.allyunits.MissileBattery;
import dk.itu.mhso.wmd.model.allyunits.Sniper;
import dk.itu.mhso.wmd.model.enemyunits.SoldierArmored;
import dk.itu.mhso.wmd.model.enemyunits.SoldierBasic;
import dk.itu.mhso.wmd.model.enemyunits.TankLight;

public class UnitFactory {
	public static Unit createUnit(String unitName) {
		if(UnitType.SOLDIER_BASIC.toString().equalsIgnoreCase(unitName)) return initializeUnit(new SoldierBasic("Basic Soldier"), unitName);
		else if(UnitType.SOLDIER_ARMORED.toString().equalsIgnoreCase(unitName)) return initializeUnit(new SoldierArmored("Armored Soldier"), unitName);
		else if(UnitType.TANK_LIGHT.toString().equalsIgnoreCase(unitName)) return initializeUnit(new TankLight("Light Tank"), unitName);
		else if(UnitType.SNIPER.toString().equalsIgnoreCase(unitName)) return initializeUnit(new Sniper("Sniper"), unitName);
		else if(UnitType.MISSILE_BATTERY.toString().equalsIgnoreCase(unitName)) return initializeUnit(new MissileBattery("Missile Battery"), unitName);
		return null;
	}
	
	private static Unit initializeUnit(Unit unit, String unitName) {
		unit.loadIcons(unitName);
		if(unit instanceof Ally) {
			unit = (Ally) unit;
			((Ally) unit).loadUpgradeInfo(unitName.toLowerCase());
		}
		return unit;
	}
}
