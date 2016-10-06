package mapSolvers;

import java.util.List;

import graph.MapEdge;
import graph.MapGraph;
import graph.AgmSolver;
import graph.ClusterSolver;
import graph.Distanciable;
import map.Cluster;
import map.Exportable;

public class LongerEdge <E extends Distanciable<E>> extends MapSolver<E>{ 
	
	MapGraph<E> graphAGM;
	MapGraph<E> rawGraph;
	MapGraph<E> clustersGraph;
	
	public LongerEdge(){
		NAME = "Mayor arista";
		
	}
	
	@Override
	public List<Cluster<E>> solveMap(int cantClusters, Exportable<E> exportador){
		
		if(this.mapPoints.isEmpty())throw new IllegalArgumentException("La lista de puntos esta vacia");
		
		removeEdges(cantClusters);
		
		return ClusterSolver.getClustersOf(clustersGraph,exportador);
		
	}
	
	@Override
	public void actualizeData(List<E> mapPoints) {
		
		this.mapPoints.clear();
		this.mapPoints.addAll(mapPoints);
		
		generateGraph();
		graphAGM = AgmSolver.getAGM(rawGraph);
		
		
	}

	public void removeEdges(int cantClusters) {
		
		if(graphAGM == null) throw new NullPointerException("El AGM es nulo");
		
		if(cantClusters > graphAGM.getEdges())throw new IllegalArgumentException("No se pueden crear " + cantClusters + " con un mapa con " + graphAGM.getEdges() + " puntos");
		
		if(cantClusters <= 0)throw new IllegalArgumentException("la cantidad de clusters debe ser positiva: " + cantClusters);
		
		clustersGraph = graphAGM.clone();
		
		for(int i = 0; i < cantClusters - 1; i++){
			MapEdge<E> e = clustersGraph.getLongerEdge();
			clustersGraph.removeEdge(e);
		}
		
	}

	public void generateGraph() {
		
		rawGraph = new MapGraph<>(mapPoints);
		
		for (E i : mapPoints) for (E j : mapPoints)
			if(i.equals(j) == false)rawGraph.addEdge(i, j);
		
	}
	
	public void setMapPoints(List<E> mapPoints){
		this.mapPoints.clear();
		this.mapPoints.addAll(mapPoints);
	}

	public MapGraph<E> getGraphAGM() {
		return graphAGM;
	}

	public MapGraph<E> getRawGraph() {
		return rawGraph;
	}

	public MapGraph<E> getClustersGraph() {
		return clustersGraph;
	}

	public void setGraphAGM(MapGraph<E> graphAGM) {
		this.graphAGM = graphAGM;
	}

	public void setRawGraph(MapGraph<E> rawGraph) {
		this.rawGraph = rawGraph;
	}

	public void setClustersGraph(MapGraph<E> clustersGraph) {
		this.clustersGraph = clustersGraph;
	}



}
