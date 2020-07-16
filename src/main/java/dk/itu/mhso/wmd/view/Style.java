package dk.itu.mhso.wmd.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import dk.itu.mhso.wmd.Main;

public class Style {
	public static final Color OVERLAY_MENU_MAIN = new Color(9, 40, 71);
	public static final Color TOWER_BUTTON_BG = new Color(22, 57, 92);
	public static final Color UPGRADE_MENU_HEADER = new Color(153, 204, 255);
	
	public static final Image PENCIL_ICON = getImage("icons/pencil.png");
	public static final Image LINE_ICON = getImage("icons/line.png");
	public static final Image DRAW_CROSSHAIR = getImage("icons/draw_cursor.png");
	public static final Image UNDO_ICON  = getImage("icons/undo.png");
	public static final Image REDO_ICON  = getImage("icons/redo.png");
	public static final Image INVIS_ICON  = getImage("icons/invis.png");
	
	private static Image getImage(String fileName) {
		try {
			if(Main.PRODUCTION) return ImageIO.read(ClassLoader.getSystemResource(fileName));
			else return ImageIO.read(new File("/resouces/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
