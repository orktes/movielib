package org.vatvit.movielib.dao;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import org.vatvit.movielib.database.DatabaseConnection;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.objects.MovieSubtitle;
import org.vatvit.movielib.models.MovieModel.Field;


public class MovieDAO {
	

	public static int addMovie(Movie movie) throws SQLException,
			ClassNotFoundException {
		int id = 0;
		movie.setAdded(new Date());
		Connection conn = org.vatvit.movielib.database.DatabaseConnection.getDBConn();
		String query = "INSERT INTO movies (title, description, slogan, director, trailer_url, year, rating, added, location) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement prep = conn.prepareStatement(query);
		prep.setString(1, movie.getTitle());
		prep.setString(2, movie.getDescription());
		prep.setString(3, movie.getSlogan());
		prep.setString(4, movie.getDirector());
		prep.setString(5, movie.getTrailerUrl());
		prep.setInt(6, movie.getYear());
		prep.setDouble(7, movie.getRating());
		prep.setLong(8, movie.getAdded().getTime());
		prep.setString(9, movie.getLocation());

		prep.executeUpdate();
		ResultSet keys = prep.getGeneratedKeys();
		if (keys.next()) {
			id = keys.getInt(1);
		}
		keys.close();
		prep.close();

		movie.setId(id);
		if (movie.getCast() != null) {
			for (MovieCast cast : movie.getCast()) {
				cast.setMovieId(id);
				addMovieCast(cast);
			}
		} else {
			movie.setCast(new ArrayList<MovieCast>());
		}
		if (movie.getGenres() != null) {
			for (MovieGenre genre : movie.getGenres()) {
				genre.setMovieId(id);
				addMovieGenre(genre);
			}
		} else {
			movie.setGenres(new ArrayList<MovieGenre>());
		}
		
		if (movie.getSubtitles() != null) {
			for (MovieSubtitle sub : movie.getSubtitles()) {
				sub.setMovieId(id);
				addMovieSubtitle(sub);
			}
		} else {
			movie.setSubtitles(new ArrayList<MovieSubtitle>());
		}
		return id;
	}

	private static int addMovieCast(MovieCast cast) throws SQLException,
			ClassNotFoundException {
		int id = 0;
		Connection conn = DatabaseConnection.getDBConn();
		String query = "INSERT INTO movies_cast (movies_id, actor, role, job) VALUES (?, ?, ?, ?)";
		PreparedStatement prep = conn.prepareStatement(query);
		prep.setInt(1, cast.getMovieId());
		prep.setString(2, cast.getActor());
		prep.setString(3, cast.getRole());
		prep.setString(4, cast.getJob());
		prep.executeUpdate();
		ResultSet keys = prep.getGeneratedKeys();
		if (keys.next()) {
			id = keys.getInt(1);
		}
		keys.close();
		prep.close();

		cast.setId(id);

		return id;
	}

	private static int addMovieSubtitle(MovieSubtitle sub) throws SQLException,
			ClassNotFoundException {
		int id = 0;
		Connection conn = DatabaseConnection.getDBConn();
		String query = "INSERT INTO movies_subtitles (movies_id, lang, label, location) VALUES (?, ?, ?, ?)";
		PreparedStatement prep = conn.prepareStatement(query);
		prep.setInt(1, sub.getMovieId());
		prep.setString(2, sub.getLang());
		prep.setString(3, sub.getLabel());
		prep.setString(4, sub.getLocation());

		prep.executeUpdate();
		ResultSet keys = prep.getGeneratedKeys();
		if (keys.next()) {
			id = keys.getInt(1);
		}
		keys.close();
		prep.close();

		sub.setId(id);

		return id;
	}

	private static int addMovieGenre(MovieGenre genre) throws SQLException,
			ClassNotFoundException {
		int id = 0;
		Connection conn = DatabaseConnection.getDBConn();
		String query = "INSERT INTO movies_genres (movies_id, title) VALUES (?, ?)";
		PreparedStatement prep = conn.prepareStatement(query);
		prep.setInt(1, genre.getMovieId());
		prep.setString(2, genre.getTitle());

		prep.executeUpdate();
		ResultSet keys = prep.getGeneratedKeys();
		if (keys.next()) {
			id = keys.getInt(1);
		}
		keys.close();
		prep.close();

		genre.setId(id);

		return id;
	}


