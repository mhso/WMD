package dk.itu.mhso.wmd.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dk.itu.mhso.wmd.controller.PathParser;

public class Level {
	public Level(String pathName) {
		parsePath(pathName);
	}
	
	private void parsePath(String pathName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(pathName + "/path.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PathParser parser = new PathParser(image);
	}
	
	private void parseWaves(String pathName) {
		
	}
}
