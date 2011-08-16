package org.vatvit.movielib.objects;

import java.util.ArrayList;
import java.util.Map;


/**
 * Elokuvan näyttelijää tai tuotantohenkilöä edustava luokka
 *
 */
public class MovieCast {
	private int id;
	private String actor;
	private String role;
	private int movieId;
	private String job;
	
	

	/**
	 * Palauttaa näyttelijän/tuontantohenkilön työtehtävän (näyttelijä = 'actor')
	 * @return työtehtävä
	 */
	public String getJob() {
		return job;
	}
	/**
	 * Asettaa näyttelijän/tuontantohenkilön työtehtävän (näyttelijä = 'actor')
	 * @param job työtehtävä
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * Palauttaa elokuvan id:n johon näyttelijä/tuontantohenkilö kuuluu
	 * @return elokuvan id
	 */
	public int getMovieId() {
		return movieId;
	}
	
	/**
	 * Asettaa elokuvan id:n johon näyttelijä/tuontantohenkilö kuuluu
	 * @param movieId elokuvan id
	 */
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	/**
	 * Näyttelijän tai tuotantohenkilön nimi
	 * @return nimi
	 */
	public String getActor() {
		return actor;
	}
	/**
	 * Asettaa Näyttelijän tai tuotantohenkilön nimen
	 * @param actor
	 */
	public void setActor(String actor) {
		this.actor = actor;
	}
	/**
	 * Näyttelijän rooli
	 * @return rooli
	 */
	public String getRole() {
		return role;
	}
	/**
	 * Asettaa näyttelijän roolin
	 * @param role rooli
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * Palauttaa näyttelijän tai tuotantohenkilön id:n
	 * @return näyttelijän tai tuotantohenkilön id:n
	 */
	public int getId() {
		return id;
	}
	/**
	 * Asettaa näyttelijän tai tuotantohenkilön id:n
	 * @param id näyttelijän tai tuotantohenkilön id:n
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		if(job.equalsIgnoreCase("actor")) {
			return role + " - " + actor;	
		} else {
			return job + " - " + actor;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actor == null) ? 0 : actor.hashCode());
		result = prime * result + id;
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + movieId;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieCast other = (MovieCast) obj;
		if (actor == null) {
			if (other.actor != null)
				return false;
		} else if (!actor.equals(other.actor))
			return false;
		if (id != other.id)
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		if (movieId != other.movieId)
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
	
	
	
	}
	