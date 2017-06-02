package dk.itu.mhso.wmd.model;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import dk.itu.mhso.wmd.controller.PathParser;
import dk.itu.mhso.wmd.controller.UnitFactory;

public class Level {
	private List<Wave> waves = new ArrayList<>();
	private int currentWave;
	private List<Exit> exits;
	private List<Entrance> entrances;
	private BufferedImage bgImage;
	private String name;
	private Path2D pathArea;
	
	public Level(String pathName) {
		name = new File(pathName).getName();
		parseWaves(pathName, parsePath(pathName));
		loadBGImage(pathName);
	}

	private UnitPath parsePath(String pathName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(pathName + "/path.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PathParser parser = new PathParser(image);
		exits = parser.getExits();
		entrances = parser.getEntrances();
		pathArea = parser.getMainPath();
		return parser.getUnitPath();
	}
	
	private void parseWaves(String pathName, UnitPath unitPath) {
		Charset charset = Charset.forName("UNICODE");
		try(BufferedReader reader = Files.newBufferedReader(Paths.get(pathName + "/enemies.txt"), charset)) {
			String line = reader.readLine();
			List<Enemy> currentWaveEnemies = new ArrayList<>();
			while(line != null) {
				if(line.equals("-")) {
					waves.add(new Wave(currentWaveEnemies));
					currentWaveEnemies = new ArrayList<>();
				}
				else if(line.startsWith("//")) {}
				else {
					Enemy enemy = (Enemy)UnitFactory.createUnit(line);
					enemy.setUnitPath(unitPath);
					currentWaveEnemies.add(enemy);
				}
				line = reader.readLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
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
	
	public List<Exit> getExits() {
		return exits;
	}
	
	public Path2D getMainPathArea() {
		return pathArea;
	}
	
	public List<Entrance> getEntrances() {
		return entrances;
	}
	
	public int getCurrentWaveNr() {
		return currentWave;
	}
	
	public Wave getNextWave() {
		if(currentWave == waves.size()) return null;
		return waves.get(currentWave++);
	}
}
