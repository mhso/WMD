package dk.itu.mhso.wmd.view;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class CursorImage {
	public static Cursor getCursor(BufferedImage image, JComponent source) { 
		return Toolkit.getDefaultToolkit().createCustomCursor(image, source.getLocation(), "stuff"); 
		}
	
	public static Cursor getDefault() {
		return Cursor.getDefaultCursor();
	}
}
