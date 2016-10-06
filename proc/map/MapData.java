package map;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import graph.Distanciable;

public class MapData <T extends Distanciable<T>> implements Iterable<T>{
	
	private ArrayList<T> points;
	private Exportable<T> exportator;
	
	public MapData(Exportable<T> exportator){
		points = new ArrayList<>();
		this.exportator = exportator;
		
	}
	
	public void loadFromFile(File file) throws IOException, ParseException{
		
		JSONParser parser = new JSONParser();

		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
		
		JSONArray coords = (JSONArray) jsonObject.get("mapData");
		
		if (coords == null) throw new IOException("El archivo " + file.getAbsolutePath() + " no tiene el formato esperado");
		
		for (Object object : coords) {
			
			JSONObject coord = (JSONObject)object;
			this.addPoint(exportator.fromJSON(coord));
			
		}
		
		
	}
	
	@SuppressWarnings("unchecked") //Es realmente necesario
	public void saveToFile(File file) throws IOException{
		
		JSONObject obj = new JSONObject();

		JSONArray list = new JSONArray();
		
		for(T point : points){
			list.add(exportator.toJSON(point));
		}

		obj.put("mapData", list);

		FileWriter finalFile = new FileWriter(file);
		finalFile.write(obj.toJSONString());
		finalFile.flush();
		finalFile.close();

		
	}
	
	public void addPoint(T point){
		
		if(point != null)
			points.add(point);
			
	}
	
	public void removePoint(T point){
		
		this.points.remove(point);
		
	}
	
	public void removeClosestTo(T other){
		
		T toRemove = this.closestTo(other);
		if (other.distanceTo(toRemove) < 10E-3)
			removePoint(toRemove);
		
	}
	
	public T closestTo(T other){
		
		if(other == null) return null;
		
		if(points.size() == 0) return null;
		
		T ret = points.get(0);
		double dist = Double.MAX_VALUE;
		
		for (T point : points) if(point.distanceTo(other) < dist){
			
			ret = point;
			dist = point.distanceTo(other);
			
		}
		
		return ret;
		
		
	}
	
	public List<T> getPoints(){
		
		if (points == null) return null;
		
		ArrayList<T> ret = new ArrayList<>();
		
		for(T point: points) ret.add(exportator.clone(point));
		
		return ret;
		
	}
	
	public int getPointsCant(){
		return this.points.size();
	}

	@Override
	public Iterator<T> iterator() {
		return this.points.iterator();
	}
		

}
