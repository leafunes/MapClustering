package tests;

import graph.Distanciable;

class GraphableInteger implements Distanciable<GraphableInteger>{
	
	int value;
	
	public GraphableInteger (int n) {
		this.value = n;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(obj.getClass() != this.getClass()) return false;
		
		GraphableInteger integer = (GraphableInteger)obj;
		return integer.getValue() == this.getValue();
		
	}

	@Override
	public double distanceTo(GraphableInteger other) {
		
		return Math.abs( this.value - other.value );
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	@Override
	public int compareTo(GraphableInteger other) {
		return this.value - other.value;
	}
	
}