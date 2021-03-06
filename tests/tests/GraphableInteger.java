package tests;

import org.json.simple.JSONObject;

import graph.Distanciable;
import map.Exportable;

class GraphableInteger implements Distanciable<GraphableInteger>{
	
	public static class Exportador implements Exportable<GraphableInteger>{

		@Override
		public GraphableInteger clone(GraphableInteger other) {
			return new GraphableInteger(other.value);
		}

		@Override
		public GraphableInteger fromJSON(JSONObject obj) {
			
			Object undefinedValue =  obj.get("value");
			
			int value = 0;
			
			if (undefinedValue.getClass() == Integer.class){	
				value = (int)undefinedValue;
			}
			else if(undefinedValue.getClass() == Long.class){
				value = (int)( (long)undefinedValue );
			}
			
			return new GraphableInteger(value);
			
		}

		@SuppressWarnings("unchecked")//Realmente necesario
		@Override
		public JSONObject toJSON(GraphableInteger obj) {
			
			JSONObject ret = new JSONObject();
			ret.put("value", obj.value);
			return ret;
			
		}
		
	}
	
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