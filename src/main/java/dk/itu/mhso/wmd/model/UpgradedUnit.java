package dk.itu.mhso.wmd.model;

public class UpgradedUnit extends Ally {
	private Ally baseAlly;
	
	public UpgradedUnit(Ally baseAlly, String name) {
		super(name);
		this.baseAlly = baseAlly;
	}
}
