package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;


public class MapGraph <V extends Graphable<V>>{

	HashMap<V, HashSet<V> > table;
	private ArrayList<V> refList;
	
	int edges;
	int vertices;
	
	public MapGraph(Collection<? extends V> vertices){
		
		table = new HashMap<>();
		refList = new ArrayList<V>();
		refList.addAll(vertices);
		
		for (V v : vertices) {
			
			HashSet<V> neighbors = new HashSet<>();
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
	
	public void removeEdge(V edge1, V edge2){
		
		if(containsEdge(edge1, edge2))
			edges--;
		
		table.get(edge1).remove(edge2);
		table.get(edge2).remove(edge1);
			
	}
	
	public void removeEdge(MapEdge<V> e){

		if(e == null) throw new IllegalArgumentException("El edge es nulo");
		removeEdge(e.vertex1, e.vertex2);
		
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
	
	public Double getWeigth(V e1, V e2){
		if(containsEdge(e1, e2)){
			
			return e1.distanceTo(e2);
		}
		
		return null;
	}
	
	public Set<V> getNehiVertex(V i2) {
		
		if(i2 == null || !table.containsKey(i2))
			throw new IllegalArgumentException("No existe el vertice");
		
		return table.get(i2);
		
		
	}
	
	public List< MapEdge<V> > getNehiEdges(V v1, V v2){
		
		if(v1 == null || v2 == null)
			throw new IllegalArgumentException("Arista nula");
		
		if(!containsEdge(v1, v2))
			throw new IllegalArgumentException("no existe la arista, " +
											v1.toString() + " -- " + v2.toString());
		
		List < MapEdge <V> > ret = new ArrayList<>();
		
		for(V vertex : table.get(v1)){
			if(!vertex.equals(v2))ret.add( new MapEdge<V>(v1, vertex));
		}
		
		for(V vertex : table.get(v2)){
			if(!vertex.equals(v1))ret.add( new MapEdge<V>(v2, vertex));
		}
		
		return ret;
		
	}
	
	public List< MapEdge<V> > getNehiEdges(MapEdge<V> edge){
		
		if(edge == null)
			throw new IllegalArgumentException("Arista nula");
		
		if(!containsEdge(edge.vertex1, edge.vertex2))
			throw new IllegalArgumentException("no existe la arista, " +
											edge.toString());
		
		return getNehiEdges(edge.vertex1, edge.vertex2);
		
	}
	
	public Set<V> getVerticesSet(){
		return table.keySet();
	}
	
	public V getVertex(int i) {
		
		if(i > refList.size() || i < 0)
			throw new IllegalArgumentException("Arista no encontrado " + i);
		
		
		return refList.get(i);
	}
	
	public List< MapEdge<V> > getEdgesList(){
		
		List< MapEdge<V> >ret = new ArrayList<>();
		
		for(V vertex1 : table.keySet()){
			for(V vertex2: table.get(vertex1)){
				MapEdge<V> toAdd = new MapEdge<V>(vertex1, vertex2);
				
				if(!ret.contains(toAdd))ret.add(toAdd);
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
	
	private void checkBounds(V v1, V v2){
		if (v1 == null || v2 == null)
			throw new IllegalArgumentException("Vertice nulo");

		if (!table.containsKey(v1) || !table.containsKey(v2))
			throw new IllegalArgumentException("Vertices no existentes" + v1.toString() + ", " + v2.toString());

		if (v1.equals(v2))
			throw new IllegalArgumentException("No se pueden agregar loops: " + v1.toString());
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
