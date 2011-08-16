package org.vatvit.movielib.objects;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.tools.EqualsTools;

/**
 * Tietokannassa olevaa elokuvaa edustava luokka.
 * 
 */
public class Movie {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((added == null) ? 0 : added.hashCode());
		result = prime * result + ((cast == null) ? 0 : cast.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((director == null) ? 0 : director.hashCode());
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((slogan == null) ? 0 : slogan.hashCode());
		result = prime * result
				+ ((subtitles == null) ? 0 : subtitles.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((trailerUrl == null) ? 0 : trailerUrl.hashCode());
		result = prime * result + year;
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
		Movie other = (Movie) obj;
		if (added == null) { 
			if (other.added != null)
				return false;
		} else if (!added.equals(other.added))
			return false;
		if (cast == null) {
			if (other.cast != null)
				return false;
		} else if (!EqualsTools.castArrayEquals(cast, other.cast))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (genres == null) {
			if (other.genres != null)
				return false;
		} else if (!EqualsTools.genreArrayEquals(genres, other.genres))
			return false;
		if (id != other.id)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (Double.doubleToLongBits(rating) != Double
				.doubleToLongBits(other.rating))
			return false;
		if (slogan == null) {
			if (other.slogan != null)
				return false;
		} else if (!slogan.equals(other.slogan))
			return false;
		if (subtitles == null) {
			if (other.subtitles != null)
				return false;
		} else if (!EqualsTools.subtitlesArrayEquals(subtitles, other.subtitles))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (trailerUrl == null) {
			if (other.trailerUrl != null)
				return false;
		} else if (!trailerUrl.equals(other.trailerUrl))
			return false;
		if (year != other.year)
			return false;
		
		return true;
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

	/**
	 * Muodostaa uuden tyhjän ilmentymän Movie:sta
	 */
	public Movie() {

	}

	/**
	 * Muodostaa uuden tyhjän ilmentymän Movie:sta ja asettaa tietolähteenä
	 * käytettävä MovieModel:n. MovieModelia tarvitaan Movie:n ilmentymässä
	 * kansi ja taustakuvien haussa metodien getBackdrop(MovieModel.ImageSize
	 * size) ja getCover(MovieModel.ImageSize size) yhteydessä.
	 * 
	 * @param model
	 *            käytettävä tietolähde (MovieModel)
	 */
	public Movie(MovieModel model) {
		this.model = model;
		// cast = new ArrayList<MovieCast>();
		// genres = new ArrayList<MovieGenre>();

	}

	/**
	 * Asettaa käytettävän tietoläheen (MovieModel) MovieModelia tarvitaan
	 * Movie:n ilmentymässä kansi ja taustakuvien haussa metodien
	 * getBackdrop(MovieModel.ImageSize size) ja getCover(MovieModel.ImageSize
	 * size) yhteydessä
	 * 
	 * @param model
	 *            käytettävä tietolähde (MovieModel)
	 */
	public void setModel(MovieModel model) {
		this.model = model;
	}

	/**
	 * Palauttaa elokuvan videotiedoston sijainnin.
	 * 
	 * @return sijainti
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Asettaa elokuvan videotiedoston sijainnin
	 * 
	 * @param location
	 *            sijainti
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Palauttaa elokuvan lisäys ajankohdan
	 * 
	 * @return aika
	 */
	public Date getAdded() {
		return added;
	}

	/**
	 * Asettaa elokuvan lisäys ajankohdan
	 * 
	 * @param added
	 *            aika
	 */
	public void setAdded(Date added) {
		this.added = added;
	}

	/**
	 * Palauttaa elokuvan id:n
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asettaa elokuvan id:n
	 * 
	 * @param id
	 *            id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Palauttaa elokuvan nimen
	 * 
	 * @return nimi
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Asettaa elokuvan nimen
	 * 
	 * @param title
	 *            nimi
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Palauttaa elokuvan kuvauksen
	 * 
	 * @return kuvaus
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Asettaa elokuvan kuvauksen
	 * 
	 * @param description
	 *            kuvaus
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Palauttaa elokuvan sloganin
	 * 
	 * @return slogan
	 */
	public String getSlogan() {
		return slogan;
	}

	/**
	 * Asettaa elokuvan sloganin
	 * 
	 * @param slogan
	 *            slogan
	 */
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	/**
	 * Palauttaa elokuvan tuotantovuoden
	 * 
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Asettaa elokuvan tuotantovuoden
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Palauttaa elokuvan ohjaajan
	 * 
	 * @return ohjaaja
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Asettaa elokuvan ohjaajan
	 * 
	 * @param director
	 *            ohjaaja
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * Palauttaa elokuvan näyttelijät/tuontantoryhmän
	 * 
	 * @return näyttelijät/tuotantoryhmä
	 */
	public ArrayList<MovieCast> getCast() {
		return cast;
	}

	/**
	 * Asettaa elokuvan näyttelijät/tuontantoryhmän
	 * 
	 * @param cast
	 *            näyttelijät/tuontantoryhmä
	 */
	public void setCast(ArrayList<MovieCast> cast) {
		this.cast = cast;
	}

	/**
	 * Palauttaa elokuvan genret
	 * 
	 * @return genret
	 */
	public ArrayList<MovieGenre> getGenres() {
		return genres;
	}

	/**
	 * Asettaa elokuvan genret
	 * 
	 * @param genres
	 *            genret
	 */
	public void setGenres(ArrayList<MovieGenre> genres) {
		this.genres = genres;
	}

	/**
	 * Palauttaa elokuvan esittelyvideon url-osoitteen
	 * 
	 * @return esittelyvideon osoite
	 */
	public String getTrailerUrl() {
		return trailerUrl;
	}

	/**
	 * Asettaa elokuvan esittelyvideon osoitteen
	 * 
	 * @param trailerUrl
	 *            esittelyvideon url-osoite
	 */
	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
	}

	/**
	 * Palauttaa elokuvan arvostelun
	 * 
	 * @return arvstelu (0 - 10)
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * Asettaa elokuvan arvostelun. Arvon tulee olla väliltä 0 - 10, muutoin
	 * arvoksi asetetaan automaattisesti 0
	 * 
	 * @param rating
	 *            arvostelu
	 */
	public void setRating(double rating) {
		if (rating >= 0 && rating <= 10) {
			this.rating = rating;
		} else {
			this.rating = 0;
		}
	}

	/**
	 * Palauttaa elokuvan tekstitykset.
	 * 
	 * @return tekstitykset
	 */
	public ArrayList<MovieSubtitle> getSubtitles() {
		return subtitles;
	}

	/**
	 * Asettaa elokuvan tekstitykset
	 * 
	 * @param subtitles
	 *            tekstitykset
	 */
	public void setSubtitles(ArrayList<MovieSubtitle> subtitles) {
		this.subtitles = subtitles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return title + " (" + year + ")";
	}

	/**
	 * Palauttaa elokuvan taustakuvan. Toiminnon käyttö vaatii, että elokuvan
	 * ilmentymälle on asetettu MovieModel:n ilmentymä kuvien tietolähteeksi.
	 * 
	 * @param size koko
	 * @return kuva
	 */
	public BufferedImage getBackdrop(MovieModel.ImageSize size) {
		if (model != null) {
			return model.getBackdropForMovie(this, size);
		} else {
			return null;
		}
	}
	
	/**
	 * Palauttaa elokuvan kansikuvan. Toiminnon käyttö vaatii, että elokuvan
	 * ilmentymälle on asetettu MovieModel:n ilmentymä kuvien tietolähteeksi.
	 * 
	 * @param size koko
	 * @return kuva
	 */
	public BufferedImage getCover(MovieModel.ImageSize size) {
		if (model != null) {
			return model.getCoverForMovie(this, size);
		} else {
			return null;
		}
	}

	/**
	 * Palauttaa elokuvan tiedot html-muotoiltuna
	 * @return tiedot html -muodossa
	 */
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
			for (MovieCast cast : this.getCast()) {
				if (cast.getJob().equalsIgnoreCase("actor")) {
					if (actors.length() == 0) {
						actors = "<b>Näyttelijät</b><br />";

					}
					actors += cast + "<br />";
				} else {
					if (otherCast.length() == 0) {
						otherCast = "<b>Muu tuotantoryhmä</b><br />";
					}
					otherCast += cast + "<br />";
				}
			}
		}
		if (this.getSubtitles() != null && this.getSubtitles().size() > 0) {
			subtitles = "<b>Tekstitykset: </b>"
					+ this.getSubtitles().toString().replace("[", "")
							.replace("]", "") + "<br />";
		}

		return formatter.format(this.getRating()) + " / 10<br /><br />"
				+ description + "<br /><br />" + director + genres + actors
				+ "<br />" + otherCast + "<br />" + subtitles
				+ "<br /><b>Lisätty:</b> " + dformat.format(this.getAdded());

	}

}
