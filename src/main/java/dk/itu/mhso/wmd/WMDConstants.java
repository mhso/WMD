package dk.itu.mhso.wmd;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

public class WMDConstants {
	public static final Dimension WINDOW_SIZE_BASE = new Dimension(1010, 839);
	public static final double AOE_DAMAGE_RATIO = 0.4;
	public static final Image EXPLOSION_IMAGE = Toolkit.getDefaultToolkit().getImage("resources/sprites/explosion.gif");
	
	public static final int FIRE_RATE_INVERTION = 6000;
	
	public static final int LEVEL_EDITOR_CURSOR_SIZE = 17;
	
	public static final int ENEMY_AREA_WIDTH = 50;
	
	public static final int ENEMY_MOVE_TICK_INVERSION = 8;
	
	public static final int PATH_POINT_SKIP_AMOUNT = 20;
	
	public static final int DEFAULT_TICKRATE = 100;
	
	public static final int PROJECTILE_MOVE_MOD = 10;
	
	public static final int WAVE_TEXT_DURATION = 3500;
	
	public static final double SELL_RETURN_PERCENTAGE = 0.75;
	
	public static final double RANGE_COST_UPGRADE_FACTOR = 0.3;
	public static final double RANGE_COST_INCREMENT_FACTOR = 1.5;
	
	public static final double DAMAGE_COST_UPGRADE_FACTOR = 0.4;
	public static final double DAMAGE_COST_INCREMENT_FACTOR = 1.6;
}
