package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import graph.MapEdge;
import map.MapPoint;

public class MapEdgeTest {
	
	MapPoint p1 = new MapPoint(123, 456);
	MapPoint p2 = new MapPoint(125, 456);
	
	MapPoint p3 = new MapPoint(204, 456);
	MapPoint p4 = new MapPoint(125, 456);

	@Test
	public void equalsTest() {
		MapEdge< MapPoint > e1 = new MapEdge<>(p1,p2);
		MapEdge< MapPoint > e2 = new MapEdge<>(p2,p1);
		MapEdge< MapPoint > e3 = new MapEdge<>(p1,p3);

		MapEdge< MapPoint > e4 = new MapEdge<>(p4,p1);
		
		assertTrue(e1.equals(e2));
		assertTrue(e1.equals(e4));
		assertFalse(e1.equals(e3));
		
		List< MapEdge<MapPoint>> set = new ArrayList<>();
		
		set.add(e1);
		
		assertTrue(set.contains(e4));
		assertFalse(set.contains(e3));
	}

}
