package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import map.MapPoint;

public class MapPointTest {

	@Test
	public void testGetDistTo() {
		MapPoint p1 = new MapPoint(40.417, -3.703);
		MapPoint p2 = new MapPoint(40.453, -3.688);
		
		assertEquals(4.20, p1.distanceTo(p2), 0.1);//Segun google maps es 4.20Km
		assertNotEquals(6, p1.distanceTo(p2), 10E-3);
	}
	
	@Test
	public void equalsTest(){

		MapPoint p1 = new MapPoint(40.417, -3.703);
		MapPoint p2 = new MapPoint(40.453, -3.688);
		MapPoint p3 = new MapPoint(40.417, -3.703);
		
		assertTrue(p1.equals(p3));
		assertFalse(p1.equals(p2));
	}

}
