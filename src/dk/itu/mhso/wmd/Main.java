package dk.itu.mhso.wmd;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dk.itu.mhso.wmd.controller.PathParser;
import dk.itu.mhso.wmd.view.Canvas;
import dk.itu.mhso.wmd.view.WindowGame;

public class Main {
	public static void main(String[] args) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PathParser parser = new PathParser(image);
		
		Canvas canvas = new Canvas();
		canvas.setPath(parser.getMainPath());
		
		WindowGame window = new WindowGame();
		window.getContentPane().add(canvas);
	}
}
