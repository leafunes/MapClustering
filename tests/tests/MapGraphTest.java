package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import graph.MapEdge;
import graph.MapGraph;
import map.MapPoint;

public class MapGraphTest {
	
	MapPoint p1 = new MapPoint(123, 456);
	MapPoint p2 = new MapPoint(125, 456);
	MapPoint p3 = new MapPoint(204, 456);
	MapPoint p4 = new MapPoint(205, 456);
	

	private MapGraph<MapPoint> generateMap() {
		ArrayList<MapPoint> array = new ArrayList<>();
		array.add(p1);
		array.add(p2);
		array.add(p3);
		array.add(p4);
		
		MapGraph<MapPoint> graph = new MapGraph<>(array);
		return graph;
	}

	@Test
	public void addEdgeTest() {
		
		MapGraph<MapPoint> graph = generateMap();
		
		assertEquals(0, graph.getEdges());
		
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p2);
		
		graph.addEdge(p2, p1);
		
		assertEquals(1, graph.getEdges());
		
		Double dist = p1.distanceTo(p2);
		
		assertEquals(dist, graph.getWeigth(p1, p2));
		assertEquals(dist, graph.getWeigth(p2, p1));
		
		MapEdge<MapPoint> edge = new MapEdge<MapPoint>(p2, p4);
		
		graph.addEdge(edge);
		
		assertEquals(2, graph.getEdges());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNullEdgeTest() {
		
		MapGraph<MapPoint> graph = generateMap();
		
		assertEquals(0, graph.getEdges());
		
		graph.addEdge(null, p2);
		
		assertEquals(0, graph.getEdges());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNotExistentEdgeTest(){
		
		MapGraph<MapPoint> graph = generateMap();
		
		assertEquals(0, graph.getEdges());
		
		MapPoint notExistent = new MapPoint(0, 0);
		
		graph.addEdge(p1, notExistent);
		
		assertEquals(0, graph.getEdges());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addLoopVEdgeTest(){

		MapGraph<MapPoint> graph = generateMap();
		
		assertEquals(0, graph.getEdges());
		
		graph.addEdge(p1, p1);
		
		assertEquals(0, graph.getEdges());
	}
	
	@Test
	public void removeEdgeTest(){

		MapGraph<MapPoint> graph = generateMap();
		
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
	public void containsEdgeTest(){

		MapGraph<MapPoint> graph = generateMap();
		
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p3);
		graph.addEdge(p2, p4);
		
		assertFalse(graph.containsEdge(p1, p4));
		
		assertTrue(graph.containsEdge(p1, p2));
		
		graph.removeEdge(p1, p2);
		
		assertFalse(graph.containsEdge(p1, p2));
		
	}
	
	@Test
	public void getVertexTest(){
		MapGraph<MapPoint> graph = generateMap();
		
		MapPoint vertex = graph.getVertex(0);
		
		assertEquals(vertex, p1);
	}
	
	@Test
	public void getNehiOfTest(){
		MapGraph<MapPoint> graph = generateMap();
		
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p3);
		graph.addEdge(p1, p4);
		graph.addEdge(p3, p4);
		
		Set<MapPoint> nehi = graph.getNehiVertex(p4);
		
		Set<MapPoint> expected = new TreeSet<>();
		expected.add(p1);
		expected.add(p3);
		
		assertTrue(nehi.containsAll(expected));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getNehiOfNotExistentTest(){
		MapGraph<MapPoint> graph = generateMap();
		
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p3);
		graph.addEdge(p1, p4);
		graph.addEdge(p3, p4);
		
		Set<MapPoint> nehi = graph.getNehiVertex(null);
		
		Set<MapPoint> expected = new TreeSet<>();
		expected.add(p1);
		expected.add(p3);
		
		assertTrue(nehi.containsAll(expected));
		
	}
	
	@Test
	public void getLongerEdgeTest(){
		
		MapGraph<MapPoint> graph = generateMap();
		
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p3);
		graph.addEdge(p1, p4);
		graph.addEdge(p3, p4);
		
		MapEdge<MapPoint> edge = graph.getLongerEdge();
		
		assertEquals(9117.983984853816, edge.weight, 10E-9);
		
	}
	
	@Test
	public void getEdgeSetTest(){
		
		MapGraph<MapPoint> graph = generateMap();
		
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p3);
		graph.addEdge(p1, p4);
		graph.addEdge(p3, p4);
		//Repetidos
		graph.addEdge(p4, p3);
		graph.addEdge(p3, p4);
		
		List <MapEdge <MapPoint>> expected = new ArrayList<>();
		
		expected.add(new MapEdge<MapPoint>(p2, p1));
		expected.add(new MapEdge<MapPoint>(p3, p1));
		expected.add(new MapEdge<MapPoint>(p1, p4));
		expected.add(new MapEdge<MapPoint>(p4, p3));
		
		
		assertEquals( graph.getEdges(),graph.getEdgesList().size() );
		
		
		
		assertTrue(expected.containsAll(graph.getEdgesList()));
		
	}
	
	@Test
	public void getNehiEdgesTest(){
		MapGraph<MapPoint> graph = generateMap();
		
		MapEdge<MapPoint> e1 = new MapEdge<>(p1, p2);
		MapEdge<MapPoint> e2 = new MapEdge<>(p1, p3);
		MapEdge<MapPoint> e3 = new MapEdge<>(p1, p4);
		MapEdge<MapPoint> e4 = new MapEdge<>(p4, p3);
		
		graph.addEdge(e1);
		graph.addEdge(e2);
		graph.addEdge(e3);
		graph.addEdge(e4);
		
		List<MapEdge<MapPoint>> nehiList = graph.getNehiEdges(e1);
		List<MapEdge<MapPoint>> expected = new ArrayList<>();
		
		expected.add(e2);
		expected.add(e3);
		
		assertEquals(2, nehiList.size());
		
		assertTrue(nehiList.containsAll(expected));
		assertTrue(expected.containsAll(nehiList));
		
		assertFalse(nehiList.contains(e4));
	}

}
