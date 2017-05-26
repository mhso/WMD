package dk.itu.mhso.wmd.model;

public abstract class Enemy extends Unit {
	protected UnitPath path;
	
	public void setUnitPath(UnitPath path) {
		this.path = path;
	}
}