	public static int countSearchMovieBy(Field searchField, String search)
			throws SQLException, ClassNotFoundException {
		Connection conn = DatabaseConnection.getDBConn();
		String query = "SELECT count(*) as maara FROM (SELECT * FROM movies"
				+ ((searchField != null && searchField == Field.GENRE) ? ", movies_genres"
						: "")
				+ ((searchField != null && searchField == Field.CAST) ? ", movies_cast"
						: "");
		if (searchField != null && search != null && search.length() > 0) {
			switch (searchField) {
			case CAST:
				query += " WHERE movies_cast.actor LIKE ? AND movies.id = movies_cast.movies_id";
				break;
			case GENRE:
				query += " WHERE movies_genres.title LIKE ? AND movies.id = movies_genres.movies_id";
				break;
			default:
				query += " WHERE " + searchField.name() + " LIKE ?";
				break;
			}
		}
		query += " GROUP BY movies.id) as elokuvat";
		PreparedStatement prep = conn.prepareStatement(query);
		if (searchField != null && search != null && search.length() > 0) {
			prep.setString(1, "%" + search + "%");
		}
		ResultSet rs = prep.executeQuery();

		int count = 0;
		while (rs.next()) {
			count = rs.getInt("maara");
		}

		rs.close();
		prep.close();
		return count;
	}

	public static boolean deleteMovie(int id) throws SQLException,
			ClassNotFoundException {
		Movie movie = MovieDAO.getMovieById(id);

		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		int effect = stat.executeUpdate("DELETE FROM movies WHERE id = "
				+ movie.getId());
		stat.close();
		if (movie.getCast() != null) {
			for (MovieCast cast : movie.getCast()) {
				MovieDAO.deleteMovieCast(cast);
			}
		}
		if (movie.getGenres() != null) {
			for (MovieGenre genre : movie.getGenres()) {
				MovieDAO.deleteMovieGenre(genre);
			}
		}
	
		if (movie.getSubtitles() != null) {
			for (MovieSubtitle sub : movie.getSubtitles()) {
				MovieDAO.deleteMovieSubtitle(sub);
			}
		}
		if (effect == 0) {
			return false;
		} else {
			return true;
		}

	}

	private static boolean deleteMovieCast(int id) throws SQLException,
			ClassNotFoundException {
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		int effect = stat.executeUpdate("DELETE FROM movies_cast WHERE id = "
				+ id);
		stat.close();
		if (effect == 0) {
			return false;
		} else {
			return true;
		}

	}

	private static boolean deleteMovieCast(MovieCast cast) throws SQLException,
			ClassNotFoundException {
		return deleteMovieCast(cast.getId());

	}

	private static boolean deleteMovieSubtitle(int id) throws SQLException,
			ClassNotFoundException {
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		int effect = stat
				.executeUpdate("DELETE FROM movies_subtitles WHERE id = " + id);
		stat.close();
		if (effect == 0) {
			return false;
		} else {
			return true;
		}

	}

	private static boolean deleteMovieSubtitle(MovieSubtitle sub)
			throws SQLException, ClassNotFoundException {
		return deleteMovieSubtitle(sub.getId());

	}

	private static boolean deleteMovieGenre(int id) throws SQLException,
			ClassNotFoundException {
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		int effect = stat.executeUpdate("DELETE FROM movies_genres WHERE id = "
				+ id);
		stat.close();
		if (effect == 0) {
			return false;
		} else {
			return true;
		}

	}

	private static boolean deleteMovieGenre(MovieGenre genre)
			throws SQLException, ClassNotFoundException {
		return deleteMovieGenre(genre.getId());

	}


	public static ArrayList<String> getAllCast() throws SQLException,
			ClassNotFoundException {
		ArrayList<String> genres = new ArrayList<String>();
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("SELECT actor FROM movies_cast GROUP BY actor ORDER BY actor");
		while (rs.next()) {
			if(rs.getString("actor").length()>0) {
				genres.add(rs.getString("actor"));
			}
		}
		rs.close();
		stat.close();

		return genres;

	}

