package graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import map.Cluster;
import map.Exportable;

public class ClusterSolver {
	
	public static <T extends Distanciable<T>> List<Cluster<T>> getClustersOf(MapGraph<T> map, Exportable<T> exportador){
		
		ArrayList<T> vertices = new ArrayList<>();
		
		List<Cluster<T>> ret = new ArrayList<Cluster<T>>();
		
		for (T vertex: map.getVerticesSet()) {
			vertices.add(vertex);
		}
		
		while(!vertices.isEmpty()){
			
			Cluster<T> cluster = new Cluster<T>(exportador);
			
			Set<T> vecindario = new TreeSet<>();
			
			ClusterSolver.vecindarioOf(vertices.get(0), map, vecindario);
			
			cluster.addAll(vecindario);
			
			ret.add(cluster);
			vertices.removeAll(vecindario);
			
		}
		
		
		return ret;
		
		
		
	}

	public static <T extends Distanciable<T>> void vecindarioOf(T e, MapGraph<T> map, Set<T> visited) {

		visited.add(e);
		
		Set<T> notVisited = map.getNehiVertex(e);
		notVisited.removeAll(visited);
		
		for(T vertex : notVisited ){
			ClusterSolver.vecindarioOf(vertex, map, visited);
		}
		
	}
	

}
