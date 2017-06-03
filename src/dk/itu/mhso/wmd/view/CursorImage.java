package dk.itu.mhso.wmd.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import dk.itu.mhso.wmd.WMDConstants;

public class CursorImage {
	public static Cursor getCursor(Image image, Point pos) {
		return Toolkit.getDefaultToolkit().createCustomCursor(image, pos, "stuff"); 
	}
	
	public static Cursor getDrawCursor(Color color) { 
		BufferedImage image = new BufferedImage(WMDConstants.LEVEL_EDITOR_CURSOR_SIZE, 
				WMDConstants.LEVEL_EDITOR_CURSOR_SIZE, BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				image.setRGB(i, j, new Color(0, 0, 0, 0).getRGB());
			}
		}
		int midX = 4;
		int midY = 4;
		
		image.setRGB(midX, midY, color.getRGB());
		
		image.setRGB(midX-3, midY, Color.BLACK.getRGB());
		image.setRGB(midX-2, midY, Color.BLACK.getRGB());
		
		image.setRGB(midX+2, midY, Color.BLACK.getRGB());
		image.setRGB(midX+3, midY, Color.BLACK.getRGB());
		
		image.setRGB(midX, midY-3, Color.BLACK.getRGB());
		image.setRGB(midX, midY-2, Color.BLACK.getRGB());
		
		image.setRGB(midX, midY+2, Color.BLACK.getRGB());
		image.setRGB(midX, midY+3, Color.BLACK.getRGB());
		
		return getCursor(image, new Point(image.getWidth(null)/2, image.getHeight(null)/2));
	}
	
	public static Cursor getDefault() {
		return Cursor.getDefaultCursor();
	}
}
