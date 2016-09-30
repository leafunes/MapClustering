package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;


public class MapGraph <V extends Graphable<V>>{

	HashMap<V, TreeSet<V> > table;
	private ArrayList<V> refList;
	
	int edges;
	int vertices;
	
	public MapGraph(Collection<? extends V> vertices){
		
		table = new HashMap<>();
		refList = new ArrayList<V>();
		refList.addAll(vertices);
		
		for (V v : vertices) {
			
			TreeSet<V> neighbors = new TreeSet<>();
			table.put(v, neighbors);
			
		}
		
		this.vertices = vertices.size();
	}
	
	public boolean containsEdge(V vertex1, V vertex2){
		
		checkBounds(vertex1, vertex2);
		
		return table.get(vertex1).contains(vertex2) &&
				table.get(vertex2).contains(vertex1);
	}

	public void addEdge(V edge1, V edge2) throws IllegalArgumentException{
		
		if(!containsEdge(edge1, edge2))
			edges++;
		
		
		table.get(edge1).add(edge2);
		table.get(edge2).add(edge1);
	}
	
	public void addEdge(MapEdge<V> e) throws IllegalArgumentException{
		if(e == null) throw new IllegalArgumentException("El edge es nulo");
		addEdge(e.vertex1, e.vertex2);
	}
	
	public void removeEdge(MapEdge<V> e){

		if(e == null) throw new IllegalArgumentException("El edge es nulo");
		removeEdge(e.vertex1, e.vertex2);
		
	}
	
	public void removeEdge(V edge1, V edge2){
		
		if(containsEdge(edge1, edge2))
			edges--;
		
		
		table.get(edge1).remove(edge2);
		table.get(edge2).remove(edge1);
		
		
	}
	
	public Double getWeigth(V e1, V e2){
		if(containsEdge(e1, e2)){
			
			return e1.distanceTo(e2);
		}
		
		return null;
	}
	
	public Set<V> getNehiVertex(V i2) {
		
		if(i2 == null || !table.containsKey(i2))
			throw new IllegalArgumentException("No existe la arista");
		
		return table.get(i2);
		
		
	}
	
	public Set<V> getVerticesSet(){
		return table.keySet();
	}
	
	public V getVertex(int i) {
		
		if(i > refList.size() || i < 0)
			throw new IllegalArgumentException("Arista no encontrado " + i);
		
		
		return refList.get(i);
	}
	
	private void checkBounds(V v1, V v2){
		if (v1 == null || v2 == null)
			throw new IllegalArgumentException("Vertice nulo");

		if (!table.containsKey(v1) || !table.containsKey(v2))
			throw new IllegalArgumentException("Vertices no existentes" + v1.toString() + ", " + v2.toString());

		if (v1.equals(v2))
			throw new IllegalArgumentException("No se pueden agregar loops: " + v1.toString());
	}



	public MapEdge<V> getLongerEdge() {
		
		if(edges == 0) return null;
		
		MapEdge<V> ret = new MapEdge<V>(null, null ,Double.MIN_VALUE);
		
		for (V edgeKey : table.keySet()) {
			for(V edgeNehi : getNehiVertex(edgeKey)){
				
				if( ret.weight.compareTo( edgeKey.distanceTo(edgeNehi)) == -1){
					ret = new MapEdge<V>(edgeKey, edgeNehi);
				}
				
			}
		}
		
		return ret;
	}
	
	public int getEdges(){
		return edges;
	}
	
	public int getVertices(){
		return vertices;
	}
	
	
	@Override
	public String toString(){
		//TODO
		return table.toString();
	}
	
	@Override
	public boolean equals(Object other){
		
		if(other == null)return false;
		
		if(other == this) return true;
		
		if(other instanceof MapGraph<?>){
			
			MapGraph<?> otherMap = (MapGraph<?>) other;
			
			return this.table.equals(otherMap.table);
		}
		return false;
		
	}

	
	
	
}
