package dk.itu.mhso.wmd;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

public class WMDConstants {
	public static final Dimension WINDOW_SIZE_BASE = new Dimension(1010, 839);
	public static final double AOE_DAMAGE_RATIO = 0.4;
	public static final Image EXPLOSION_IMAGE = Toolkit.getDefaultToolkit().getImage("resources/sprites/explosion.gif");
	
	public static final int FIRE_RATE_INVERTION = 300;
	
	public static final double SELL_RETURN_PERCENTAGE = 0.75;
	
	public static final double RANGE_COST_UPGRADE_FACTOR = 0.3;
	public static final double RANGE_COST_INCREMENT_FACTOR = 1.5;
	
	public static final double DAMAGE_COST_UPGRADE_FACTOR = 0.4;
	public static final double DAMAGE_COST_INCREMENT_FACTOR = 1.6;
}
