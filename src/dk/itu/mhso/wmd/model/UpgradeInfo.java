package dk.itu.mhso.wmd.model;

import java.io.Serializable;

import dk.itu.mhso.wmd.Util;
import dk.itu.mhso.wmd.WMDConstants;

public class UpgradeInfo implements Serializable {
	private static final long serialVersionUID = -6990744034649925329L;
	
	private Ally ally;
	private int[] rangeIncrements;
	private int[] rangeCosts;
	private int[] damageIncrements;
	private int[] damageCosts;
	private int[] fireRateIncrements;
	private int[] fireRateCosts;
	private int[] aoeRadiusIncrements;
	private int[] aoeRadiusCosts;
	
	private short currentRangeIndex;
	private short currentDamageIndex;
	private short currentFireRateIndex;
	private short currentAOERadiusIndex;
	
	public UpgradeInfo(int[] rangeIncrements, int[] rangeCosts, int[] damageIncrements, int[] damageCosts, int[] fireRateIncrements, int[] fireRateCosts) {
		this.rangeIncrements = rangeIncrements;
		this.rangeCosts = rangeCosts;
		this.damageIncrements = damageIncrements;
		this.damageCosts = damageCosts;
		this.fireRateIncrements = fireRateIncrements;
		this.fireRateCosts = fireRateCosts;
	}
	
	public void setAlly(Ally ally) {
		this.ally = ally;
	}
	
	public void upgradeRange() {
		ally.incrementWorth(rangeCosts[currentRangeIndex]);
		ally.setRange(ally.getRange() + rangeIncrements[currentRangeIndex++]);
	}
	
	public int getCurrentRangeCost() {
		return rangeCosts[currentRangeIndex];
	}
	
	public void upgradeDamage() {
		ally.incrementWorth(damageCosts[currentDamageIndex]);
		if(ally.getAOEDamage() > 0) ally.setAOEDamage(ally.getAOEDamage() + 
				(int)Math.floor((double)damageIncrements[currentDamageIndex]*WMDConstants.AOE_DAMAGE_RATIO));
		ally.setDamage(ally.getDamage() + damageIncrements[currentDamageIndex++]);
	}
	
	public int getCurrentDamageCost() {
		return damageCosts[currentDamageIndex];
	}
	
	public void upgradeFireRate() {
		ally.incrementWorth(fireRateCosts[currentFireRateIndex]);
		ally.setFireRate(ally.getFireRate() + fireRateIncrements[currentFireRateIndex++]);
	}
	
	public int getCurrentFireRateCost() {
		return fireRateCosts[currentFireRateIndex];
	}
	
	public void setAOERadiusIncrements(int[] aoeRadiusIncrements) {
		this.aoeRadiusIncrements = aoeRadiusIncrements;
	}
	
	public void setAOERadiusCosts(int[] aoeRadiusCosts) {
		this.aoeRadiusCosts = aoeRadiusCosts;
	}
	
	public void upgradeAOERadius() {
		ally.incrementWorth(aoeRadiusCosts[currentAOERadiusIndex]);
		ally.setAOERadius(ally.getAOERadius() + aoeRadiusIncrements[currentAOERadiusIndex++]);
	}
	
	public int getCurrentAOERadiusCost() {
		return aoeRadiusCosts[currentAOERadiusIndex];
	}
	
	public boolean isDamageMaxed() {
		return damageIncrements.length == currentDamageIndex;
	}
	
	public boolean isRangeMaxed() {
		return rangeIncrements.length == currentRangeIndex;
	}
	
	public boolean isAOERadiusMaxed() {
		return aoeRadiusIncrements.length == currentAOERadiusIndex;
	}
	
	public boolean isFireRateMaxed() {
		return fireRateIncrements.length == currentFireRateIndex;
	}
	
	public static void main(String[] args) {
		UpgradeInfo uiSniper = new UpgradeInfo(new int[]{100, 150, 250}, new int[]{50, 75, 150}, new int[]{1, 1, 2, 2}, new int[]{75, 125, 200, 300},
				new int[]{20, 20, 25, 25}, new int[]{50, 75, 150, 250});
		Util.writeObjectToFile(uiSniper, "resources/unitinfo/sniper_upginf.bin");
		
		UpgradeInfo uiMissile = new UpgradeInfo(new int[]{50, 75, 150}, new int[]{200, 300, 400}, new int[]{3, 3, 4, 4}, new int[]{250, 400, 600, 900},
				new int[]{15, 15, 20, 20}, new int[]{200, 300, 400, 550});
		uiMissile.setAOERadiusIncrements(new int[]{25, 50, 75, 100});
		uiMissile.setAOERadiusCosts(new int[]{250, 350, 550, 850});
		Util.writeObjectToFile(uiMissile, "resources/unitinfo/missile_battery_upginf.bin");
	}
}