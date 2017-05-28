package dk.itu.mhso.wmd.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import dk.itu.mhso.wmd.model.Entrance;
import dk.itu.mhso.wmd.model.Exit;
import dk.itu.mhso.wmd.model.UnitPath;

public class PathParser {
	private final int AREA_RGB = Color.BLACK.getRGB();
	private final int MOVE_RGB = Color.RED.getRGB();
	private final int ENTRANCE_RGB = Color.GREEN.getRGB();
	private final int EXIT_RGB = Color.BLUE.getRGB();
	
	private BufferedImage pathImage;
	private List<Entrance> entrances = new ArrayList<>();
	private List<Exit> exits = new ArrayList<>();
	private Path2D mainPath;
	private UnitPath unitPath;
	
	public PathParser(BufferedImage pathImage) {
		this.pathImage = pathImage;
		parsePathArea();
		parseUnitPath();
	}
	
	private void parsePathArea() {
		List<Point> entrancePoints = new ArrayList<>();
		List<Point> exitPoints = new ArrayList<>();
				
		for(int x = 0; x < pathImage.getWidth(); x++) {
			for(int y = 0; y < pathImage.getHeight(); y++) {
				int pointColorRGB = pathImage.getRGB(x, y);
				if(pointColorRGB == ENTRANCE_RGB) entrancePoints.add(new Point(x, y));
				else if(pointColorRGB == EXIT_RGB) exitPoints.add(new Point(x, y));
			}
		}
		createExitsAndEntrances(exitPoints, true);
		createExitsAndEntrances(entrancePoints, false);
		
		List<UnitPath> movePaths = new ArrayList<>();
		List<Point> traversedPoints = new ArrayList<>();
		int x = entrances.get(0).getBetween().x;
		int y = entrances.get(0).getBetween().y;
		traversedPoints.add(entrances.get(0).getBetween());
		unitPath = new UnitPath();
		unitPath.addPoint(entrances.get(0).getBetween());
		boolean finished = false;
		while(!finished) {
			Point nextPoint = getNextPathPoint(new Point(x, y), traversedPoints, MOVE_RGB);
			traversedPoints.add(nextPoint);
			unitPath.addPoint(nextPoint);
			x = nextPoint.x;
			y = nextPoint.y;
			
			for(Exit exit : exits) {
				if(exit.getBetween().x == x && exit.getBetween().y == y) {
					unitPath.addPoint(exit.getBetween());
					finished = true;
					movePaths.add(unitPath);
				}
			}
		}
		
		List<Path2D> paths = new ArrayList<>();
		for(int i = 0; i < entrancePoints.size(); i++) {
			traversedPoints = new ArrayList<>();
			x = entrancePoints.get(i).x;
			y = entrancePoints.get(i).y;
			traversedPoints.add(entrancePoints.get(i));
			Path2D path = new Path2D.Double();
			path.moveTo(x, y);
			finished = false;
			while(!finished) {
				Point nextPoint = getNextPathPoint(new Point(x, y), traversedPoints, AREA_RGB);
				traversedPoints.add(nextPoint);
				path.lineTo(nextPoint.x, nextPoint.y);
				x = nextPoint.x;
				y = nextPoint.y;
				
				for(Exit exit : exits) {
					if(exit.getStartPoint().x == x && exit.getStartPoint().y == y) {
						path.lineTo(exit.getEndPoint().x, exit.getEndPoint().y);
						finished = true;
						paths.add(path);
					}
					else if(exit.getEndPoint().x == x && exit.getEndPoint().y == y) {
						path.lineTo(exit.getStartPoint().x, exit.getStartPoint().y);
						finished = true;
						paths.add(path);
					}
				}
			}
			if(i % 2 == 0) {
				path.moveTo(entrances.get(i/2).getStartPoint().x, entrances.get(i/2).getStartPoint().y);
				path.lineTo(entrances.get(i/2).getEndPoint().x, entrances.get(i/2).getEndPoint().y);
			}
			else {
				path.moveTo(exits.get(i/2).getStartPoint().x, exits.get(i/2).getStartPoint().y);
				path.lineTo(exits.get(i/2).getEndPoint().x, exits.get(i/2).getEndPoint().y);
			}
		}
		Path2D firstPath = paths.get(0);
		for(int i = 1; i < paths.size(); i++) {
			firstPath.append(paths.get(i), true);
		}
		mainPath = firstPath;
	}

	private Point getNextPathPoint(Point currentPoint, List<Point> traversedPoints, int SEARCH_RGB) {
		return getNextPathPoint(1, currentPoint, traversedPoints, SEARCH_RGB);
	}
	
	private Point getNextPathPoint(int radius, Point currentPoint, List<Point> traversedPoints, int SEARCH_RGB) {
		Point result = null;
		for(int i = currentPoint.x-radius; i <= currentPoint.x+radius; i++) {
			for(int j = currentPoint.y-radius; j <= currentPoint.y+radius; j++) {
				if(!traversedPoints.contains(new Point(i, j)) && isWithinBounds(i, j) && 
						(pathImage.getRGB(i, j) == SEARCH_RGB || pathImage.getRGB(i, j) == EXIT_RGB)) result = new Point(i, j);
			}
		}
		if(result == null) return getNextPathPoint(radius+1, currentPoint, traversedPoints, SEARCH_RGB);
		return result;
	}
	
	private boolean isWithinBounds(int x, int y) {
		return x >= 0 && x < pathImage.getWidth() && y >= 0 && y < pathImage.getHeight();
	}
	
	private void createExitsAndEntrances(List<Point> relevantPoints, boolean exit) {
		List<Point> usedPoints = new ArrayList<>();
		for(Point point : relevantPoints) {
			if(usedPoints.contains(point)) continue;
			double shortestDist = Double.POSITIVE_INFINITY;
			Point closestPoint = null;
			for(Point otherPoint : relevantPoints) {
				if(point == otherPoint) continue;
				double dist = point.distance(otherPoint);
				if(dist < shortestDist) {
					shortestDist = dist;
					closestPoint = otherPoint;
				}
			}
			if(exit) exits.add(new Exit(point, closestPoint));
			else entrances.add(new Entrance(point, closestPoint));
			usedPoints.add(point);
			usedPoints.add(closestPoint);
		}
	}
	
	public List<Exit> getExits() {
		return exits;
	}
	
	public List<Entrance> getEntrances() {
		return entrances;
	}
	
	public Path2D getMainPath() {
		return mainPath;
	}
	
	public UnitPath getUnitPath() {
		return unitPath;
	}
	
	private void connectMainArea(List<Point> movePoints) {
		
	}
	
	private void parseUnitPath() {
		
	}
}
