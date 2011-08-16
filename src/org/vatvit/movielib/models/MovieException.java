package org.vatvit.movielib.models;

import org.vatvit.movielib.objects.Movie;

/**
 * Yleinen, elokuviin liittyvä Exception -luokka
 *
 */
public class MovieException extends Exception {
	
	private Movie movie;

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public MovieException(String message) {
		super(message);
	}
	
}
