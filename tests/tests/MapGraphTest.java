package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import graph.MapGraph;
import map.MapPoint;

public class MapGraphTest {
	
	MapPoint p1 = new MapPoint(123, 456);
	MapPoint p2 = new MapPoint(125, 456);
	MapPoint p3 = new MapPoint(204, 456);
	MapPoint p4 = new MapPoint(205, 456);
	
	private void fill4Refs(MapGraph<MapPoint> graph){
	
		
		graph.addEdge(p1);
		graph.addEdge(p2);
		graph.addEdge(p3);
		graph.addEdge(p4);
	}

	@Test
	public void addVertexTest() {
		
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		assertEquals(0, graph.getVertices());
		
		graph.addVertex(p1, p2);
		
		graph.addVertex(p2, p1);
		
		assertEquals(1, graph.getVertices());
		
		Double dist = p1.distanceTo(p2);
		
		assertEquals(dist, graph.getWeigth(p1, p2));
		assertEquals(dist, graph.getWeigth(p2, p1));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNullVertexTest() {
		
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		assertEquals(0, graph.getVertices());
		
		graph.addVertex(null, p2);
		
		assertEquals(0, graph.getVertices());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNotExistentVertexTest(){
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		assertEquals(0, graph.getVertices());
		
		MapPoint notExistent = new MapPoint(0, 0);
		
		graph.addVertex(p1, notExistent);
		
		assertEquals(0, graph.getVertices());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addLoopVertexTest(){
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		assertEquals(0, graph.getVertices());
		
		graph.addVertex(p1, p1);
		
		assertEquals(0, graph.getVertices());
	}
	
	@Test
	public void removeVertexTest(){
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		graph.addVertex(p1, p2);
		graph.addVertex(p1, p3);
		graph.addVertex(p2, p4);
		
		assertEquals(3, graph.getVertices());
		
		graph.removeVertex(p1, p2);
		
		assertEquals(2, graph.getVertices());
		
		graph.removeVertex(p1, p2);
		
		assertEquals(2, graph.getVertices());
		
	}
	
	@Test
	public void containsVertexTest(){
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		graph.addVertex(p1, p2);
		graph.addVertex(p1, p3);
		graph.addVertex(p2, p4);
		
		assertFalse(graph.containsVertex(p1, p4));
		
		assertTrue(graph.containsVertex(p1, p2));
		
		
		graph.removeVertex(p1, p2);
		
		assertFalse(graph.containsVertex(p1, p2));
		
	}

}
