package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@Test
	public void saveListToFileTest() {
		List<Cluster<GraphableInteger>> list= getList();
		
		File file = new File("tests" + File.separatorChar + "JsonTests"+File.separatorChar+"clustersTestOut.json");
		
		try {
			Cluster.saveListToFile(list, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
