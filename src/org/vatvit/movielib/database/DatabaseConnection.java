package org.vatvit.movielib.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {
	private static final String DATABASE = "database.sqlite";
	private static Connection conn = null;
	private static final String createMovies = "CREATE TABLE movies (id INTEGER PRIMARY KEY, title TEXT NOT NULL, description TEXT NOT NULL, slogan TEXT NOT NULL, director TEXT NOT NULL, trailer_url TEXT NOT NULL, location TEXT NOT NULL, year INTEGER NOT NULL, rating DECIMAL NOT NULL, added INTEGER NOT NULL)";
	private static final String createMovieGenres = "CREATE TABLE movies_genres (id INTEGER  PRIMARY KEY, movies_id INTEGER, title TEXT NOT NULL)";
	private static final String createMovieCast = "CREATE TABLE movies_cast (id INTEGER  PRIMARY KEY, movies_id INTEGER, actor TEXT NOT NULL, job TEXT NOT NULL, role TEXT NOT NULL)";
	private static final String createMovieSubtitle = "CREATE TABLE movies_subtitles (id INTEGER  PRIMARY KEY, movies_id INTEGER, lang TEXT NOT NULL, label TEXT NOT NULL, location TEXT NOT NULL)";

	
	private static final String[] createTables = new String[] { createMovies, createMovieSubtitle,
			createMovieGenres, createMovieCast };

	private static Connection createDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
		Statement stat = conn.createStatement();
		
		for(String query : createTables) {
			stat.executeUpdate(query);
		}
		stat.close();
		
		return conn;
	}

	public static Connection getDBConn() throws SQLException,
			ClassNotFoundException {
		
		if (conn == null || conn.isClosed()) {
			File dbFile = new File(DATABASE);
			if (!dbFile.exists()) {
				conn = createDatabase();
			} else {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE);
			}

			

		}
		return conn;
	}

	public static void main(String[] args) {
		try {
			DatabaseConnection.getDBConn();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
