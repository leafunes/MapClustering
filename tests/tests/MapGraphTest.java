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
	
		
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);
		graph.addVertex(p4);
	}

	@Test
	public void addVertexTest() {
		
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		assertEquals(0, graph.getEdges());
		
		graph.addEdge(p1, p2);
		
		graph.addEdge(p2, p1);
		
		assertEquals(1, graph.getEdges());
		
		Double dist = p1.distanceTo(p2);
		
		assertEquals(dist, graph.getWeigth(p1, p2));
		assertEquals(dist, graph.getWeigth(p2, p1));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNullVertexTest() {
		
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		assertEquals(0, graph.getEdges());
		
		graph.addEdge(null, p2);
		
		assertEquals(0, graph.getEdges());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNotExistentVertexTest(){
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		assertEquals(0, graph.getEdges());
		
		MapPoint notExistent = new MapPoint(0, 0);
		
		graph.addEdge(p1, notExistent);
		
		assertEquals(0, graph.getEdges());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addLoopVertexTest(){
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		assertEquals(0, graph.getEdges());
		
		graph.addEdge(p1, p1);
		
		assertEquals(0, graph.getEdges());
	}
	
	@Test
	public void removeVertexTest(){
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p3);
		graph.addEdge(p2, p4);
		
		assertEquals(3, graph.getEdges());
		
		graph.removeEdge(p1, p2);
		
		assertEquals(2, graph.getEdges());
		
		graph.removeEdge(p1, p2);
		
		assertEquals(2, graph.getEdges());
		
	}
	
	@Test
	public void containsVertexTest(){
		MapGraph<MapPoint> graph = new MapGraph<>(4);
		fill4Refs(graph);
		
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p3);
		graph.addEdge(p2, p4);
		
		assertFalse(graph.containsEdge(p1, p4));
		
		assertTrue(graph.containsEdge(p1, p2));
		
		
		graph.removeEdge(p1, p2);
		
		assertFalse(graph.containsEdge(p1, p2));
		
	}

}
