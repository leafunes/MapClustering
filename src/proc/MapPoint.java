package proc;

public class MapPoint {
	
	private double lat;
	private double lon;
	
	private final float EARTH_RAD = 6371;
	
	public MapPoint(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	public double getDistTo(MapPoint other){
		
		//TODO
		return 0;
		
		
	}
	
	public double getLat(){
		return lat;
	}
	
	public double getLon(){
		return lon;
	}

}
