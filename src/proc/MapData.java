package proc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class MapData{
	
	private ArrayList<MapPoint> points;
	private GrafoPesado grafo;
	
	public MapData(){
		points = new ArrayList<>();
		
	}
	
	public MapData(ArrayList<MapPoint> points){
		this();
		
		for (MapPoint point : points){
			this.points.add(point);
		}
		
	}
	
	public void loadFromFile(File file) throws IOException, ParseException{
		
		JSONParser parser = new JSONParser();

		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));

		JSONArray coords = (JSONArray) jsonObject.get("mapData");
		for (Object object : coords) {
			
			JSONObject coord = (JSONObject)object;
			double lat = (double)coord.get("latitud");
			double lon = (double)coord.get("longitud");
			
			MapPoint toAdd = new MapPoint(lat, lon);
			this.addPoint(toAdd);
			
		}
		
		
	}
	
	public void saveToFile(File file) throws IOException{
		
		JSONObject obj = new JSONObject();

		JSONArray list = new JSONArray();
		
		for(MapPoint point : points){
			JSONObject coord = new JSONObject();
			coord.put("latitud", point.getLat());
			coord.put("longitud", point.getLon());
			list.add(coord);
		}

		obj.put("mapData", list);

		FileWriter finalFile = new FileWriter(file);
		finalFile.write(obj.toJSONString());
		finalFile.flush();
		finalFile.close();

		
	}
	
	public void addPoint(MapPoint point){
		
		if(point != null){
			
			points.add(point);
			
			if(points.size() >= 2){
				//TODO
			}
		}
		
	}
	
	public ArrayList<MapPoint> getPoints(){
		
		if (points == null) return null;
		
		ArrayList<MapPoint> ret = new ArrayList<>();
		
		for(MapPoint point: points) ret.add(point.clone());
		
		return ret;
		
	}
		

}
