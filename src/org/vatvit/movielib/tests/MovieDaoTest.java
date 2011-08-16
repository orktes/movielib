package org.vatvit.movielib.tests;

import java.sql.SQLException;
import java.util.ArrayList;

import org.vatvit.movielib.dao.MovieDAO;
import org.vatvit.movielib.dao.QueryResult;
import org.vatvit.movielib.models.MovieModel.Field;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.objects.MovieSubtitle;

import junit.framework.Assert;
import junit.framework.TestCase;


public class MovieDaoTest extends TestCase { 
	private Movie movie;
	private int count;
	private int id;
	
	public MovieDaoTest(String name) {
	    super(name);
	  }

	protected void setUp() { 
		movie = new Movie();
		movie.setDescription("Kuvaus");
		movie.setDirector("Ohjaaja4");
		movie.setRating(5.5);
		movie.setSlogan("Slogan");
		movie.setTitle("Elokuva");
		movie.setTrailerUrl("http://Youtube.com/");
		movie.setYear(2010);
		movie.setLocation("elokuva.avi");       
	  }

	
	  public void testAddMovie() throws SQLException, ClassNotFoundException {
	    count = MovieDAO.countSearchMovieBy(null, null);
		
		id = MovieDAO.addMovie(movie);
		
		Assert.assertEquals(count+1, MovieDAO.countSearchMovieBy(null, null));
		
	  }
	  public void testUpdateMovie() throws Exception {
		ArrayList<Movie> result = MovieDAO.searchMovieBy(null, null, 0, 0,
				Field.YEAR, false, true, true, true).getResults();
		movie = result.get(result.size()-1);
		
		
		MovieCast cast = new MovieCast();
		cast.setActor("Maija Meikalainen");
		cast.setRole("Superman");
		cast.setJob("Actor");
		movie.getCast().add(cast);
		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());

		Assert.assertEquals(1, movie.getCast().size());
		Assert.assertEquals("Maija Meikalainen", movie.getCast().get(0).getActor());
		Assert.assertEquals("Superman", movie.getCast().get(0).getRole());
		Assert.assertEquals("Actor", movie.getCast().get(0).getJob());
		Assert.assertTrue(MovieDAO.getAllCast().size()>0);
		
		movie.getCast().remove(0);
		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());
		
		Assert.assertEquals(0, movie.getCast().size());

		MovieGenre genre = new MovieGenre();
		genre.setTitle("KAUHU");
		movie.getGenres().add(genre);
		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());
		
		Assert.assertEquals(1, movie.getGenres().size());
		Assert.assertEquals("KAUHU", movie.getGenres().get(0).getTitle());
		Assert.assertTrue(MovieDAO.getAllGenres().size()>0);
		
		
		movie.getGenres().remove(0);
		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());

		Assert.assertEquals(0, movie.getGenres().size());

		
		MovieSubtitle sub2 = new MovieSubtitle();
		sub2.setLang("fi-FI");
		sub2.setLabel("Suomi");
		sub2.setLocation("srt");
		movie.getSubtitles().add(sub2);

		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());

		Assert.assertEquals(1, movie.getSubtitles().size());
		Assert.assertEquals("Suomi", movie.getSubtitles().get(0).getLabel());
		Assert.assertEquals("fi-FI", movie.getSubtitles().get(0).getLang());
		Assert.assertEquals("srt", movie.getSubtitles().get(0).getLocation());
		
		movie.getSubtitles().remove(0);
		MovieDAO.updateMovie(movie);
		movie = MovieDAO.getMovieById(movie.getId());
		
		Assert.assertEquals(0, movie.getSubtitles().size());
		
	
	  }
	  public void testGetAllYears() throws SQLException, ClassNotFoundException {
		  Assert.assertTrue(MovieDAO.getAllYears().size()>0);
	  }
	  public void testSearchMovie() throws Exception {
		  QueryResult<Movie> result = MovieDAO.searchMovieBy(null, null, 0, 0,
					Field.YEAR, false, true, true, true);
		  
		  
		  Assert.assertTrue(result.getResults().size()>0);
		  
	  }
	  public void testDelete() throws Exception {
		  ArrayList<Movie> result = MovieDAO.searchMovieBy(null, null, 0, 0,
					Field.YEAR, false, true, true, true).getResults();
		  count = result.size()-1;
			movie = result.get(result.size()-1);
			
			
		  MovieDAO.deleteMovie(movie.getId());
		  
		  Assert.assertEquals(count, MovieDAO.countSearchMovieBy(null, null));
		  
	  }
}
