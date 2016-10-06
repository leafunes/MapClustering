package graph;

import java.util.Set;
import java.util.TreeSet;


public class AgmSolver {
	
	// Algoritmo de Prim
		public static <T extends Distanciable<T>> MapGraph<T> getAGM(MapGraph<T> graph){
			
			if(graph == null || graph.getVertices() == 0) throw new IllegalArgumentException("Parámtero nulo");
			
			MapGraph<T> agm = new MapGraph<>(graph.getVerticesSet());
			
			Set<T> visited = new TreeSet<>();
			Set<T> notVisited = new TreeSet<>();
			
			for(T vertex : graph.getVerticesSet()) notVisited.add(vertex);
			
			visited.add(graph.getVertex(0)); // Cualquiera
			notVisited.remove(graph.getVertex(0));
			
			int edges = graph.getEdges();
			
			for(int i=0; i< edges - 1; ++i)
			{
				MapEdge<T> a = minorEdge(graph, notVisited, visited);
				
				try {
					agm.addEdge(a.vertex1, a.vertex2);
				}catch(IllegalArgumentException e){}
				
				visited.add(a.vertex2);
				notVisited.remove(a.vertex2);
			}
			
			return agm;
			
		}

		// Retorna la arista de menor peso entre un vertice visitado y uno no visitado
		public static <T extends Distanciable<T>> MapEdge<T> minorEdge(MapGraph<T> graph, Set<T> notVisited, Set<T> visited)
		{
			MapEdge<T> ret = new MapEdge<>(graph.getVertex(0), graph.getVertex(0), Double.MAX_VALUE);
			
			for(T i: visited)
			for(T j: notVisited){
				
				if(graph.containsEdge(i, j) && graph.getWeigth(i, j) < ret.weight )
					ret = new MapEdge<>(i, j);
			}
			
			return ret;
		}

}
