package dk.itu.mhso.wmd.model;

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
	
	public Level(String pathName) {
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
				else {
					Enemy enemy = (Enemy)UnitFactory.createUnit(line);
					enemy.setUnitPath(unitPath);
					enemy.loadIcon(line.toLowerCase());
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getBGImage() {
		return bgImage;
	}
	
	public List<Exit> getExits() {
		return exits;
	}
	
	public List<Entrance> getEntrances() {
		return entrances;
	}
	
	public Wave getNextWave() {
		if(currentWave == waves.size()) return null;
		Wave wave = waves.get(currentWave);
		currentWave++;
		return wave;
	}
}
