package org.vatvit.movielib.objects;


public class MovieGenre {
private int id;
private String title;
private int movieId;

public int getMovieId() {
	return movieId;
}
public void setMovieId(int movieId) {
	this.movieId = movieId;
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

public String toString() {
	return title;	
}

public boolean equals(Object obj) {
	if(obj instanceof MovieGenre) {
		return false;
	} else {
		return false;
	}
}

}
