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
import map.MapPoint;

abstract public class MapSolver <E extends Distanciable<E>>{
	
	protected List<E> mapPoints;
	protected List< Cluster<E> > finalClusters;
	public String NAME;
	
	public MapSolver() {
		this.mapPoints = new ArrayList<E>();
	}
	
	public double getClusterProm(){
		double prom = 0;
		
		for (Cluster<E> cluster : finalClusters) {
			prom += cluster.size();
		}
		
		return prom / finalClusters.size();
	}
	
	public int getClusterMax(){
		int max = Integer.MIN_VALUE;
		
		for (Cluster<E> cluster : finalClusters) {
			if(cluster.size() > max){
				max = cluster.size();
			}
		}
		
		return max;
		
	}
	
	public int getClusterMin(){
		int min = Integer.MAX_VALUE;
		
		for (Cluster<E> cluster : finalClusters) {
			if(cluster.size() < min){
				min = cluster.size();
			}
		}
		
		return min;
	}
	
	public double getClusterDesv(double median){
		
		double desv = 0;
		
		for (Cluster<E> cluster : finalClusters) {
			double distToMedian = median - cluster.size();
			
			desv = Math.pow(distToMedian, 2);
		}
		
		return desv / finalClusters.size();
	}
	
	public int getClustersCant(){
		return finalClusters.size();
	}
	
	public abstract void actualizeData(List<E> mapPoints);
	public abstract List<Cluster<E>> solveMap(int cantClusters, Exportable<E> exportador);


}