	public static ArrayList<String> getAllDirectors() throws SQLException,
			ClassNotFoundException {
		ArrayList<String> genres = new ArrayList<String>();
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("SELECT director FROM movies GROUP BY director ORDER BY director");
		while (rs.next()) {
			if(rs.getString("director").length()>0) {
				genres.add(rs.getString("director"));
			}
		}
		rs.close();
		stat.close();

		return genres;

	}

	public static ArrayList<String> getAllGenres() throws SQLException,
			ClassNotFoundException {
		ArrayList<String> genres = new ArrayList<String>();
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("SELECT title FROM movies_genres GROUP BY title ORDER BY title");
		while (rs.next()) {
			if(rs.getString("title").length()>0) {
				genres.add(rs.getString("title"));
			}

		}
		rs.close();
		stat.close();

		return genres;

	}

	public static QueryResult<Movie> getAllMovies(Field orderBy, boolean desc)
			throws Exception {
		return searchMovieBy(null, null, 0, 0, null, desc, false, false,
				false);
	}

	public static ArrayList<String> getAllYears() throws SQLException,
			ClassNotFoundException {
		ArrayList<String> genres = new ArrayList<String>();
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("SELECT year FROM movies GROUP BY year ORDER BY year");
		while (rs.next()) {
			genres.add(rs.getString("year"));
		}
		rs.close();
		stat.close();

		return genres;

	}

	private static ArrayList<MovieCast> getCastForMovieId(int id)
			throws SQLException, ClassNotFoundException {
		ArrayList<MovieCast> cast = new ArrayList<MovieCast>();
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("SELECT * FROM movies_cast WHERE movies_id = "
						+ id + " ORDER BY actor");
		while (rs.next()) {
			cast.add(resultSetToMovieCast(rs));
		}
		rs.close();
		stat.close();
		return cast;
	}

	private static ArrayList<MovieSubtitle> getSubtitlesForMovieId(int id)
			throws SQLException, ClassNotFoundException {
		ArrayList<MovieSubtitle> subs = new ArrayList<MovieSubtitle>();
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("SELECT * FROM movies_subtitles WHERE movies_id = "
						+ id + " ORDER BY label");
		while (rs.next()) {
			subs.add(resultSetToMovieSubtitle(rs));
		}
		rs.close();
		stat.close();
		return subs;
	}

	private static ArrayList<MovieGenre> getGenresForMovieId(int id)
			throws SQLException, ClassNotFoundException {
		ArrayList<MovieGenre> genres = new ArrayList<MovieGenre>();
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat
				.executeQuery("SELECT * FROM movies_genres WHERE movies_id = "
						+ id + " ORDER BY title");
		while (rs.next()) {
			genres.add(resultSetToMovieGenre(rs));
		}
		rs.close();
		stat.close();
		return genres;
	}

