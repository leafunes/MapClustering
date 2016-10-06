package map;

import org.json.simple.JSONObject;

public interface Exportable <T>{
	
	T clone(T other);
	
	T fromJSON(JSONObject obj);
	JSONObject toJSON(T obj);
	
}
