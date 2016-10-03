package map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import graph.Graphable;
import graph.MapGraph;

public class Cluster <E extends Graphable<E>> implements Iterable<E>{
	
	ArrayList<E> points;
	Color color;
	MapGraph<E> graph;
	
	public Cluster(Color c){
		this();
		this.color = c;
	}
	
	public Cluster (){
		points = new ArrayList<>();
		color = Color.yellow;
		
	}
	
	public void addPoint(E point){
		points.add(point);
	}

	@Override
	public Iterator<E> iterator() {
		return points.iterator();
	}

	public void addAll(Iterable<E> vecinos) {
		for (E e : vecinos) {
			points.add(e);
		}
		
	}
	
	@Override
	public boolean equals(Object other){
		if(other == null) return false;
		
		if(other.getClass() == this.getClass()){
			Cluster<?> cluster = (Cluster<?>) other;
			
			return cluster.points.containsAll(points) && points.containsAll(cluster.points);
			
		}
		return false;
	}

	public void setColor(Color color) {
		this.color = color;
		
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}
	
	

}
