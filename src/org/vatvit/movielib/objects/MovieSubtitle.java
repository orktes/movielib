package org.vatvit.movielib.objects;

/**
 * Elokuvan tekstitystiedostoa edustava luokka
 *
 */
public class MovieSubtitle {
	private String lang;
	private String label;
	private int movieId;
	private int id;
	private String location;
	
	/**
	 * Palauttaa tekstityksen kielen
	 * @return kieli
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * Asettaa tekstityksen kielen
	 * @param lang kieli
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	/**
	 * Palauttaa tekstityksen nimen
	 * @return nimi
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Asettaa tekstityksen nimen
	 * @param label tekstityksen nimi
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Palauttaa elokuvan id:N
	 * @return elokuvan id
	 */
	public int getMovieId() {
		return movieId;
	}
	/**
	 * Asettaa elokuvan id:n
	 * @param movieId
	 */
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	/**
	 * Palauttaa tekstityksen id:n
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Asettaa tekstityksen id:n
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Palauttaa tekstitystiedoston sijainnin
	 * @return sijanti
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * Asettaa tekstitystiedoston sijainnin
	 * @param location sijainti
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString() {
		return label+" ("+lang+")";	
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + movieId;
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
		MovieSubtitle other = (MovieSubtitle) obj;
		if (id != other.id)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (movieId != other.movieId)
			return false;
		return true;
	}
	
	
	

}
