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
	
	public Level(String pathName) {
		name = new File(pathName).getName();
		parseUnitData(pathName);
		loadBGImage(pathName);
	}

	private void parseUnitData(String pathName) {
		try {
			List<UnitPath> paths = new ArrayList<>();
			List<Path2D> areas = new ArrayList<>();
			Iterator<Path> files = Files.list(Paths.get(pathName)).iterator();
			while(files.hasNext()) {
				Path path = files.next();
				if(path.toString().contains("path")) paths.add((UnitPath) Util.readObjectFromFile(path.toString()));
				else if(path.toString().contains("area")) areas.add((Path2D) Util.readObjectFromFile(path.toString()));
			}
			pathAreas = areas.toArray(new Path2D[areas.size()]);
			parseWaves(pathName, paths.toArray(new UnitPath[paths.size()]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseWaves(String pathName, UnitPath[] unitPaths) {
		List<Enemy> currentWaveEnemies = new ArrayList<>();
		if(!Files.exists(Paths.get(pathName + "/enemies.txt"))) {
			String[] arr = Resources.getDefaultEnemies();
			for(String s : arr) {
				if(s.equals("-")) {
					waves.add(new Wave(currentWaveEnemies));
					currentWaveEnemies = new ArrayList<>();
				}
				else if(s.startsWith("//")) {}
				else {
					Enemy enemy = (Enemy)UnitFactory.createUnit(s);
					enemy.setUnitPaths(unitPaths);
					currentWaveEnemies.add(enemy);
				}
			}
		}
		else {
			Charset charset = Charset.forName("UNICODE");
			try(BufferedReader reader = Files.newBufferedReader(Paths.get(pathName + "/enemies.txt"), charset)) {
				String line = reader.readLine();
				while(line != null) {
					if(line.equals("-")) {
						waves.add(new Wave(currentWaveEnemies));
						currentWaveEnemies = new ArrayList<>();
					}
					else if(line.startsWith("//")) {}
					else {
						Enemy enemy = (Enemy)UnitFactory.createUnit(line);
						enemy.setUnitPaths(unitPaths);
						currentWaveEnemies.add(enemy);
					}
					line = reader.readLine();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
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
	
	public String getName() {
		return name;
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
