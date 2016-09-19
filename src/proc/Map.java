package proc;


import java.util.ArrayList;

public class Map{
	
	private ArrayList<MapPoint> points;
	private GrafoPesado grafo;
	
	public Map(){
		points = new ArrayList<>();
		
	}
	
	public Map(ArrayList<MapPoint> points){
		this();
		
		for (MapPoint point : points){
			this.points.add(point);
		}
		
	}
	
	public void addPoint(MapPoint point){
		points.add(point);
		
		if(points.size() >= 2){
			//TODO
		}
		
	}

}
