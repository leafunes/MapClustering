package graph;

public interface Graphable <T> extends Comparable<T> {
	
	public abstract double distanceTo(T other);

}

