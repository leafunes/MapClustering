package map;

import java.awt.Color;
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
import graph.MapGraph;

public class Cluster <E extends Distanciable<E>> implements Iterable<E>{
	
	Color color;
	ArrayList<E> points;
	MapGraph<E> graph;
	Exportable<E> exportador;
	
	public Cluster(Color c, Exportable<E> exportador){
		this(exportador);
		this.color = c;
	}
	
	public Cluster (Exportable<E> exportador){
		points = new ArrayList<>();
		color = Color.yellow;
		this.exportador = exportador;
		
	}
	
	public void addPoint(E point){
		points.add(point);
	}
	
	public void removePoint(E point) {
		points.remove(point);
		
	}

	public void addAll(Iterable<E> vecinos) {
		for (E e : vecinos) {
			points.add(e);
		}
		
	}
	
	public boolean contains(E point){
		return points.contains(point);
	}
	
	public int size(){
		return points.size();
	}
	
	public static <E extends Distanciable<E>> int getBelongsIndex(List <Cluster<E>> list, E toExam){
		if(list == null)
			throw new IllegalArgumentException("La lista es nula");
		
		if(toExam == null)
			throw new IllegalArgumentException("El argumento a examinar es nulo");
		
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(toExam)) return i;
		}
		
		return -1;
		
	}
	
	public static <E extends Distanciable<E>> E getClosestToList(List <Cluster<E>> list, E toExam){
		
		return getClosestToList(list, toExam, Double.MAX_VALUE);
		
	}
	
	public static <E extends Distanciable<E>> E getClosestToList(List <Cluster<E>> list, E toExam, double limit){
		
		if(list == null)
			throw new IllegalArgumentException("La lista es nula");
		
		if(toExam == null)
			throw new IllegalArgumentException("El argumento a examinar es nulo");
		
		double dist = Double.MAX_VALUE;
		E closest = null;
		
		for (Cluster<E> cluster : list) {
			for (E actualPoint : cluster) {
				double actualDist = actualPoint.distanceTo(toExam);
				
				if(actualDist < dist){
					dist = actualDist;
					closest = actualPoint;
				}
			}
		}
		
		if(dist <= limit) return closest;
		
		return null;
		
		
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Distanciable<E>> void saveListToFile(List <Cluster<E>> list, File file) throws IOException{

		JSONObject obj = new JSONObject();
		
		obj.put("size", list.size());
		
		int index = 0;
		
		for (Cluster<E> cluster : list) {
			JSONArray array = new JSONArray();
			
			array = cluster.toJsonArray();
			obj.put("clusterData" + index, array);
			index ++;
			
		}
		
		FileWriter finalFile = new FileWriter(file);
		finalFile.write(obj.toJSONString());
		finalFile.flush();
		finalFile.close();
		
	}
	
	public static <E extends Distanciable<E>> List <Cluster<E>> loadListFromFile(Exportable<E> exportador, File file) throws IOException, ParseException{
		
		List<Cluster<E>> ret = new ArrayList<>();

		JSONParser parser = new JSONParser();

		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
		
		if (jsonObject.get("size") == null) throw new IOException("El archivo " + file.getAbsolutePath() + " no tiene el formato esperado");
		
		long size = (long)jsonObject.get("size");
		
		for (int i = 0; i < size; i++) {
			
			Cluster<E> toAdd = new Cluster<>(exportador);
			
			JSONArray array = (JSONArray) jsonObject.get("clusterData"+i);
			
			if (array == null) throw new IOException("El archivo " + file.getAbsolutePath() + " no tiene el formato esperado");
			
			toAdd.fromJsonArray(array);
			
			ret.add(toAdd);
			
		}
		
		return ret;
		
	}
	
	public static <E extends Distanciable<E>> MapData<E> listToMapData(List<Cluster<E>> list, Exportable<E> exportador){
		
		MapData<E> ret = new MapData<E>(exportador);
		
		for (Cluster<E> cluster : list) {
			for (E distanciable : cluster) {
				ret.addPoint(distanciable);
			}
		}
		
		return ret;
		
	}
	
	@SuppressWarnings("unchecked") //Es realmente necesario
	public JSONArray toJsonArray(){
		

		JSONArray list = new JSONArray();
		
		for(E point : points){
			list.add(exportador.toJSON(point));
		}

		return list;
	}
	
	public void fromJsonArray(JSONArray list) throws IOException{
		
		
		for (Object object : list) {

			JSONObject point = (JSONObject)object;
			this.addPoint(exportador.fromJSON(point));
			
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
	
	@Override
	public Iterator<E> iterator() {
		return points.iterator();
	}

	public void setColor(Color color) {
		this.color = color;
		
	}

	public Color getColor() {
		
		return this.color;
	}



}
