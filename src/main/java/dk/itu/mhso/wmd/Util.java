package dk.itu.mhso.wmd;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Util {
	public static double calculateAngle(Point p1, Point p2) {
		int xDelta = p2.x-p1.x;
		int yDelta = p2.y-p1.y;
		return Math.atan2(yDelta, xDelta);
	}
	
	public static Object readObjectFromFile(String fileName) {
		try {
            var stream = Main.PRODUCTION ? Util.class.getResourceAsStream(fileName) : new FileInputStream(fileName);
			ObjectInputStream oos = new ObjectInputStream(stream);
			Object object = oos.readObject();
			oos.close();
			return object;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean writeObjectToFile(Object object, String fileName) {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(object);
			oos.close();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
    }
}
