package mapSolvers;

import java.util.ArrayList;
import java.util.HashMap;

import graph.MapEdge;
import graph.MapGraph;
import graph.SolverAGM;
import map.Cluster;
import map.MapData;
import map.MapPoint;

public class MapSolver {
	
	public static HashMap<Integer, Cluster> solveMap(MapData map, int n){
		
		MapGraph<MapPoint> graph = MapSolver.generateGraph(map);
		
		MapGraph<MapPoint> graphAGM = SolverAGM.getAGM(graph);
		
		MapSolver.removeVerices(graphAGM, n);
		
		return MapSolver.generateClusters(graphAGM);
		
	}

	private static HashMap<Integer, Cluster> generateClusters(MapGraph<MapPoint> graphAGM) {
		return null; //TODO
	}

	private static void removeVerices(MapGraph<MapPoint> graphAGM, int n) {
		for(int i = 0; i < n; i++){
			MapEdge<MapPoint> e = graphAGM.getLongerEdge();
			graphAGM.removeEdge(e);
		}
		
	}

	private static MapGraph<MapPoint> generateGraph(MapData map) {
		
		ArrayList<MapPoint> mapPoints = map.getPoints();
		
		MapGraph<MapPoint> graph = new MapGraph<>(mapPoints.size());
		
		for (MapPoint mapPoint : mapPoints) {
			graph.addVertex(mapPoint);
		}
		
		for (MapPoint i : mapPoints) {
			for (MapPoint j : mapPoints) {
				try{
					graph.addEdge(i, j);
				}
				catch(IllegalArgumentException e){
					
				}
			}
		}
		
		return graph;
		
		
	}

}
