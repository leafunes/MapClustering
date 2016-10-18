package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import map.Cluster;
import map.Exportable;
import mapSolvers.MapSolver;

public class MapSolverTest {
	
	private class MapSolverForTest extends MapSolver<GraphableInteger>{
		
		public void setfinalClusters(List<Cluster<GraphableInteger>> finalClusters){
			super.finalClusters = finalClusters;
		}
		
		@Override
		public void actualizeData(List<GraphableInteger> mapPoints) {	
		}

		@Override
		public List<Cluster<GraphableInteger>> solveMap(int cantClusters, Exportable<GraphableInteger> exportador) {
			return null;
		}
		
	}
	
	GraphableInteger a1 = new GraphableInteger(10);
	GraphableInteger a2 = new GraphableInteger(8);
	GraphableInteger a3 = new GraphableInteger(2);
	GraphableInteger a4 = new GraphableInteger(4);
	GraphableInteger a5 = new GraphableInteger(1);
	GraphableInteger a6 = new GraphableInteger(13);
	
	GraphableInteger.Exportador exportador = new GraphableInteger.Exportador();

	
	List<Cluster<GraphableInteger>> getList(){
		List <Cluster<GraphableInteger>> ret = new ArrayList<>();
		
		Cluster<GraphableInteger>c1 = new Cluster<>(exportador);
		
		c1.addPoint(a1);
		c1.addPoint(a2);
		
		ret.add(c1);
		
		Cluster<GraphableInteger>c2 = new Cluster<>(exportador);
		
		c2.addPoint(a3);
		c2.addPoint(a4);
		c2.addPoint(a5);
		
		ret.add(c2);
		
		Cluster<GraphableInteger>c3 = new Cluster<>(exportador);
		
		c3.addPoint(a6);
		
		ret.add(c3);
		
		return ret;
		
	}
	
	private MapSolverForTest getSolverConList(){
		MapSolverForTest solver = new MapSolverForTest();
		solver.setfinalClusters(getList());
		
		return solver;
	}

	@Test
	public void medianTest() {
		
		MapSolverForTest solver = getSolverConList();
		
		double prom = solver.getClusterProm();
		
		assertEquals(2, prom, 10E-9);
		
		
	}
	
	@Test
	public void maxAndMinTest() {
		
		MapSolverForTest solver = getSolverConList();
		
		double max = solver.getClusterMax();
		
		double min = solver.getClusterMin();
		
		assertEquals(3, max, 10E-9);
		
		assertEquals(1, min ,10E-9);
		
	}
	
	@Test
	public void desvTest() {
		
		MapSolverForTest solver = getSolverConList();
		
		double prom = solver.getClusterProm();
		
		double desv = solver.getClusterDesv(prom);
		
		assertEquals(0.816496, desv, 10E-5);
		
		
	}

}
