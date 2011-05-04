package org.vatvit.movielib.views;

import java.awt.image.BufferedImage;

import org.vatvit.movielib.objects.Movie;

public interface MovieListener {
	public void movieList(MovieView view);
	public void movieSaved(MovieView view);
	public void movieSelected(MovieView view);
	public void movieDeleted(MovieView view);
}
