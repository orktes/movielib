package org.vatvit.movielib.models;

/**
 * Elokuvatietojen haussa taphtuvia virheitä edustava Exception -luokka
 */
public class MovieDataRetrieveException extends MovieException {

	public MovieDataRetrieveException(String message) {
		super(message);
	}

}
