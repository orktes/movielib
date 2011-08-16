package org.vatvit.movielib.tests;

import java.sql.Connection;
import java.sql.SQLException;

import org.vatvit.movielib.database.DatabaseConnection;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DatabaseConnectionTest extends TestCase {
	public void testDatabaseConnectionCreation() throws SQLException, ClassNotFoundException {
		Connection conn = DatabaseConnection.getDBConn();
		Assert.assertFalse(conn.isClosed());
		conn.close();
		Assert.assertTrue(conn.isClosed());
	}
}
