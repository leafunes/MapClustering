package mapSolvers;

import java.util.List;

import graph.MapEdge;
import graph.MapGraph;
import graph.AgmSolver;
import graph.ClusterSolver;
import graph.Graphable;
import map.Cluster;

public class LongerEdge <E extends Graphable<E>> extends MapSolver<E>{ 
	
	MapGraph<E> graphAGM;
	
	public LongerEdge(){
		NAME = "Mayor arista";
		
	}
	
	@Override
	public List<Cluster<E>> solveMap(int cantClusters){
		
		MapGraph<E> graphAGM_copy = graphAGM.clone();
		
		removeVertices(graphAGM_copy, cantClusters);
		
		return ClusterSolver.getClustersOf(graphAGM_copy);
		
	}
	
	@Override
	public void actualizeData(List<E> mapPoints) {
		
		this.mapPoints.clear();
		this.mapPoints.addAll(mapPoints);
		
		MapGraph<E> graph = generateGraph();
		graphAGM = AgmSolver.getAGM(graph);
		
		
	}

	public void removeVertices(MapGraph<E> graphAGM, int cantClusters) {
		for(int i = 0; i < cantClusters - 1; i++){
			MapEdge<E> e = graphAGM.getLongerEdge();
			graphAGM.removeEdge(e);
		}
		
	}

	public MapGraph<E> generateGraph() {
		
		MapGraph<E> graph = new MapGraph<>(mapPoints);
		
		for (E i : mapPoints) {
			for (E j : mapPoints) {
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
