package dk.itu.mhso.wmd;

import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.view.windows.WindowMainMenu;

public class Main {
	public static final String NAME = "Weapons of Mass Defense";
	public static final String VERSION = "0.5";
	
	public static final boolean SHOW_FPS = true;
	public static final boolean DEBUG = false;
	public static final boolean CHEAT_MODE = true;
	public static final boolean PRODUCTION = false;
	
	public static WindowMainMenu window;
	
	public static void main(String[] args) {
		Game.loadLevels();
		window = new WindowMainMenu();
	}
}
