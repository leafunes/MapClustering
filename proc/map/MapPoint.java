package map;

import java.util.List;

import org.json.simple.JSONObject;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import graph.Distanciable;

public class MapPoint implements Distanciable<MapPoint>{
	
	public static class Exportator implements Exportable<MapPoint>{

		@Override
		public MapPoint fromJSON(JSONObject obj) {

			double lat = (double)obj.get("latitud");
			double lon = (double)obj.get("longitud");
			
			return new MapPoint(lat, lon);
		}

		@SuppressWarnings("unchecked") //Es realmente necesario
		@Override
		public JSONObject toJSON(MapPoint obj) {
			
			JSONObject ret = new JSONObject();
			ret.put("latitud", obj.getLat());
			ret.put("longitud", obj.getLon());
			
			return ret;
		}

		@Override
		public MapPoint clone(MapPoint other) {
			return other.clone();
		}

		
	}
	
	private double lat;
	private double lon;
	
	private final float EARTH_RAD = 6371;
	
	public MapPoint(){
		
	}
	
	public MapPoint(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	
	public double getLat(){
		return lat;
	}
	
	
	public double getLon(){
		return lon;
	}
	
	
	public void setLat(double lat) {
		this.lat = lat;
		
	}

	
	public void setLon(double lon) {
		this.lon = lon;
		
	}
	
	public MapPoint clone(){
		MapPoint ret = new MapPoint(lat,lon);
		return ret;
	}
	
	public Coordinate toCoordinate() {
		return new Coordinate(lat, lon);
	}
	
	
	public static Coordinate listToMedianCoordinate(List<MapPoint> list){
		if(list == null) throw new IllegalArgumentException("Lista de puntos nula");
		if(list.isEmpty()) return new Coordinate(0, 0);
			
			
		double x = 0;
        double y = 0;
        double z = 0;

        for (MapPoint mapPoint : list) {
			
            double latitude = mapPoint.getLat() * Math.PI / 180;
            double longitude = mapPoint.getLon() * Math.PI / 180;

            x += Math.cos(latitude) * Math.cos(longitude);
            y += Math.cos(latitude) * Math.sin(longitude);
            z += Math.sin(latitude);
        }

        double total = list.size();

        x = x / total;
        y = y / total;
        z = z / total;

        double centralLongitude = Math.atan2(y, x);
        double centralSquareRoot = Math.sqrt(x * x + y * y);
        double centralLatitude = Math.atan2(z, centralSquareRoot);

        return new Coordinate(centralLatitude * 180 / Math.PI, centralLongitude * 180 / Math.PI);
	}
	
	@Override
	public double distanceTo(MapPoint other) {
		if(other == null)return 0;
			
		double dLat = Math.toRadians(this.getLat() - other.getLat());  
	    double dLng = Math.toRadians(this.getLon() - other.getLon());  
	    double sindLat = Math.sin(dLat / 2);  
	    double sindLng = Math.sin(dLng / 2);  
	    double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
	            * Math.cos(Math.toRadians(this.getLat())) * Math.cos(Math.toRadians(other.getLat()));  
	    double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
	    double dist = EARTH_RAD * va2;  
	
	    return dist;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    
	    if (other == this) return true;
	    
	    if (other.getClass() == this.getClass()){
	    	MapPoint otherMapPoint = (MapPoint)other;
	    	if(this.getLat() == otherMapPoint.getLat() && 
	    			this.getLon() == otherMapPoint.getLon())return true;
	    }
	    return false;
	}
	
	@Override
	public String toString(){
		return "(" + "Lat: " + this.lat + ", Lon: " + this.lon + ")";
	}

	@Override
	public int compareTo(MapPoint other) {
		if (other == null) return 1;
	    
	    if (other == this) return 0;
	    
	    //TODO closest to north pole?
	    
	    MapPoint northPole = new MapPoint(90, 0);
	    if(this.distanceTo(northPole) < other.distanceTo(northPole))return 1;
	    if(this.distanceTo(northPole) > other.distanceTo(northPole))return -1;
	    
	    return 0;
	}


}
