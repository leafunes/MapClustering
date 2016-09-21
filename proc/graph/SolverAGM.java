package graph;

import java.util.Set;
import java.util.TreeSet;


public class SolverAGM {
	
	// Algoritmo de Prim
		public static <E extends Graphable<E>> MapGraph<E> getAGM(MapGraph<E> graph){
			
			MapGraph<E> agm = new MapGraph<>(graph.refs);
			Set<E> visited = new TreeSet<>();
			visited.add(graph.getVertex(0)); // Cualquiera
			
			for(int i=0; i<graph.getEdges()-1; ++i)
			{
				MapEdge<E> a = minorEdge(graph, visited); // De un amarillo a un negro
				agm.addEdge(a.vertex1, a.vertex2);
				visited.add(a.vertex2);
			}
			
			return agm;
			
		}

		// Retorna la arista de menor peso entre un vertice amarillo y uno no amarillo
		static <E extends Graphable<E>> MapEdge<E> minorEdge(MapGraph<E> graph, Set<E> visited)
		{
			MapEdge<E> ret = new MapEdge<>(graph.getVertex(0), graph.getVertex(0), Double.MAX_VALUE);
			
			for(E i: visited)
			for(E j: graph.getNehiVertex(i)) if( visited.contains(j) == false )
			{
				if( graph.getWeigth(i, j) < ret.weight )
					ret = new MapEdge<>(i, j, graph.getWeigth(i, j));
			}
			
			return ret;
		}

}
