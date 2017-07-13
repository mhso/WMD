package dk.itu.mhso.wmd.model;

import java.io.IOException;

public abstract class Trap extends Ally {

	public Trap(String name) {
		super(name);
	}

	public void loadIcons(String unitName) {
		try {
			loadMainIcon(unitName);
			loadHighlightedIcon(unitName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
