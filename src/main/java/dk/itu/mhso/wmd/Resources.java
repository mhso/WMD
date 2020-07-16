package dk.itu.mhso.wmd;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class Resources {
    private static Map<Integer, BufferedImage[]> explosionImages = new HashMap<>();
    private static String pathPrefix = "./";
	
	public static Map<Integer, BufferedImage[]> getExplosionImages() { return explosionImages; }
	
	public static void loadExplosionImages() throws IOException, URISyntaxException {
        BufferedImage[] originalImages = new BufferedImage[30];

        var uri = Resources.class.getResource("/dk/itu/mhso/wmd/").toURI();
        FileSystem fileSystem = FileSystems.getFileSystem(uri);
        Path imagePath = fileSystem.getPath("sprites/explosion");
        Stream<Path> walk = Files.walk(imagePath, 1);
		int i = 0;
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            var path = it.next();
            if (path.equals(imagePath))
                continue;
            String pathStr = path.toString().substring(1);
			originalImages[i] = ImageIO.read(ClassLoader.getSystemResource(pathStr));
			i++;
        }
        walk.close();

		explosionImages.put(100, originalImages);
		final double widthRatio = (double)originalImages[0].getWidth()/(double)originalImages[0].getHeight();
		BufferedImage[] images = getResizedExplosionImages(originalImages, widthRatio, 125);
		explosionImages.put(125, images);
		images = getResizedExplosionImages(originalImages, widthRatio, 175);
		explosionImages.put(175, images);
		images = getResizedExplosionImages(originalImages, widthRatio, 250);
		explosionImages.put(250, images);
		images = getResizedExplosionImages(originalImages, widthRatio, 350);
		explosionImages.put(350, images);
	}
	
	private static BufferedImage[] getResizedExplosionImages(BufferedImage[] originals, double widthRatio, int radius) {
		BufferedImage[] resizedImages = new BufferedImage[originals.length];
		for(int i = 0; i < originals.length; i++) {
			Image image = originals[i].getScaledInstance((int)(radius*2 * widthRatio), radius*2, Image.SCALE_SMOOTH);
			BufferedImage bfImage = new BufferedImage((int)(radius*2 * widthRatio), radius*2, BufferedImage.TYPE_INT_ARGB);
			bfImage.getGraphics().drawImage(image, 0, 0, null);
			resizedImages[i] = bfImage;
		}
		return resizedImages;
    }
	
	public static Path getLevelsPath() throws IOException, URISyntaxException {
        var uri = WMDConstants.class.getResource("/dk/itu/mhso/wmd/").toURI();
        if(Main.PRODUCTION) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            return fileSystem.getPath("levels");
        }
        return Paths.get(uri);
	}
	
	public static String getSpritesPath() {
		if(Main.PRODUCTION) return "sprites";
		else return pathPrefix + "resources/sprites";
	}
	
	public static String getUnitinfoPath() {
		if(Main.PRODUCTION) return "/unitinfo/";
		else return pathPrefix + "resources/unitinfo";
	}
	
	public static String getEditorPath() {
		if(Main.PRODUCTION) return "/editor";
		else return pathPrefix + "resources/editor";
	}
	
	public static String[] getEnemies(String pathName) {
		if(!Files.exists(Paths.get(pathName + "/enemies.txt"))) return getEnemiesAsArray("/default_enemies.txt");
		else return getEnemiesAsArray(pathName + "/enemies.txt");
	}
	
	private static String[] getEnemiesAsArray(String pathName) {
        Charset charset = Charset.forName("UNICODE");
		try(var reader = new BufferedReader(new InputStreamReader(Resources.class.getResourceAsStream(pathName), charset))) {
			List<String> lines = new ArrayList<>();
			String line = reader.readLine();
			while(line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			return lines.toArray(new String[lines.size()]);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
