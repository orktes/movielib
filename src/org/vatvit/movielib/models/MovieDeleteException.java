package org.vatvit.movielib.models;

/**
 * Elokuvatietojen poistossa taphtuvia virheitä edustava Exception -luokka
 */
public class MovieDeleteException extends MovieException {

	public MovieDeleteException(String message) {
		super(message);
		
	}


}
