package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import graph.ClusterSolver;
import graph.MapGraph;
import map.Cluster;

public class ClusterSolverTest {
	
	GraphableInteger a1 = new GraphableInteger(10);
	GraphableInteger a2 = new GraphableInteger(8);
	GraphableInteger a3 = new GraphableInteger(2);
	GraphableInteger a4 = new GraphableInteger(4);
	GraphableInteger a5 = new GraphableInteger(1);
	GraphableInteger a6 = new GraphableInteger(13);

	private MapGraph<GraphableInteger> map(){
		
		List<GraphableInteger> toAdd = new ArrayList<GraphableInteger>();
		
		toAdd.add(a1);
		toAdd.add(a2);
		toAdd.add(a3);
		toAdd.add(a4);
		toAdd.add(a5);
		toAdd.add(a6);
		
		MapGraph<GraphableInteger> ret = new MapGraph<>(toAdd);
		
		ret.addEdge(toAdd.get(0), toAdd.get(1)); //from 10 to 8  weigth 2
		ret.addEdge(toAdd.get(2), toAdd.get(3)); //from 2 to 4   weigth 2
		
		ret.addEdge(toAdd.get(2), toAdd.get(5)); //from 2 to 13  weigth 11
		ret.addEdge(toAdd.get(5), toAdd.get(3)); //from 13 to 4  weigth 9
		ret.addEdge(toAdd.get(1), toAdd.get(4)); //from 8 to 1   weigth 7
		
		return ret;
		
	}

	@Test
	public void getVecindarioOfTest(){
		MapGraph<GraphableInteger> map = map();
		
		Set<GraphableInteger> visited = new TreeSet<>();
		
		ClusterSolver.vecindarioOf(a1, map,visited);
		
		Set<GraphableInteger> vecindarioExpected = new TreeSet<>();
		
		vecindarioExpected.add(a1);
		vecindarioExpected.add(a2);
		vecindarioExpected.add(a5);
		
		assertTrue(visited.containsAll(vecindarioExpected));
	}
	
	@Test(timeout = 1000)
	public void testGetClustersOf() {
		MapGraph<GraphableInteger> map = map();
		
		List<Cluster<GraphableInteger>> clusters = ClusterSolver.getClustersOf(map);
		
		assertEquals(2, clusters.size());
		
		Set<GraphableInteger> vecindarioExpected = new TreeSet<>();
		
		vecindarioExpected.add(a1);
		vecindarioExpected.add(a2);
		vecindarioExpected.add(a5);
		
		Cluster<GraphableInteger> c1 = new Cluster<>();
		c1.addPoint(a1);
		c1.addPoint(a2);
		c1.addPoint(a3);
		
		
		assertTrue(clusters.get(0).equals(c1));
		
	}
	

}
