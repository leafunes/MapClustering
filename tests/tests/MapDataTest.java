package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import map.MapData;
import map.MapPoint;

public class MapDataTest {
	
	MapPoint.Exportator exportator = new MapPoint.Exportator();
	
	private MapData<MapPoint> mapaCon2(){
		MapData <MapPoint> map = new MapData<>(exportator);
		
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
		
		MapData<MapPoint> map = mapaCon2();
		
		List<MapPoint> points = map.getPoints();
		
		assertEquals(2,points.size());
		assertEquals(123.123, points.get(0).getLat(), 10E-9);
		assertEquals(-456.458, points.get(0).getLon(), 10E-9);
		
	}
	
	@Test
	public void loadFromFileTest() {
		
		MapData<MapPoint> map = new MapData<MapPoint>(exportator);
		
		File file = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"mapTest.json");
		
		try {
			map.loadFromFile(file);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		List<MapPoint> points = map.getPoints();
		
		assertEquals(2,points.size());

		assertEquals(-34.52133782929332, points.get(0).getLat(), 10E-5);
		assertEquals(-58.70068073272705, points.get(0).getLon(), 10E-5);
		
	}
	
	@Test (expected = ParseException.class)
	public void loadFromNotJSONTest() throws ParseException, IOException{
		
		MapData <MapPoint> map = new MapData<>(exportator);
		
		File notJson = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"notAJson.txt");
		
		map.loadFromFile(notJson);
		
	}
	
	@Test (expected = IOException.class)
	public void loadFromOtherJSONTest() throws ParseException, IOException{
		
		MapData <MapPoint> map = new MapData<>(exportator);
		
		File notJson = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"otherJson.txt");
		
		map.loadFromFile(notJson);
		
	}
	
	@Test (expected = ClassCastException.class)
	public void loadFromNotFormattedJsonTest() throws ParseException, IOException{
		
		MapData <MapPoint> map = new MapData<>(exportator);
		
		File notJson = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"stringJson.json");
		
		map.loadFromFile(notJson);
		
	}
	
	@Test
	public void saveToFileTest() {
		
		MapData <MapPoint> map = mapaCon2();
		
		File file = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"mapTestOut.json");
		
		try {
			map.saveToFile(file);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		MapData <MapPoint> mapnew = new MapData<>(exportator);
		
		try {
			mapnew.loadFromFile(file);
		} catch (IOException | ParseException e) {
			
			e.printStackTrace();
		}
		
		List<MapPoint> points = mapnew.getPoints();
		
		assertEquals(2, points.size());

		assertEquals(123.123, points.get(0).getLat(), 10E-5);
		assertEquals(-456.458, points.get(0).getLon(), 10E-5);
		
	}
	
	@Test
	public void removePointTest(){
		MapData <MapPoint> map = mapaCon2();
		
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
		MapData<MapPoint> map = mapaCon2();
		
		MapPoint mapP = map.getPoints().get(0);
		
		MapPoint point1 = new MapPoint(123.125, -456.458);
		
		MapPoint closest = map.closestTo(point1);
		
		assertEquals(mapP, closest);
		
		@SuppressWarnings("unused")// No se usa, es solo para ver que no falla con null
		MapPoint closest2 = map.closestTo(null);
	}

}
