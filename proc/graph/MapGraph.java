package graph;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import map.MapPoint;



public class MapGraph <V extends Graphable<V>>{

	
	ArrayList<V> refs;
	Double [][] matrix;
	int edges;
	
	public MapGraph(int edges){
		matrix = new Double [edges][edges];
		refs = new ArrayList<>(edges);
		
	}
	
	protected MapGraph(ArrayList<V> refs){
		matrix = new Double [refs.size()][refs.size()];
		this.refs = refs;
	}
	
	public void addVertex(V vertex){
		if (vertex == null)
			throw new IllegalArgumentException("No se pueden agregar aristas nulas");
			
		refs.add(vertex);
		
	}
	
	public boolean containsEdge(V vertex1, V vertex2){
		
		checkBounds(vertex1, vertex2);
		
		int i = refs.indexOf(vertex1);
		int j = refs.indexOf(vertex2);
		
		return matrix[i][j] != null;
	}

	public void addEdge(V edge1, V edge2) throws IllegalArgumentException{
		
		if(!containsEdge(edge1, edge2))
			edges++;
		
		int i = refs.indexOf(edge1);
		int j = refs.indexOf(edge2);
		
		matrix[i][j] = matrix[j][i] = edge1.distanceTo(edge2);
		
		
	}
	
	public void addEdge(MapEdge<V> e) throws IllegalArgumentException{
		
		addEdge(e.vertex1, e.vertex2);
		
		
	}
	
	public void removeEdge(MapEdge<V> e){
		
		removeEdge(e.vertex1, e.vertex2);
		
		
	}
	
	public void removeEdge(V edge1, V edge2){
		
		if(containsEdge(edge1, edge2))
			edges--;
		
		int i = refs.indexOf(edge1);
		int j = refs.indexOf(edge2);
		
		matrix[i][j] = matrix[j][i] = null;
		
		
	}
	
	public Double getWeigth(V e1, V e2){
		if(containsEdge(e1, e2)){
			
			int i = refs.indexOf(e1);
			int j = refs.indexOf(e2);
			
			return matrix[i][j];
		}
		
		return null;
	}
	
	public int getEdges(){
		return edges;
	}
	
	public int getVertices(){
		return refs.size();
	}

	
	private void checkBounds(V v1, V v2){
		if (v1 == null || v2 == null)
			throw new IllegalArgumentException("Vertice nulo");

		if (!refs.contains(v1) || !refs.contains(v2))
			throw new IllegalArgumentException("Vertices no existentes" + v1.toString() + ", " + v2.toString());

		if (v1.equals(v2))
			throw new IllegalArgumentException("No se pueden agregar loops: " + v1.toString());
	}

	public V getVertex(int i) {
		
		if(i < refs.size())
			throw new IllegalArgumentException("Arista no encontrado " + i);
		
		
		return refs.get(0);
	}

	public Set<V> getNehiVertex(V i2) {
		
		if(!refs.contains(i2))
			throw new IllegalArgumentException("No existe la arista");
		
		int index = refs.indexOf(i2);
		
		Set<V> ret = new TreeSet<V>();
		
		for(int i = 0; i < refs.size(); i++){
			
			if(matrix[index][i] != null) ret.add(refs.get(i));
			
		}
		
		return ret;
		
		
	}

	public MapEdge<V> getLongerEdge() {
		
		int longerI = 0;
		int longerJ = 0;
		
		for (int i = 0; i < getVertices(); i++){
			for (int j = 0; j < getVertices(); i++){
				if (matrix[i][j].compareTo(matrix[longerI][longerJ]) > 0){
					longerI = i;
					longerJ = j;
				}
			}
		}
		return new MapEdge<V>(refs.get(longerI), refs.get(longerJ), matrix[longerI][longerJ]);
	}
	
	
	
}
