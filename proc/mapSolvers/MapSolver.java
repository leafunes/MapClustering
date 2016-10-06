package mapSolvers;

import java.util.ArrayList;
import java.util.List;

import graph.MapEdge;
import graph.MapGraph;
import graph.AgmSolver;
import graph.ClusterSolver;
import graph.Distanciable;
import map.Cluster;
import map.Exportable;

abstract public class MapSolver <E extends Distanciable<E>>{
	
	protected List<E> mapPoints;
	public String NAME;
	
	public MapSolver() {
		this.mapPoints = new ArrayList<E>();
	}
	
	public abstract void actualizeData(List<E> mapPoints);
	public abstract List<Cluster<E>> solveMap(int cantClusters, Exportable<E> exportador);


}