	public static Movie getMovieById(int id) throws SQLException,
			ClassNotFoundException {
		Movie movie = null;
		Connection conn = DatabaseConnection.getDBConn();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM movies WHERE id = "
				+ id);
		if (rs.next()) {
			movie = resultSetToMovie(rs);
			movie.setGenres(getGenresForMovieId(id));
			movie.setCast(getCastForMovieId(id));
			movie.setSubtitles(getSubtitlesForMovieId(id));
		}
		rs.close();
		stat.close();
		return movie;
	}


	public static void main(String[] args) throws Exception {
		// TESTIT
		int count = MovieDAO.countSearchMovieBy(null, null);
		System.out.println("Tietokannassa on " + count + " elokuvaa");

		Movie movie = new Movie();
		movie.setDescription("Kuvaus");
		movie.setDirector("Ohjaaja4");
		movie.setRating(5.5);
		movie.setSlogan("Slogan");
		movie.setTitle("Elokuva");
		movie.setTrailerUrl("http://Youtube.com/");
		movie.setYear(2010);
		movie.setLocation("elokuva.avi");
		movie.setSubtitles(new ArrayList<MovieSubtitle>());
		MovieSubtitle sub = new MovieSubtitle();
		sub.setLabel("Suomi");
		sub.setLang("fi-FI");
		sub.setLocation("test.srt");
		movie.getSubtitles().add(sub);



		System.out.println("Lis�t��n uusi elokuva");
		int id = MovieDAO.addMovie(movie);
		System.out.println("- " + movie.toString());
		count = MovieDAO.countSearchMovieBy(null, null);
		System.out.println("Tietokannassa on " + count + " elokuvaa");

		// CAST
		System.out.println("Lis�tyll� elokuvalla on " + movie.getCast().size()
				+ " kpl \"n�yttelij�it�\" cast:ssa");
		System.out.println("Lis�t��n yksi cast j�sen lis��");

		MovieCast cast = new MovieCast();
		cast.setActor("Maija Meik�l�inen");
		cast.setRole("Superman");
		movie.getCast().add(cast);

		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());

		System.out.println("Lis�tyll� elokuvalla on nyt "
				+ movie.getCast().size() + " kpl \"n�yttelij�it�\" cast:ssa");
		System.out.println("- " + movie.toString());
		System.out.println("T�ss� kaikki kannassa olevat n�yttelij�t");
		ArrayList<String> actors = MovieDAO.getAllCast();
		for (String actor : actors) {
			System.out.println("- " + actor);
		}

		System.out.println("Poisteaan n�yttelij� "
				+ movie.getCast().get(0).getActor());
		movie.getCast().remove(0);
		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());
		System.out.println("Lis�tyll� elokuvalla on nyt "
				+ movie.getCast().size() + " kpl \"n�yttelij�it�\" cast:ssa");
		System.out.println("- " + movie.toString());
		// GENRE
		System.out.println("Lis�tyll� elokuvalla on "
				+ movie.getGenres().size() + " genre�");
		System.out.println("Lis�t��n yksi genre lis��");

		MovieGenre genre = new MovieGenre();
		genre.setTitle("KAUHU");

		movie.getGenres().add(genre);

		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());

		System.out.println("Lis�tyll� elokuvalla on nyt "
				+ movie.getGenres().size() + " genre�");
		System.out.println("- " + movie.toString());
		System.out.println("T�ss� kaikki kannassa olevat genret");
		ArrayList<String> genret = MovieDAO.getAllGenres();
		for (String gen : genret) {
			System.out.println("- " + gen);
		}

		System.out.println("Poisteaan  genre "
				+ movie.getGenres().get(0).getTitle());
		movie.getGenres().remove(0);
		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());

		System.out.println("Lis�tyll� elokuvalla on nyt "
				+ movie.getGenres().size() + " genre�");
		System.out.println("- " + movie.toString());

		

		// TEKSTITTYKSET
		System.out.println("Lis�tyll� elokuvalla on nyt "
				+ movie.getSubtitles().size() + " kpl tekstityst�");
		System.out.println("Lis�t��n yksi tekstitys lis��");

		MovieSubtitle sub2 = new MovieSubtitle();
		sub2.setLang("fi-FI");
		sub2.setLabel("Suomi");
		sub2.setLocation("srt");
		movie.getSubtitles().add(sub2);

		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());

		System.out.println("Lis�tyll� elokuvalla on nyt "
				+ movie.getSubtitles().size() + " kpl tekstityst�");
		System.out.println("- " + movie.toString());
		System.out.println("Poistetaan tekstitys");
		movie.getSubtitles().remove(0);
		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());
		System.out.println("Lis�tyll� elokuvalla on nyt "
				+ movie.getSubtitles().size() + " kpl tekstityst�");
		System.out.println("- " + movie.toString());

		System.out
				.println("Haetaan elokuvat ja j�rjestet��n ilmestymis vuoden mukaan");
		QueryResult<Movie> result = MovieDAO.searchMovieBy(null, null, 0, 0,
				Field.YEAR, false, true, true, true);
		System.out.println("Haku palautti " + result.getResults().size()
				+ " vastausta. Tulostetaan elokuvat.");
		for (Movie mov : result.getResults()) {
			mov.setDescription("Follow a day of the life of Big Buck Bunny when he meets three bullying rodents: Frank, Rinky, and Gamera. The rodents amuse themselves by harassing helpless creatures by throwing fruits, nuts and rocks at them. After the deaths of two of Bunny's favorite butterflies, and an offensive attack on Bunny himself, Bunny sets aside his gentle nature and orchestrates a complex plan for revenge.");
			//if(mov.getId()<30)MovieDAO.updateMovie(mov);
			
			System.out.println(mov.getId()+" - " + mov.toString());
		}

		System.out.println("Poistetaan �skett�in lis�tty elokuva");
		MovieDAO.deleteMovie(id);

		count = MovieDAO.countSearchMovieBy(null, null);
		System.out.println("Tietokannassa on " + count + " elokuvaa");

	}

	private static Movie resultSetToMovie(ResultSet set) throws SQLException {
		Movie movie = new Movie();
		movie.setAdded(new Date(set.getLong("added")));
		movie.setDescription(set.getString("description"));
		movie.setDirector(set.getString("director"));
		movie.setId((int) set.getInt("id"));
		movie.setRating(set.getFloat("rating"));
		movie.setSlogan(set.getString("slogan"));
		movie.setTitle(set.getString("title"));
		movie.setTrailerUrl(set.getString("trailer_url"));
		movie.setYear((int) set.getInt("year"));
		movie.setLocation(set.getString("location"));
		
		return movie;
	}

	private static MovieCast resultSetToMovieCast(ResultSet set)
			throws SQLException {
		MovieCast cast = new MovieCast();
		cast.setActor(set.getString("actor"));
		cast.setId(set.getInt("id"));
		cast.setMovieId(set.getInt("movies_id"));
		cast.setRole(set.getString("role"));
		cast.setJob(set.getString("job"));
		return cast;

	}

	private static MovieSubtitle resultSetToMovieSubtitle(ResultSet set)
			throws SQLException {
		MovieSubtitle sub = new MovieSubtitle();
		sub.setLang(set.getString("lang"));
		sub.setId(set.getInt("id"));
		sub.setMovieId(set.getInt("movies_id"));
		sub.setLabel(set.getString("label"));
		sub.setLocation(set.getString("location"));
		return sub;

	}

	private static MovieGenre resultSetToMovieGenre(ResultSet set)
			throws SQLException {
		MovieGenre genre = new MovieGenre();
		genre.setId(set.getInt("id"));
		genre.setMovieId(set.getInt("movies_id"));
		genre.setTitle(set.getString("title"));

		return genre;

	}


	public static QueryResult<Movie> searchMovieBy(Field searchField,
			String search, int limit, int page, Field orderBy, boolean desc,
			boolean includeCast, boolean includeGenres,
			boolean includeSubtitles)
			throws Exception {

		if (orderBy != null
				&& ((orderBy == Field.CAST && searchField != Field.CAST) || (orderBy == Field.GENRE && searchField != Field.GENRE))) {

			throw new Exception(orderBy.name()
					+ " ei movies kentt� joten haku kent�n tulee olla my�s "
					+ orderBy.name());
		}

		QueryResult<Movie> result = new QueryResult<Movie>();
		if (searchField != null) {
			result.setField(searchField.name());
		}
		result.setSearch(search);
		result.setLimit(limit);
		result.setPage(page);
		if (orderBy != null) {
			result.setOrderBy(orderBy.name());
		}
		result.setDesc(desc);

		result.setTotal(countSearchMovieBy(searchField, search));

		Connection conn = DatabaseConnection.getDBConn();

		String query = "SELECT movies.* FROM movies"
				+ ((searchField != null && searchField == Field.GENRE) ? ", movies_genres"
						: "")
				+ ((searchField != null && searchField == Field.CAST) ? ", movies_cast"
						: "");
		if (searchField != null && search != null && search.length() > 0) {
			switch (searchField) {
			case CAST:
				query += " WHERE movies_cast.actor LIKE ? AND movies.id = movies_cast.movies_id";
				break;
			case GENRE:
				query += " WHERE movies_genres.title LIKE ? AND movies.id = movies_genres.movies_id";
				break;
			default:
				query += " WHERE " + searchField.name() + " LIKE ?";
				break;
			}
		}

		if (searchField != null) {
			switch (searchField) {
			case CAST:
				query += " GROUP BY movies.id";
				break;
			case GENRE:
				query += " GROUP BY movies.id";
				break;
			}
		}
		if (orderBy != null) {
			query += " ORDER BY " + orderBy.name() + (desc ? " DESC" : "");
		}
		if (limit > 0) {
			int start = 0;
			if (page > 0)
				start = limit * (page - 1);
			query += " LIMIT " + start + ", " + limit;
		}

		PreparedStatement prep = conn.prepareStatement(query);
		if (searchField != null && search != null && search.length() > 0) {
			prep.setString(1, "%" + search + "%");
		}

		ResultSet rs = prep.executeQuery();

		ArrayList<Movie> movies = new ArrayList<Movie>();
		while (rs.next()) {

			Movie movie = resultSetToMovie(rs);
			if (includeGenres)
				movie.setGenres(getGenresForMovieId(movie.getId()));
			if (includeCast)
				movie.setCast(getCastForMovieId(movie.getId()));
			if (includeSubtitles)
				movie.setSubtitles(getSubtitlesForMovieId(movie.getId()));
			movies.add(movie);
		}
		result.setResults(movies);

		rs.close();
		prep.close();

		return result;
	}

	public static QueryResult<Movie> searchMoviesBy(Field searchField,
			String search, Field orderBy) throws Exception {
		return searchMovieBy(searchField, search, 0, 0, orderBy, false, false,
				false, false);
	}

	public static boolean updateMovie(Movie movie) throws SQLException,
			ClassNotFoundException {
		Movie oldMovie = MovieDAO.getMovieById(movie.getId());
		// TARKISTETAAN ONKO TIETOJA MUUTETTU = eroavatko objektien sis�ll�t
		if (!movie.equals(oldMovie)) {

			Connection conn = DatabaseConnection.getDBConn();
			PreparedStatement prep = conn
					.prepareStatement("UPDATE movies SET TITLE = ?, DESCRIPTION = ?, SLOGAN = ?, DIRECTOR = ?, TRAILER_URL = ?, YEAR = ?, RATING = ?, LOCATION = ? WHERE ID = ?");
			prep.setString(1, movie.getTitle());
			prep.setString(2, movie.getDescription());
			prep.setString(3, movie.getSlogan());
			prep.setString(4, movie.getDirector());
			prep.setString(5, movie.getTrailerUrl());
			prep.setInt(6, movie.getYear());
			prep.setDouble(7, movie.getRating());
			prep.setString(8, movie.getLocation());
			prep.setInt(9, movie.getId());

			int effect = prep.executeUpdate();

			if (movie.getCast() != null) {
				// SUORITETAAN CAST LIS�YKSET JA MUUTOKSET
				for (MovieCast cast : movie.getCast()) {
					// LIS�T��N JOS ID = 0
					if (cast.getId() == 0) {
						cast.setMovieId(movie.getId());
						MovieDAO.addMovieCast(cast);
						// MUUTOIN TARKSITETAAN ONKO TIEDOT (actor, role)
						// MUUTTUNEET
					} else if (oldMovie.getCast() != null) {
						for (MovieCast oldCast : oldMovie.getCast()) {
							if (cast.getId() == oldCast.getId()
									&& !cast.equals(oldCast)) {
								MovieDAO.updateMovieCast(cast);
								break;
							}
						}
					}
				}

				// SUORITETAAN MAHDOLLISET POISTOT
				if (oldMovie.getCast() != null) {
					for (MovieCast oldCast : oldMovie.getCast()) {
						boolean found = false;
						for (MovieCast cast : movie.getCast()) {
							if (cast.getId() == oldCast.getId()) {
								found = true;
								break;
							}
						}
						// Jos ei l�ydetty uusista tiedoista pointetaan
						// tietokannasta
						if (!found) {
							MovieDAO.deleteMovieCast(oldCast);
						}

					}
				}
			}

			if (movie.getGenres() != null) {
				// SUORITETAAN GENRE LIS�YKSET JA MUUTOKSET
				for (MovieGenre genre : movie.getGenres()) {
					// LIS�T��N JOS ID = 0
					if (genre.getId() == 0) {
						genre.setMovieId(movie.getId());
						MovieDAO.addMovieGenre(genre);
						// MUUTOIN TARKSITETAAN ONKO TIEDOT (title) MUUTTUNEET
					} else if (oldMovie.getGenres() != null) {
						for (MovieGenre oldGenre : oldMovie.getGenres()) {
							if (genre.getId() == oldGenre.getId()
									&& !genre.equals(oldGenre)) {
								MovieDAO.updateMovieGenre(genre);
								break;
							}
						}
					}
				}

				// SUORITETAAN MAHDOLLISET POISTOT
				if (oldMovie.getGenres() != null) {
					for (MovieGenre oldGenre : oldMovie.getGenres()) {
						boolean found = false;
						for (MovieGenre genre : movie.getGenres()) {
							if (genre.getId() == oldGenre.getId()) {
								found = true;
								break;
							}
						}
						// Jos ei l�ydetty uusista tiedoista pointetaan
						// tietokannasta
						if (!found) {
							MovieDAO.deleteMovieGenre(oldGenre);
						}

					}
				}
			}
			

			
			if (movie.getSubtitles() != null) {
				// SUORITETAAN SUBTITLE LIS�YKSET JA MUUTOKSET
				for (MovieSubtitle sub : movie.getSubtitles()) {
					// LIS�T��N JOS ID = 0
					if (sub.getId() == 0) {
						sub.setMovieId(movie.getId());
						MovieDAO.addMovieSubtitle(sub);
						// MUUTOIN TARKSITETAAN ONKO TIEDOT (actor, role)
						// MUUTTUNEET
					} else if (oldMovie.getSubtitles() != null) {
						for (MovieSubtitle oldSub : oldMovie.getSubtitles()) {
							if (sub.getId() == oldSub.getId()
									&& !sub.equals(oldSub)) {
								MovieDAO.updateMovieSubtitle(sub);
								break;
							}
						}
					}
				}

				// SUORITETAAN MAHDOLLISET POISTOT
				if (oldMovie.getSubtitles() != null) {
					for (MovieSubtitle oldSub : oldMovie.getSubtitles()) {
						boolean found = false;
						for (MovieSubtitle sub : movie.getSubtitles()) {
							if (sub.getId() == oldSub.getId()) {
								found = true;
								break;
							}
						}
						// Jos ei l�ydetty uusista tiedoista pointetaan
						// tietokannasta
						if (!found) {
							MovieDAO.deleteMovieSubtitle(oldSub);
						}

					}
				}
			}

	
			if (effect == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	private static boolean updateMovieCast(MovieCast cast) throws SQLException,
			ClassNotFoundException {
		Connection conn = DatabaseConnection.getDBConn();
		String query = "UPDATE movies_cast SET actor = ?, role = ?, job = ? WHERE id = ?";
		PreparedStatement prep = conn.prepareStatement(query);

		prep.setString(1, cast.getActor());
		prep.setString(2, cast.getRole());
		prep.setString(3, cast.getJob());
		prep.setInt(4, cast.getId());

		int effect = prep.executeUpdate();
		if (effect == 0) {
			return false;
		} else {
			return true;
		}

	}

	private static boolean updateMovieSubtitle(MovieSubtitle sub)
			throws SQLException, ClassNotFoundException {
		Connection conn = DatabaseConnection.getDBConn();
		String query = "UPDATE movies_subtitles SET lang = ?, label = ?, location = ? WHERE id = ?";
		PreparedStatement prep = conn.prepareStatement(query);

		prep.setString(1, sub.getLang());
		prep.setString(2, sub.getLabel());
		prep.setString(3, sub.getLocation());
		prep.setInt(4, sub.getId());

		int effect = prep.executeUpdate();
		if (effect == 0) {
			return false;
		} else {
			return true;
		}

	}

	private static boolean updateMovieGenre(MovieGenre genre)
			throws SQLException, ClassNotFoundException {
		Connection conn = DatabaseConnection.getDBConn();
		String query = "UPDATE movies_genres SET title = ? WHERE id = ?";
		PreparedStatement prep = conn.prepareStatement(query);

		prep.setString(1, genre.getTitle());
		prep.setInt(2, genre.getId());

		int effect = prep.executeUpdate();
		if (effect == 0) {
			return false;
		} else {
			return true;
		}

	}

}