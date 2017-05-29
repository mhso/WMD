package dk.itu.mhso.wmd.model;

import java.awt.image.BufferedImage;

public abstract class Unit {
	protected BufferedImage icon;
	
	public BufferedImage getIcon() {
		return icon;
	}
	
	public abstract void loadIcon(String unitName);
}
