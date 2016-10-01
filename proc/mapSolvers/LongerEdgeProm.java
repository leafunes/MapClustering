package mapSolvers;

import java.util.List;

import graph.AgmSolver;
import graph.ClusterSolver;
import graph.Graphable;
import graph.MapEdge;
import graph.MapGraph;
import map.Cluster;

public class LongerEdgeProm <E extends Graphable<E>> extends MapSolver<E>{
	
	

	public LongerEdgeProm(List<E> mapPoints, int n) {
		super(mapPoints, n);
	}
	
	@Override
	public List<Cluster<E>> solveMap(){
		
		MapGraph<E> graph = generateGraph();
		
		MapGraph<E> graphAGM = AgmSolver.getAGM(graph);
		
		removeVertices(graphAGM);
		
		return ClusterSolver.getClustersOf(graphAGM);
		
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

	public void removeVertices(MapGraph<E> graphAGM) {
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
