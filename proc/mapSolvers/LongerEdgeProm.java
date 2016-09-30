package mapSolvers;

import java.util.List;

import graph.Graphable;
import map.Cluster;

public class LongerEdgeProm <E extends Graphable<E>> extends MapSolver<E>{
	
	

	public LongerEdgeProm(List<E> mapPoints, int n) {
		super(mapPoints, n);
	}

	@Override
	public List<Cluster<E>> solveMap() {
		
		return null; //TODO
		
	}

	
	
}
