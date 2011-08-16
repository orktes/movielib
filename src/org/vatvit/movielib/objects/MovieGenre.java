package org.vatvit.movielib.objects;


/**
 * Elokuvan genreä edustava luokka
 *
 */
public class MovieGenre {
private int id;
private String title;
private int movieId;

/**
 * Palauttaa elokuvan id:N
 * @return elokuvan id
 */
public int getMovieId() {
	return movieId;
}
/**
 * Asettaa elokuvan id:n
 * @param movieId elokuvan id
 */
public void setMovieId(int movieId) {
	this.movieId = movieId;
}
/**
 * Palauttaa genren id:n
 * @return genren id
 */
public int getId() {
	return id;
}
/**
 * Asettaa genren id:n
 * @param id genren id
 */
public void setId(int id) {
	this.id = id;
}
/**
 * Palauttaa genren nimen
 * @return nimi
 */
public String getTitle() {
	return title;
}
/**
 * Asettaa genren nimen
 * @param title nimi
 */
public void setTitle(String title) {
	this.title = title;
}

public String toString() {
	return title;	
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + movieId;
	result = prime * result + ((title == null) ? 0 : title.hashCode());
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
	MovieGenre other = (MovieGenre) obj;
	if (id != other.id)
		return false;
	if (movieId != other.movieId)
		return false;
	if (title == null) {
		if (other.title != null)
			return false;
	} else if (!title.equals(other.title))
		return false;
	return true;
}




}
