package graph;

public interface Distanciable <T> extends Comparable<T> {
	
	public abstract double distanceTo(T other);

}

