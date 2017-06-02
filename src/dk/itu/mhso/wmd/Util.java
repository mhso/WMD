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
		return Math.atan2(yDelta, xDelta) + Math.toRadians(180);
	}
	
	public static Object readObjectFromFile(String fileName) {
		try {
			BufferedInputStream fout = new BufferedInputStream(new FileInputStream(fileName));
			ObjectInputStream oos = new ObjectInputStream(fout);
			Object object = oos.readObject();
			oos.close();
			return object;
		} catch(Exception e) {
			System.out.println("Could not find file: " + fileName);
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
