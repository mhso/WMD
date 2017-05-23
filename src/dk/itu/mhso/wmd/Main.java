package dk.itu.mhso.wmd;

import dk.itu.mhso.wmd.controller.Game;

import dk.itu.mhso.wmd.view.WindowMainMenu;

public class Main {
	public static void main(String[] args) {
		Game.loadLevels();
		new WindowMainMenu();
	}
}
