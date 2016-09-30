package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import graph.AgmSolver;
import graph.MapEdge;
import graph.MapGraph;

public class AgmSolverTest {
	
	GraphableInteger a1 = new GraphableInteger(10);
	GraphableInteger a2 = new GraphableInteger(8);
	GraphableInteger a3 = new GraphableInteger(2);
	GraphableInteger a4 = new GraphableInteger(4);
	
	@Test
	public void GraphableIntegerTest(){
		GraphableInteger i1 = new GraphableInteger(2);
		GraphableInteger i2 = new GraphableInteger(2);
		GraphableInteger i3= new GraphableInteger(3);
		
		assertTrue(i1.equals(i2));
		assertFalse(i1.equals(i3));
		assertFalse(i1.equals(null));
	}
	
	private MapGraph<GraphableInteger> map(){
		
		List<GraphableInteger> toAdd = new ArrayList<GraphableInteger>();
		
		toAdd.add(a1);
		toAdd.add(a2);
		toAdd.add(a3);
		toAdd.add(a4);
		
		MapGraph<GraphableInteger> ret = new MapGraph<>(toAdd);
		
		ret.addEdge(toAdd.get(0), toAdd.get(1)); //from 10 to 8  weigth 2
		ret.addEdge(toAdd.get(0), toAdd.get(2)); //from 10 to 2  weigth 8
		ret.addEdge(toAdd.get(2), toAdd.get(3)); //from 2 to 4   weigth 2
		ret.addEdge(toAdd.get(0), toAdd.get(3)); //from 10 to 4  weigth 6
		
		return ret;
		
	}
	
	private MapGraph<GraphableInteger> mapAgm(){
		List<GraphableInteger> toAdd = new ArrayList<GraphableInteger>();
		
		toAdd.add(a1);
		toAdd.add(a2);
		toAdd.add(a3);
		toAdd.add(a4);
		
		MapGraph<GraphableInteger> ret = new MapGraph<>(toAdd);
		
		ret.addEdge(toAdd.get(0), toAdd.get(1)); //from 10 to 8  weigth 2
		ret.addEdge(toAdd.get(2), toAdd.get(3)); //from 2 to 4   weigth 2
		ret.addEdge(toAdd.get(0), toAdd.get(3)); //from 10 to 4  weigth 6
		
		return ret;
	}
	
	@Test
	public void minorEdgeTest(){
		
		MapGraph<GraphableInteger> map = map();
		
		Set<GraphableInteger> visited = new TreeSet<>();
		
		visited.add(a1);
		visited.add(a2);
		
		MapEdge<GraphableInteger> edge = AgmSolver.minorEdge(map, visited);
		
		MapEdge<GraphableInteger> exprectedEdge = new MapEdge<GraphableInteger>(new GraphableInteger(10), new GraphableInteger(4));

		double weight = edge.weight;
		
		assertEquals(6, weight, 0);
		assertTrue(exprectedEdge.equals(edge));
		
		
	}
	
	@Test
	public void getAgmTest(){
		
		MapGraph<GraphableInteger> map = map();
		MapGraph<GraphableInteger> expectedAgm = mapAgm();
		
		MapGraph<GraphableInteger> actualAgm = AgmSolver.getAGM(map);
		
		assertTrue(expectedAgm.equals(actualAgm));
		
	}

}
