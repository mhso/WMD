package dk.itu.mhso.wmd;

import dk.itu.mhso.wmd.controller.Game;

import dk.itu.mhso.wmd.view.WindowMainMenu;

public class Main {
	public static final boolean DEBUG = false;
	public static WindowMainMenu window;
	
	public static void main(String[] args) {
		Game.loadLevels();
		window = new WindowMainMenu();
	}
}
