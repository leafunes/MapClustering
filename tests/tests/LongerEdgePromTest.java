package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import graph.MapEdge;
import graph.MapGraph;
import map.Cluster;
import mapSolvers.LongerEdgeProm;

public class LongerEdgePromTest {
	
	GraphableInteger i1 = new GraphableInteger(1);
	GraphableInteger i3 = new GraphableInteger(3);
	GraphableInteger i8 = new GraphableInteger(8);
	GraphableInteger i5 = new GraphableInteger(5);
	GraphableInteger i13 = new GraphableInteger(13);
	
	GraphableInteger.Exportador exportador = new GraphableInteger.Exportador();
	
	private List<GraphableInteger> getList(){
		List<GraphableInteger> ret = new ArrayList<>();
		ret.add(i1);
		ret.add(i3);
		ret.add(i5);
		ret.add(i8);
		ret.add(i13);
		
		return ret;
	}
	
	private MapGraph<GraphableInteger> getGraph() {
		//Creo grafo todos contra todos
		MapGraph<GraphableInteger> expected = new MapGraph<>(getList());
		//i1
		expected.addEdge(i1,i3);
		expected.addEdge(i1,i8);
		expected.addEdge(i1,i5);
		expected.addEdge(i1,i13);
		//i3
		expected.addEdge(i3,i8);
		expected.addEdge(i3,i5);
		expected.addEdge(i3,i13);
		//i5
		expected.addEdge(i5,i8);
		expected.addEdge(i5, i13);
		//i8
		expected.addEdge(i8, i13);
		return expected;
	}
	
	private List<MapEdge<GraphableInteger>> getEdges(){
		
		List<MapEdge<GraphableInteger>> ret = new ArrayList<>();
		
		ret.add(new MapEdge<GraphableInteger>(i1,i3));
		ret.add(new MapEdge<GraphableInteger>(i1,i8));
		ret.add(new MapEdge<GraphableInteger>(i1,i13));
		ret.add(new MapEdge<GraphableInteger>(i5,i8));
		
		return ret;
		
	}

	@Test
	public void generateGraphTest() {
		
		LongerEdgeProm<GraphableInteger> toTest = new LongerEdgeProm<GraphableInteger>();
		toTest.setMapPoints(getList());
		
		MapGraph<GraphableInteger> expected = getGraph();
		
		toTest.generateGraph();
		
		assertEquals(10, toTest.getRawGraph().getEdges());
		assertTrue(toTest.getRawGraph().equals(expected));
		
		
	}
	
	@Test
	public void getEdgesMedianTest(){
		
		LongerEdgeProm<GraphableInteger> toTest = new LongerEdgeProm<GraphableInteger>();
		List<MapEdge<GraphableInteger>> list = getEdges();
		
		double median = toTest.getEdgesMedian(list);
		
		assertEquals(7.1763500472, median, 10E-9);
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getEdgesMedianNullTest(){
		
		LongerEdgeProm<GraphableInteger> toTest = new LongerEdgeProm<GraphableInteger>();
		List<MapEdge<GraphableInteger>> list = null;
		
		@SuppressWarnings("unused")//Es solo para hacer saltar la excepecion
		double median = toTest.getEdgesMedian(list);
		
	}
	
	@Test
	public void removeVerticesTest(){
		
		LongerEdgeProm<GraphableInteger> toTest = new LongerEdgeProm<GraphableInteger>();
		
		MapGraph<GraphableInteger> graph = getGraph();
		
		toTest.setGraphAGM(graph);
		
		toTest.removeEdges(3);
		
		assertEquals(10, graph.getEdges());
		assertEquals(8, toTest.getClustersGraph().getEdges());
		
		assertTrue(toTest.getClustersGraph().containsEdge(i5, i13));
		assertFalse(toTest.getClustersGraph().containsEdge(i1, i13));
		assertFalse(toTest.getClustersGraph().containsEdge(i3, i13));
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void solveMapExceptionIITest(){
		
		LongerEdgeProm<GraphableInteger> toTest = new LongerEdgeProm<GraphableInteger>();
		
		MapGraph<GraphableInteger> graph = getGraph();
		
		toTest.setGraphAGM(graph);
		
		toTest.solveMap(1000, exportador);
		
	}
	
	@Test
	public void solveMapTest(){
		
		LongerEdgeProm<GraphableInteger> toTest = new LongerEdgeProm<GraphableInteger>();
		
		toTest.actualizeData(getList());
		
		List <Cluster<GraphableInteger>> expected = new ArrayList<>();
		Cluster<GraphableInteger> c1 = new Cluster<>(exportador);
		
		c1.addPoint(i1);
		c1.addPoint(i3);
		c1.addPoint(i5);
		c1.addPoint(i8);
		expected.add(c1);
		
		Cluster<GraphableInteger> c2 = new Cluster<>(exportador);
		
		c2.addPoint(i13);
		expected.add(c2);
		
		List <Cluster<GraphableInteger>> clusters = toTest.solveMap(2, exportador);
		
		assertEquals(2,clusters.size());

		assertTrue(clusters.containsAll(expected));
		
	}

}
