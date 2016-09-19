package proc;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.Test;

public class MapDataTest {

	@Test
	public void addPointTest() {
		
		MapData map = new MapData();
		
		MapPoint point1 = new MapPoint(123.123, -456.458);
		MapPoint point2 = new MapPoint(0.654, -10.19);
		MapPoint point3 = null;
		
		map.addPoint(point1);
		map.addPoint(point2);
		map.addPoint(point3);
		
		ArrayList<MapPoint> points = map.getPoints();
		
		assertEquals(2,points.size());
		assertEquals(point1.getLon(), points.get(0).getLon(), 10E-9);
		assertEquals(point1.getLat(), points.get(0).getLat(), 10E-9);
		
	}
	
	@Test
	public void loadFromFileTest() {
		
		MapData map = new MapData();
		
		File file = new File("mapTest.json");
		
		try {
			map.loadFromFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<MapPoint> points = map.getPoints();
		
		assertEquals(2,points.size());

		assertEquals(-34.52133782929332, points.get(0).getLat(), 10E-5);
		assertEquals(-58.70068073272705, points.get(0).getLon(), 10E-5);
		
	}
	
	@Test
	public void saveToFileTest() {
		
		MapData map = new MapData();
		
		MapPoint point1 = new MapPoint(123.123, -456.458);
		MapPoint point2 = new MapPoint(0.654, -10.19);
		MapPoint point3 = null;
		
		map.addPoint(point1);
		map.addPoint(point2);
		map.addPoint(point3);
		
		File file = new File("mapTestOut.json");
		
		try {
			map.saveToFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MapData mapnew = new MapData();
		
		try {
			mapnew.loadFromFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<MapPoint> points = mapnew.getPoints();
		
		assertEquals(2, points.size());

		assertEquals(point1.getLat(), points.get(0).getLat(), 10E-5);
		assertEquals(point1.getLon(), points.get(0).getLon(), 10E-5);
		
	}

}
