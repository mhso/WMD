package dk.itu.mhso.wmd.model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Enemy extends Unit {
	protected UnitPath path;
	
	public void setUnitPath(UnitPath path) {
		this.path = path;
	}
	
	public void loadIcon(String unitName) {
		try {
			icon = ImageIO.read(new File("resources/sprites/enemy/"+unitName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
