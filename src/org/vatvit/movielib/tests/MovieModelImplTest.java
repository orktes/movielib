package org.vatvit.movielib.tests;

import java.util.ArrayList;

import org.vatvit.movielib.models.MovieDataRetrieveException;
import org.vatvit.movielib.models.MovieException;
import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.models.MovieModelImpl;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieGenre;


import junit.framework.Assert;
import junit.framework.TestCase;

public class MovieModelImplTest extends TestCase {
	private MovieModel model;
	private ArrayList<Movie> movies;
	
	private void reloadMovies() {
		try {
			movies = model.getMovies(null, null, 0, 0, null, false, true, true, true).getResults();
		} catch (MovieDataRetrieveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void setUp() {
		model = new MovieModelImpl();
		reloadMovies();
	}

	public void testAddMovie() throws MovieException {	
		
		Movie movie = new Movie();
		movie.setTitle("Big Buck Bunny");
		movie.setDescription("Follow a day of the life of Big Buck Bunny when he meets three bullying rodents: Frank, Rinky, and Gamera. The rodents amuse themselves by harassing helpless creatures by throwing fruits, nuts and rocks at them. After the deaths of two of Bunny's favorite butterflies, and an offensive attack on Bunny himself, Bunny sets aside his gentle nature and orchestrates a complex plan for revenge.");
		movie.setDirector("Sacha Goedegebure");
		movie.setRating(7.8);
		movie.setSlogan("");
		movie.setYear(2008);
		movie.setTrailerUrl("http://www.youtube.com/watch?v=IBTE-RoMsvw");
		movie.setGenres(new ArrayList<MovieGenre>());
		MovieGenre genre = new MovieGenre();
		genre.setTitle("Animation");
		movie.getGenres().add(genre);
		movie.setLocation("test.avi");
		int count = movies.size();
		Assert.assertTrue(model.saveMovie(movie, null, null)>0);
		reloadMovies();
		Assert.assertEquals(count+1, movies.size());	
		
	}
	public void testGetMovie() throws MovieException {	
		
		Movie movie = model.getMovie(movies.get(movies.size()-1).getId());
		
		Assert.assertTrue(movie.equals(movies.get(movies.size()-1)));
		
	}
	public void testMovieDelete() throws MovieException {	
		
		Movie movie = model.getMovie(movies.get(movies.size()-1).getId());
		
		int count = movies.size();
		model.deleteMovie(movie);
		reloadMovies();
		Assert.assertEquals(count-1, movies.size());	
		
	}
}
