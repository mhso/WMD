package dk.itu.mhso.wmd.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dk.itu.mhso.wmd.controller.PathParser;

public class Level {
	public Level(String imageFileName) {
		parsePath(imageFileName);
	}
	
	private void parsePath(String imageFileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imageFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PathParser parser = new PathParser(image);
	}
}
