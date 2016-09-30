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
	
	public MapEdge(V i, V j){
		vertex1 = i;
		vertex2 = j;
		this.weight = i.distanceTo(j);
	}

	@Override
	public boolean equals(Object other)
	{
		
		if (other instanceof MapEdge<?>){
			MapEdge<?>edge = (MapEdge<?>)other;
			
	        if ( edge.vertex1.equals(vertex1) && edge.vertex2.equals(vertex2) ||
	        		edge.vertex1.equals(vertex2) && edge.vertex2.equals(vertex1)){
	            return true;
	        }
	    }
		
		return false;
	}	
}
