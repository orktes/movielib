package org.vatvit.movielib.objects;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.vatvit.movielib.models.MovieModel;

public class Movie {
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Movie) {
			//T�H�N PIT�� VIEL TEHD�
			return false;
		} else {
			return false;
		}
	}

	private int id;
	private String title;
	private String description;
	private String slogan;
	private int year;
	private String director;
	private ArrayList<MovieCast> cast;
	private ArrayList<MovieGenre> genres;
	private String trailerUrl;
	private double rating;
	private ArrayList<MovieSubtitle> subtitles;
	private String location;
	private Date added;

	private MovieModel model;
	
	public Movie() {
		
	}
	
	public Movie(MovieModel model) {
		this.model=model;
		// cast = new ArrayList<MovieCast>();
		// genres = new ArrayList<MovieGenre>();

	}
	
	public void setModel(MovieModel model) {
		this.model = model;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public ArrayList<MovieCast> getCast() {
		return cast;
	}

	public void setCast(ArrayList<MovieCast> cast) {
		this.cast = cast;
	}

	public ArrayList<MovieGenre> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<MovieGenre> genres) {
		this.genres = genres;
	}

	public String getTrailerUrl() {
		return trailerUrl;
	}

	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public ArrayList<MovieSubtitle> getSubtitles() {
		return subtitles;
	}

	public void setSubtitles(ArrayList<MovieSubtitle> subtitles) {
		this.subtitles = subtitles;
	}

	@Override
	public String toString() {
		
		return title+" ("+year+")";
	}

	public BufferedImage getBackdrop(MovieModel.ImageSize size) {
		if(model!=null) {
			return model.getBackdropForMovie(this, size);
		} else {
			return null;
		}
	}
	public BufferedImage getCover(MovieModel.ImageSize size) {
		if(model!=null) {
			return model.getCoverForMovie(this, size);
		} else {
			return null;
		}
	}
	public String toHTML() {
		SimpleDateFormat dformat = new SimpleDateFormat("dd.MM.yyyy");
		NumberFormat formatter = new DecimalFormat("#0.0");
		
		String genres = "";
		String actors = "";
		String otherCast = "";
		String subtitles = "";
		String description = this.getDescription();
		String director = "";

		if (this.getDirector() != null && this.getDirector().length() > 0) {
			director = "<b>Ohjaaja:</b> " + this.getDirector() + "<br /><br />";
		}

		

		if (this.getGenres() != null && this.getGenres().size() > 0) {
			genres = "<b>Genret: </b>"
					+ this.getGenres().toString().replace("[", "")
							.replace("]", "") + "<br /><br />";
		}
		if (this.getCast() != null) {
			for(MovieCast cast : this.getCast()) {
				if(cast.getJob().equalsIgnoreCase("actor")) {
					if(actors.length()==0) {
						actors="<b>Näyttelijät</b><br />";
		
					}
					actors+=cast+"<br />";
				} else {
					if(otherCast.length()==0) {
						otherCast="<b>Muu tuotantoryhmä</b><br />";
					}
					otherCast+=cast+"<br />";
				}
			}
		}
		if (this.getSubtitles() != null && this.getSubtitles().size() > 0) {
			subtitles = "<b>Tekstitykset: </b>"
					+ this.getSubtitles().toString().replace("[", "")
							.replace("]", "") + "<br />";
		}
		
		return formatter.format(this.getRating())
		+ " / 10<br /><br />" + description + "<br /><br />" + director
		+ genres + actors +"<br />"+ otherCast +"<br />"+ subtitles + "<br /><b>Lisätty:</b> "
		+ dformat.format(this.getAdded());
		
	}

}
