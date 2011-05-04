package org.vatvit.movielib.objects;

import java.util.ArrayList;
import java.util.Map;


public class MovieCast {
	private int id;
	private String actor;
	private String role;
	private int movieId;
	private String job;
	
	

	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
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
	
	public boolean equals(Object obj) {
		if(obj instanceof MovieCast) {
			//TODO
			return false;
		} else {
			return false;
		}
	}
	}
	