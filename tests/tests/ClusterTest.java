package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import map.Cluster;

public class ClusterTest {
	
	GraphableInteger a1 = new GraphableInteger(10);
	GraphableInteger a2 = new GraphableInteger(8);
	GraphableInteger a3 = new GraphableInteger(2);
	GraphableInteger a4 = new GraphableInteger(4);
	GraphableInteger a5 = new GraphableInteger(1);
	GraphableInteger a6 = new GraphableInteger(13);
	
	GraphableInteger.Exportador exportador = new GraphableInteger.Exportador();

	
	List<Cluster<GraphableInteger>> getList(){
		List <Cluster<GraphableInteger>> ret = new ArrayList<>();
		
		Cluster<GraphableInteger>c1 = new Cluster<>(exportador);
		
		c1.addPoint(a1);
		c1.addPoint(a2);
		
		ret.add(c1);
		
		Cluster<GraphableInteger>c2 = new Cluster<>(exportador);
		
		c2.addPoint(a3);
		c2.addPoint(a4);
		c2.addPoint(a5);
		
		ret.add(c2);
		
		return ret;
		
	}
	
	Cluster<GraphableInteger> getCluster(){
		
		Cluster<GraphableInteger>ret = new Cluster<>(exportador);
		
		ret.addPoint(a3);
		ret.addPoint(a4);
		ret.addPoint(a5);
		
		return ret;
		
	}

	@Test
	public void saveAndLoadListTest() {
		List<Cluster<GraphableInteger>> list = getList();
		
		File file = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"clustersTestOut.json");
		
		try {
			Cluster.saveListToFile(list, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(file.exists());
		

		List<Cluster<GraphableInteger>> expected = null;
		
		try {
			expected = Cluster.loadListFromFile(exportador, file);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		assertNotEquals(null, expected);
		
		assertEquals(expected, list);
		
	}
	
	@Test(expected = ParseException.class)
	public void LoadNotJsonTest() throws IOException, ParseException {
		
		File file = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"notAJson.txt");
		
		@SuppressWarnings("unused")//Para hacer saltar la excepcion
		List<Cluster<GraphableInteger>> list = Cluster.loadListFromFile(exportador, file);
		
		
	}
	
	@Test(expected = IOException.class)
	public void LoadOtherJsonTest() throws IOException, ParseException {
		
		File file = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"otherJson.json");
		
		@SuppressWarnings("unused")//Para hacer saltar la excepcion
		List<Cluster<GraphableInteger>> list = Cluster.loadListFromFile(exportador, file);
		
		
	}
	
	@Test
	public void toJsonArrayTest(){
		
		Cluster<GraphableInteger> cluster = getCluster();
		
		JSONArray array = cluster.toJsonArray();
		
		JSONArray expected = generateJSON(new int[] {1,4,2});
		
		assertEquals(3, array.size());
		
		
		for (Object value : expected) {
			assertTrue(array.contains(value));
		}
		
		
		
	}
	
	@Test
	public void fromJsonArrayTest(){
		
		Cluster<GraphableInteger> cluster = new Cluster<>(exportador);
		
		JSONArray arrayGenerator = generateJSON(new int[] {1,4,2});
		
		try {
			cluster.fromJsonArray(arrayGenerator);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Cluster<GraphableInteger> expected = getCluster(); 
		
		assertEquals(3, cluster.size());
		
		assertEquals(expected, cluster);
		
		
	}
	
	@Test
	public void getBelongsIndex(){
		List<Cluster<GraphableInteger>> list = getList();
		
		assertEquals(0, Cluster.getBelongsIndex(list, a1));
		assertEquals(1, Cluster.getBelongsIndex(list, a5));
		assertEquals(-1, Cluster.getBelongsIndex(list, a6));
		
	}
	
	@Test
	public void getClosestToListTest(){
		List<Cluster<GraphableInteger>> list = getList();
		
		GraphableInteger value11 = new GraphableInteger(11);
		
		assertEquals(a1, Cluster.getClosestToList(list, value11, 4)); //El mas cercano al 11 es el 10
		
		
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray generateJSON(int [] valuesToAdd){
		
		JSONArray ret = new JSONArray();
		
		for (int value : valuesToAdd) {
			
			JSONObject objectToAdd = new JSONObject();
			objectToAdd.put("value", value);
			ret.add(objectToAdd);
		}
		
		return ret;
	}

}
