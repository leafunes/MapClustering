package graph;

import java.util.ArrayList;

public class MapGraph <E extends Graphable<E>>{
	
	ArrayList<E> refs;
	Double [][] matrix;
	int vertices;
	
	public MapGraph(int edges){
		matrix = new Double [edges][edges];
		refs = new ArrayList<>(edges);
		
	}
	
	public void addEdge(E edge){
		if (edge == null)
			throw new IllegalArgumentException("No se pueden agregar aristas nulas");
			
		refs.add(edge);
		
	}
	
	public boolean containsVertex(E edge1, E edge2){
		
		checkBounds(edge1, edge2);
		
		int i = refs.indexOf(edge1);
		int j = refs.indexOf(edge2);
		
		return matrix[i][j] != null;
	}
	
	public void addVertex(E edge1, E edge2){
		
		if(!containsVertex(edge1, edge2))
			vertices++;
		
		int i = refs.indexOf(edge1);
		int j = refs.indexOf(edge2);
		
		matrix[i][j] = matrix[j][i] = edge1.distanceTo(edge2);
		
		
	}
	
	public void removeVertex(E edge1, E edge2){
		
		if(containsVertex(edge1, edge2))
			vertices--;
		
		int i = refs.indexOf(edge1);
		int j = refs.indexOf(edge2);
		
		matrix[i][j] = matrix[j][i] = null;
		
		
	}
	
	public Double getWeigth(E e1, E e2){
		if(containsVertex(e1, e2)){
			
			int i = refs.indexOf(e1);
			int j = refs.indexOf(e2);
			
			return matrix[i][j];
		}
		
		return null;
	}
	
	public int getVertices(){
		return vertices;
	}

	
	private void checkBounds(E e1, E e2){
		if (e1 == null || e2 == null)
			throw new IllegalArgumentException("Vertices nulos");

		if (!refs.contains(e1) || !refs.contains(e2))
			throw new IllegalArgumentException("Vertices no existentes" + e1.toString() + ", " + e2.toString());

		if (e1.equals(e2))
			throw new IllegalArgumentException("No se pueden agregar loops: " + e1.toString());
	}
	
	
	
}
