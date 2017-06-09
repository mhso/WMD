package dk.itu.mhso.wmd.model;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.sun.crypto.provider.AESParameters;

import dk.itu.mhso.wmd.Resources;
import dk.itu.mhso.wmd.Util;
import dk.itu.mhso.wmd.controller.UnitFactory;

public class Level {
	private List<Wave> waves = new ArrayList<>();
	private int currentWave;
	private BufferedImage bgImage;
	private String name;
	private Path2D[] pathAreas;
	private LevelInfo info;
	
	public Level(String pathName) {
		name = new File(pathName).getName();
		loadLevelInfo(pathName);
		parseUnitData(pathName);
		loadBGImage(pathName);
	}

	private void parseUnitData(String pathName) {
		List<UnitPath> paths = (List<UnitPath>) Util.readObjectFromFile(pathName + "/paths.bin");
		List<Path2D> areas = (List<Path2D>) Util.readObjectFromFile(pathName + "/areas.bin");
		pathAreas = areas.toArray(new Path2D[areas.size()]);
		parseWaves(pathName, paths.toArray(new UnitPath[paths.size()]));
	}
	
	private void parseWaves(String pathName, UnitPath[] unitPaths) {
		List<Enemy> currentWaveEnemies = new ArrayList<>();
		List<UnitPath> copyList = new ArrayList<>(unitPaths.length);
		for(int u = 0; u < unitPaths.length; u++) copyList.add(unitPaths[u]);
		String[] arr = Resources.getEnemies(pathName);
		for(String s : arr) {
			if(s.equals("-")) {
				waves.add(new Wave(currentWaveEnemies));
				currentWaveEnemies = new ArrayList<>();
			}
			else if(s.startsWith("//")) {}
			else {
				Enemy enemy = (Enemy)UnitFactory.createUnit(s);
				if(info.getEnemiesPerSpawn() == 1) enemy.setUnitPaths(unitPaths);
				else {
					if(copyList.size() == 0) for(int u = 0; u < unitPaths.length; u++) copyList.add(unitPaths[u]);
					enemy.setUnitPath(copyList.remove(new Random().nextInt(copyList.size())));
				}
				currentWaveEnemies.add(enemy);
			}
		}
	}
	
	private void loadBGImage(String pathName) {
		try {
			bgImage = ImageIO.read(new File(pathName+"/bg.png"));
			for(int x = 0; x < bgImage.getWidth(); x++) 
				for(int y = 0; y < bgImage.getHeight(); y++) bgImage.setRGB(x, y, new Color(bgImage.getRGB(x, y)).darker().getRGB());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadLevelInfo(String pathName) {
		info = (LevelInfo) Util.readObjectFromFile(pathName + "/config.bin");
	}
	
	public String getName() {
		return name;
	}
	
	public LevelInfo getLevelInfo() {
		return info;
	}
	
	public BufferedImage getBGImage() {
		return bgImage;
	}
	
	public Path2D[] getMainPathAreas() {
		return pathAreas;
	}
	
	public int getCurrentWaveNr() {
		return currentWave;
	}
	
	public Wave getNextWave() {
		if(currentWave == waves.size()) return null;
		return waves.get(currentWave++);
	}
}
