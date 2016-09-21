package graph;

public class MapEdge <V extends Graphable<V>>{
	public V vertex1;
	public V vertex2;		
	public Double weight;
	
	public MapEdge(V i, V j, Double weight){
		vertex1 = i;
		vertex2 = j;
		this.weight = weight;
	}

	@Override
	public boolean equals(Object other)
	{
		
		if (other instanceof MapEdge<?>){
	        if ( ((MapEdge<?>)other).vertex1.equals(vertex1) && ((MapEdge<?>)other).vertex2.equals(vertex2)){
	            return true;
	        }
	    }
		
		return false;
	}	
}
