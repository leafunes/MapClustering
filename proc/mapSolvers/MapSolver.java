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
	public String NAME;
	
	public MapSolver() {
		this.mapPoints = new ArrayList<E>();
	}
	
	public abstract void actualizeData(List<E> mapPoints);
	public abstract List<Cluster<E>> solveMap(int cantClusters);


}
