package org.vatvit.movielib.views;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.vatvit.movielib.dao.QueryResult;
import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.objects.Movie;

public interface MovieView {

	// KUUNTELIJOIDEN ASETUS
	/**
	 * Asettaa kuuntelijan joka tarkkailee sovelluksen sulkupyyntöjä
	 * 
	 * @param listener
	 */
	public void setCloseListener(CloseListener listener);

	/**
	 * Asettaa kuuntelijan joka tarkkailee elokuviin liittyviä tapahtumia
	 * 
	 * @param listener
	 */
	public void setMovieListener(MovieListener listener);

	/**
	 * Asettaa kuuntelijan joka tarkkailee näyttelijöiden/tuotantotiimin
	 * tapahtumia
	 * 
	 * @param listener
	 */
	public void setCastListener(GroupListener listener);

	/**
	 * Asettaa kuuntelijan joka tarkkailee ohjaajien tapahtumia
	 * 
	 * @param listener
	 */
	public void setDirectorListener(GroupListener listener);

	/**
	 * Asettaa kuuntelijan joka tarkkailee vuosien
	 * 
	 * @param listener
	 */
	public void setYearListener(GroupListener listener);

	/**
	 * Asettaa kuuntelijan joka tarkkailee genrejen tapahtumia
	 * 
	 * @param listener
	 */
	public void setGenreListener(GroupListener listener);

	// NÄYTÄ

	/**
	 * Pyytää käyttöliittymää näyttämääm parametrinä annetun listan
	 * näyttelijöistä ja muista tuotannon jäsenistä
	 * 
	 * @param cast
	 *            lista näyttelijöistä tai vst.
	 */
	public void showCast(ArrayList<String> cast);

	/**
	 * Pyytää käyttöliittymää näyttämääm parametrinä annetun listan genreistä
	 * 
	 * @param genres
	 *            lista genrejä
	 */
	public void showGenres(ArrayList<String> genres);

	/**
	 * Pyytää käyttöliittymää näyttämääm parametrinä annetun listan ohjaajia
	 * 
	 * @param directors
	 *            lista ohjaajia
	 */
	public void showDirectors(ArrayList<String> directors);

	/**
	 * Pyytää käyttöliittymää näyttämääm parametrinä annetun listan
	 * tuotantovuosista
	 * 
	 * @param years
	 *            lista tuotantovuosia
	 */
	public void showYears(ArrayList<String> years);

	/**
	 * Pyytää käyttöliittymää näyttämääm parametrinä annetun listan elokuvista
	 * 
	 * @param movies
	 *            lista elokuvia
	 */
	public void showMovies(ArrayList<Movie> movies);

	/**
	 * Pyytää käyttöliittymää näyttämääm parametrinä annetun elokuvan teidot
	 * 
	 * @param movie
	 *            elokuva
	 */
	public void showMovie(Movie movie);

	/**
	 * Pyytää käyttöliittymää näyttämään esikatselmuksen elokuvista joissa
	 * näyttelijä tai vst. on
	 * 
	 * @param movies
	 *            ote ryhmään kuuluvista elokuvista
	 */
	public void showCastPreview(ArrayList<Movie> movies);

	/**
	 * Pyytää käyttöliittymää näyttämään esikatselmuksen elokuvista jotka
	 * kuuluvat genreen
	 * 
	 * @param movies
	 *            ote ryhmään kuuluvista elokuvista
	 */
	public void showGenrePreview(ArrayList<Movie> movies);

	/**
	 * Pyytää käyttöliittymää näyttämään esikatselmuksen elokuvista jotka on
	 * ohjannut parametrinä annettu ohjaaja
	 * 
	 * @param movies
	 *            ote ryhmään kuuluvista elokuvista
	 */
	public void showDirectorPreview(ArrayList<Movie> movies);

	/**
	 * Pyytää käyttöliittymää näyttämään esikatselmuksen elokuvista jotka on
	 * tuotettu kyseisenä
	 * 
	 * @param movies
	 *            ote ryhmään kuuluvista elokuvista
	 */
	public void showYearPreview(ArrayList<Movie> movies);

	// Käynnistyts

	/**
	 * Kertoo käyttöliittymälle, että kaikki muu on valmista
	 */
	public void init();

	// VIRHEET

	/**
	 * Näyttää virheilmoitukset
	 * 
	 * @param error
	 *            näytettävä virheilmoitus
	 */
	public void showError(String error);

	// Lataus
	/**
	 * Näyttää käyttäjälle että latautuminen tapahtuu
	 * 
	 * @param reason
	 */
	public void showLoading(String reason);

	/**
	 * Piiloittaa latautumis indikaattorin
	 */
	public void hideLoading();

	// Tiedot

	/**
	 * @return kyseisen näkymän/ulkoasun nimi
	 */
	public String getViewName();
	
	/**
	 * @return valittuna oleva genre 
	 */
	public String getGenre();
	/**
	 * @return valittuna oleva näyttelijä tai muu tuotantoryhmän jäsen
	 */
	public String getCast();
	/**
	 * @return valittuna oleva ohjaaja
	 */
	public String getDirector();
	/**
	 * @return valittuna oleva vuosi
	 */
	public String getYear();
	/**
	 * @return valittuna oleva elokuva
	 */
	public Movie getMovie();
	/**
	 * @return uudelle elokuvalle valittu kansi
	 */
	public BufferedImage getCover();
	/**
	 * @return uudelle elokuvalle valittu tausta
	 */
	public BufferedImage getBackdrop();


}
