package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AgmSolverTest.class, MapDataTest.class, MapGraphTest.class, MapPointTest.class, ClusterSolverTest.class, MapEdgeTest.class})
public class AllTests {

}
