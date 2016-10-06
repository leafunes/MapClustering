package mapSolvers;

import java.util.List;

import graph.AgmSolver;
import graph.ClusterSolver;
import graph.Distanciable;
import graph.MapEdge;
import graph.MapGraph;
import map.Cluster;

public class LongerEdgeProm <E extends Distanciable<E>> extends MapSolver<E>{
	
	MapGraph<E> graphAGM;
	MapGraph<E> rawGraph;
	MapGraph<E> clustersGraph;

	public LongerEdgeProm() {
		NAME = "Mayor arista prom.";
	}
	
	@Override
	public List<Cluster<E>> solveMap(int cantClusters){
		
		if(this.mapPoints.isEmpty())throw new IllegalArgumentException("La lista de puntos esta vacia");
		
		removeVertices(cantClusters);
		
		return ClusterSolver.getClustersOf(clustersGraph);
		
		
	}
	
	@Override
	public void actualizeData(List<E> mapPoints) {
		
		this.mapPoints.clear();
		this.mapPoints.addAll(mapPoints);
		
		generateGraph();
		graphAGM = AgmSolver.getAGM(rawGraph);
		
	}
	

	public void generateGraph() {
		
		rawGraph = new MapGraph<>(mapPoints);
		
		for (E i : mapPoints) for (E j : mapPoints)
			if(i.equals(j) == false)rawGraph.addEdge(i, j);
	}

	public void removeVertices(int cantClusters) {
		
		if(graphAGM == null) throw new NullPointerException("El AGM es nulo");
		
		if(cantClusters > graphAGM.getEdges())throw new IllegalArgumentException("No se pueden crear " + cantClusters + " con un mapa con " + graphAGM.getEdges() + " puntos");
		
		if(cantClusters <= 0)throw new IllegalArgumentException("la cantidad de clusters debe ser positiva: " + cantClusters);
		
		clustersGraph = graphAGM.clone();
		
		for(int i = 0; i < cantClusters - 1; i++){
			
			MapEdge<E> e = getLongerEdgeProm();
			clustersGraph.removeEdge(e);
		}
		
	}
	
	private MapEdge<E> getLongerEdgeProm(){
		
		List <MapEdge<E>> edges = clustersGraph.getEdgesList();
		
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
