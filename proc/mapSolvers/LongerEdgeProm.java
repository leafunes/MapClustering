package mapSolvers;

import java.util.List;

import graph.AgmSolver;
import graph.ClusterSolver;
import graph.Graphable;
import graph.MapEdge;
import graph.MapGraph;
import map.Cluster;

public class LongerEdgeProm <E extends Graphable<E>> extends MapSolver<E>{
	
	MapGraph<E> graphAGM;

	public LongerEdgeProm() {
		NAME = "Mayor arista prom.";
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

	public void removeVertices(MapGraph<E> graphAGM, int cantClusters) {
		for(int i = 0; i < cantClusters - 1; i++){
			MapEdge<E> e = getLongerEdgeProm(graphAGM);
			graphAGM.removeEdge(e);
		}
		
	}
	
	private MapEdge<E> getLongerEdgeProm(MapGraph<E> graphAGM){
		
		List <MapEdge<E>> edges = graphAGM.getEdgesList();
		
		double longerRelDist = Double.MIN_VALUE;
		MapEdge<E> longerEdge = null;
		
		for (MapEdge<E> actualEdge : edges) {
			
			double actualRelDist = actualEdge.weight / getEdgeProm(graphAGM.getNehiEdges(actualEdge));
			
			if(actualRelDist > longerRelDist){
				longerRelDist = actualRelDist;
				longerEdge = actualEdge;
			}

			
		}
		
		return longerEdge;
		
	}

	
	private double getEdgeProm(List<MapEdge<E>> edges){
		
		double prom = 0;
		
		for(MapEdge<E> edge: edges)prom += edge.weight;
		
		return prom/edges.size();
	}
	
	
}
