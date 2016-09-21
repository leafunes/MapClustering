package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import map.MapData;
import map.MapPoint;

public class MapDataTest {
	
	private MapData mapaCon2(){
		MapData map = new MapData();
		
		MapPoint point1 = new MapPoint(123.123, -456.458);
		MapPoint point2 = new MapPoint(0.654, -10.19);
		MapPoint point3 = null;
		
		map.addPoint(point1);
		map.addPoint(point2);
		map.addPoint(point3);
		
		return map;
	}

	@Test
	public void addPointTest() {
		
		MapData map = mapaCon2();
		
		ArrayList<MapPoint> points = map.getPoints();
		
		assertEquals(2,points.size());
		assertEquals(123.123, points.get(0).getLat(), 10E-9);
		assertEquals(-456.458, points.get(0).getLon(), 10E-9);
		
	}
	
	@Test
	public void loadFromFileTest() {
		
		MapData map = new MapData();
		
		File file = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"mapTest.json");
		
		try {
			map.loadFromFile(file);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		ArrayList<MapPoint> points = map.getPoints();
		
		assertEquals(2,points.size());

		assertEquals(-34.52133782929332, points.get(0).getLat(), 10E-5);
		assertEquals(-58.70068073272705, points.get(0).getLon(), 10E-5);
		
	}
	
	@Test (expected = ParseException.class)
	public void loadFromNotJSONTest() throws ParseException, IOException{
		
		MapData map = new MapData();
		
		File notJson = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"notAJson.txt");
		
		map.loadFromFile(notJson);
		
	}
	
	@Test (expected = IOException.class)
	public void loadFromOtherJSONTest() throws ParseException, IOException{
		
		MapData map = new MapData();
		
		File notJson = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"otherJson.txt");
		
		map.loadFromFile(notJson);
		
	}
	
	@Test (expected = ClassCastException.class)
	public void loadFromNotFormattedJsonTest() throws ParseException, IOException{
		
		MapData map = new MapData();
		
		File notJson = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"stringJson.json");
		
		map.loadFromFile(notJson);
		
	}
	
	@Test
	public void saveToFileTest() {
		
		MapData map = mapaCon2();
		
		File file = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"mapTestOut.json");
		
		try {
			map.saveToFile(file);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		MapData mapnew = new MapData();
		
		try {
			mapnew.loadFromFile(file);
		} catch (IOException | ParseException e) {
			
			e.printStackTrace();
		}
		
		ArrayList<MapPoint> points = mapnew.getPoints();
		
		assertEquals(2, points.size());

		assertEquals(123.123, points.get(0).getLat(), 10E-5);
		assertEquals(-456.458, points.get(0).getLon(), 10E-5);
		
	}
	
	@Test
	public void removePointTest(){
		MapData map = mapaCon2();
		
		assertEquals(2, map.getPoints().size());
		
		MapPoint existingPoint = map.getPoints().get(0);
		
		map.removePoint(existingPoint);
		
		assertEquals(1, map.getPoints().size());
		
		MapPoint notExisting = new MapPoint(1234, 789);
		
		map.removePoint(notExisting);
		
		assertEquals(1, map.getPoints().size());
		
		map.removePoint(null);
		
		assertEquals(1, map.getPoints().size());
		
	}
	
	@Test 
	public void removeClosetTo(){
		MapData map = mapaCon2();
		
		MapPoint mapP = map.getPoints().get(0);
		
		MapPoint point1 = new MapPoint(123.125, -456.458);
		
		MapPoint closest = map.closestTo(point1);
		
		assertEquals(mapP, closest);
		
		MapPoint closest2 = map.closestTo(null);
	}

}
