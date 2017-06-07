package dk.itu.mhso.wmd;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class Resources {
	private static Map<Integer, BufferedImage[]> explosionImages = new HashMap<>();
	
	public static Map<Integer, BufferedImage[]> getExplosionImages() { return explosionImages; }
	
	public static void loadExplosionImages() throws IOException {
		BufferedImage[] originalImages = new BufferedImage[30];
		Iterator<Path> imagePaths = Files.list(Paths.get(Resources.getSpritesPath()+"/explosion")).iterator();
		int i = 0;
		while(imagePaths.hasNext()) {
			Path path = imagePaths.next();
			originalImages[i] = ImageIO.read(path.toFile());
			i++;
		}
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
	
	public static String getLevelsPath() {
		if(Main.PRODUCTION) return WMDConstants.class.getResource("/resources/levels").toString();
		else return "resources/levels";
	}
	
	public static String getSpritesPath() {
		if(Main.PRODUCTION) return WMDConstants.class.getResource("/resources/sprites").toString();
		else return "resources/sprites";
	}
	
	public static String getUnitinfoPath() {
		if(Main.PRODUCTION) return WMDConstants.class.getResource("/resources/unitinfo").toString();
		else return "resources/unitinfo";
	}
	
	public static String getEditorPath() {
		if(Main.PRODUCTION) return WMDConstants.class.getResource("/resources/editor").toString();
		else return "resources/editor";
	}
	
	public static String[] getDefaultEnemies() {
		Charset charset = Charset.forName("UNICODE");
		try(BufferedReader reader = Files.newBufferedReader(Paths.get("resources/default_enemies.txt"), charset)) {
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
