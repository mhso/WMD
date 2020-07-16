package dk.itu.mhso.wmd.model;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;

import dk.itu.mhso.wmd.Resources;
import dk.itu.mhso.wmd.Util;

public class BeamProjectile extends Projectile {
	private final boolean debug = false;
	
	private BufferedImage[] windupImages;
	private BufferedImage beamIcon;
	private int counter;
	
	private final int TICK_DIV = 20;
	
	public BeamProjectile(String name, Ally ally, Enemy targetEnemy) {
		super(name, ally, targetEnemy);
		location = getWindupLocation();
	}

	@Override
	public void move() {
		int index = counter/TICK_DIV;
		
		location = getWindupLocation();
		
		if(index >= windupImages.length) icon = getBeamImage();
		if(index < windupImages.length) icon = windupImages[index];
		angle = Util.calculateAngle(location, getTarget().getLocation());
		
		counter++;
	}
	
	@Override
	public BufferedImage transformIcon(BufferedImage transformIcon) {
		AffineTransform transform = AffineTransform.getRotateInstance(getAngle(), 0, getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(transformIcon, null);
	}

	private Point getWindupLocation() {
		int x = (int)(getAlly().getMiddlePoint().x-7 + (getAlly().getWidth()/2) * Math.cos(getAlly().getAngle()));
		int y = (int)(getAlly().getMiddlePoint().y-7 + (getAlly().getWidth()/2) * Math.sin(getAlly().getAngle()));
		return new Point(x, y);
	}
	
	@Override
	public boolean hasHit() {
		return counter/TICK_DIV == 12 || getAlly().getCurrentlyTargetedEnemy() == null;
	}

	private BufferedImage getBeamImage() {
		int dist = (int)getTarget().getLocation().distance(location);
		
		BufferedImage image = new BufferedImage(dist, beamIcon.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				image.setRGB(x, y, beamIcon.getRGB(x, y));
			}
		}
		return image;
	}
	
	@Override
	public void loadIcons(String unitName) {
		super.loadIcons(unitName);
		
		windupImages = new BufferedImage[9];
		try {
			Iterator<Path> pathIt = Files.list(Paths.get(Resources.getSpritesPath() + "/ally/particle_cannon_windup")).iterator();
			int i = 0;
			while(pathIt.hasNext()) {
				Path p = pathIt.next();
				windupImages[i] = ImageIO.read(p.toFile());
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		beamIcon = icon;
		icon = windupImages[0];
    }
}