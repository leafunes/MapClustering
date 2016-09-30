package mapSolvers;

import java.util.ArrayList;
import java.util.List;

import graph.MapEdge;
import graph.MapGraph;
import graph.AgmSolver;
import graph.ClusterSolver;
import graph.Graphable;
import map.Cluster;

abstract public class MapSolver <E extends Graphable<E>>{
	
	protected List<E> mapPoints;
	protected int cantClusters;
	public String NAME;
	
	public MapSolver(List<E> mapPoints, int n) {
		this.mapPoints = new ArrayList<E>();
		
		this.mapPoints.addAll(mapPoints);
		
		this.cantClusters = n;
	}
	
	
	public abstract List<Cluster<E>> solveMap();


}
