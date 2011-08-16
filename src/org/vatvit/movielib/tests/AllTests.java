package org.vatvit.movielib.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(MovieModelImplTest.class);
		suite.addTestSuite(DatabaseConnectionTest.class);
		suite.addTestSuite(MovieDaoTest.class);
		suite.addTestSuite(ToolTest.class);
		//$JUnit-END$
		return suite;
	}

}
