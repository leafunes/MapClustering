package map;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import graph.Graphable;

public class MapPoint implements Graphable<MapPoint>{
	
	private double lat;
	private double lon;
	
	private final float EARTH_RAD = 6371;
	
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
	
	public MapPoint clone(){
		MapPoint ret = new MapPoint(lat,lon);
		return ret;
	}

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
	    
	    if (!(other instanceof MapPoint))return false;
	    
	    MapPoint otherMapPoint = (MapPoint)other;
	    
	    if(this.getLat() == otherMapPoint.getLat() && this.getLon() == otherMapPoint.getLon())return true;
	    else return false;
	}
	
	@Override
	public String toString(){
		return "(" + "Lat: " + this.lat + ", Lon: " + this.lon + ")";
	}

	public Coordinate toCoordinate() {
		return new Coordinate(lat, lon);
	}

}