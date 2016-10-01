package graph;

import java.util.Set;
import java.util.TreeSet;


public class AgmSolver {
	
	// Algoritmo de Prim
		public static <T extends Graphable<T>> MapGraph<T> getAGM(MapGraph<T> graph){
			
			//TODO
			if(graph == null || graph.getVertices() == 0) throw new IllegalArgumentException("Parámtero nulo");
			
			MapGraph<T> agm = new MapGraph<>(graph.getVerticesSet());
			Set<T> visited = new TreeSet<>();
			visited.add(graph.getVertex(0)); // Cualquiera
			
			int edges = graph.getEdges();
			
			for(int i=0; i< edges - 1; ++i)
			{
				MapEdge<T> a = minorEdge(graph, visited);
				
				try {
					agm.addEdge(a.vertex1, a.vertex2);
				}finally{}
				
				visited.add(a.vertex2);
			}
			
			return agm;
			
		}

		// Retorna la arista de menor peso entre un vertice visitado y uno no visitado
		public static <T extends Graphable<T>> MapEdge<T> minorEdge(MapGraph<T> graph, Set<T> visited)
		{
			MapEdge<T> ret = new MapEdge<>(graph.getVertex(0), graph.getVertex(0), Double.MAX_VALUE);
			
			for(T i: visited)
			for(T j: graph.getNehiVertex(i)) if( visited.contains(j) == false )
			{
				if( graph.getWeigth(i, j) < ret.weight )
					ret = new MapEdge<>(i, j);
			}
			
			return ret;
		}

}
