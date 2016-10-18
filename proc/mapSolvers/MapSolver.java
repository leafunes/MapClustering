package mapSolvers;

import java.util.ArrayList;
import java.util.List;

import graph.Distanciable;
import map.Cluster;
import map.Exportable;

abstract public class MapSolver <E extends Distanciable<E>>{
	
	protected List<E> mapPoints;
	protected List< Cluster<E> > finalClusters;
	public String NAME;
	
	public MapSolver() {
		this.mapPoints = new ArrayList<E>();
	}
	
	public double getClusterProm(){
		
		if(finalClusters.isEmpty()) return 0;
		
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
		
		if(finalClusters.isEmpty()) return 0;
		
		double desv = 0;
		
		for (Cluster<E> cluster : finalClusters) {
			double distToMedian = median - cluster.size();
			
			desv += Math.pow(distToMedian, 2);
		}
		
		double ret = Math.sqrt( (desv / (finalClusters.size())) );
		
		return ret;
	}
	
	public int getClustersCant(){
		return finalClusters.size();
	}
	
	public abstract void actualizeData(List<E> mapPoints);
	public abstract List<Cluster<E>> solveMap(int cantClusters, Exportable<E> exportador);


}
