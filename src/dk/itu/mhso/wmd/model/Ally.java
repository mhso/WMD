package dk.itu.mhso.wmd.model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Ally extends Unit {
	public void loadIcon(String unitName) {
		try {
			icon = ImageIO.read(new File("resources/sprites/ally/"+unitName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
