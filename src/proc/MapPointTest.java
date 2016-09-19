package proc;

import static org.junit.Assert.*;

import org.junit.Test;

public class MapPointTest {

	@Test
	public void testGetDistTo() {
		MapPoint p1 = new MapPoint(40.417, -3.703);
		MapPoint p2 = new MapPoint(40.453, -3.688);
		
		assertEquals(4.20, p1.distanceTo(p2), 0.1);//Segun google maps es 4.20Km
		assertNotEquals(6, p1.distanceTo(p2), 10E-3);
	}

}
