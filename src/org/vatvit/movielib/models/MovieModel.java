package org.vatvit.movielib.models;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.vatvit.movielib.dao.QueryResult;
import org.vatvit.movielib.objects.Movie;

/**
 * Rajapinta, jonka toteuttavia luokkia voidaan käyttää sovelluksen
 * tietolähteinä.
 * 
 */
public interface MovieModel {

	/**
	 * Elokuvatietojen kenttiä edustava enumeraali
	 */
	public enum Field {
		ID, TITLE, DESCRIPTION, SLOGAN, DIRECTOR, TRAILER_URL, YEAR, RATING, ADDED, GENRE, CAST
	}

	/**
	 * Kansi ja taustakuvan kokoja edustava enumeraali
	 */
	public enum ImageSize {
		SMALL, MEDIUM, LARGE
	}

	/**
	 * @return Palauttaa tietolähteen nimen
	 */
	public String getModelName();

	/**
	 * Poistaa elokuvan tietovarastosta.
	 * 
	 * @param movie
	 *            poistettava elokuva
	 * @return totuusarvo siitä onnistuiko poisto
	 * @throws MovieDeleteException elokuvan poistossa tapahtunut virhe
	 */
	public boolean deleteMovie(Movie movie) throws MovieDeleteException;

	/**
	 * Palauttaa id:tä vastaavan elokuvan tiedot
	 * 
	 * @param id
	 *            elokuvan id
	 * @return Movie -luokan ilmentymä täytettynä elokuvan tiedoilla.
	 * @throws MovieDataRetrieveException elokuvan tietojen haussa tapahtunut virhe
	 */
	public Movie getMovie(int id) throws MovieDataRetrieveException;

	/**
	 * Elokuvien haku.
	 * 
	 * @param searchField
	 *            Kenttä, jonka perusteella haku tapahtuu
	 * @param search
	 *            Haettava sisältö
	 * @param limit
	 *            Vastauksien raja
	 * @param page
	 *            Monenneltako sivulta vastaukset halutaan
	 * @param orderBy
	 *            Kenttä, jonka perusteella vastaukset järjestetään
	 * @param desc
	 *            Järjestetäänkö laskevassa järjestyksessä
	 * @param includeCast
	 *            Liitetäänkö vastauksiin myös näyttelijät ja muu tuotantoryhmä
	 * @param includeGenres
	 *            Liitetäänkö vastauksiin myös genret
	 * @param includeSubtitles
	 *            Liitetäänkö vastauksiin myös tekstitykset
	 * @return QueryResult instanssi, joka sisältää tiedot käytetyistä
	 *         hakuehdoista ja haun vastauksen.
	 * @throws MovieDataRetrieveException elokuvan tietojen haussa tapahtunut virhe
	 */
	public QueryResult<Movie> getMovies(Field searchField, String search,
			int limit, int page, Field orderBy, boolean desc,
			boolean includeCast, boolean includeGenres, boolean includeSubtitles) throws MovieDataRetrieveException;

	/**
	 * Lisää elokuvan tietovarastoon.
	 * 
	 * @param movie
	 *            Elokuvan tiedot movie -oliossa.
	 * @param cover
	 *            Elokuvan kansikuva
	 * @param backdrop
	 *            Elokuvan taustakuva
	 * @return Lisätyn elokuvan id.
	 * @throws MovieException elokuvan tallennuksessa tapahtunut virhe
	 */
	public int saveMovie(Movie movie, BufferedImage cover,
			BufferedImage backdrop) throws MovieException;

	/**
	 * Palauttaa elokuvan taustakuvan halutussa koossa
	 * 
	 * @param movie
	 *            halutun elokuvan tiedot
	 * @param size
	 *            koko
	 * @return BufferedImage elokuvan taustakuvasta
	 */
	public BufferedImage getBackdropForMovie(Movie movie, ImageSize size);

	/**
	 * Palauttaa elokuvan kannen halutussa koossa
	 * 
	 * @param movie
	 *            halutun elokuvan tiedot
	 * @param size
	 *            koko
	 * @return BufferedImage elokuvan kannesta
	 */
	public BufferedImage getCoverForMovie(Movie movie, ImageSize size);

	/**
	 * @return onko tietolähteen elokuvia salittu muokattavan
	 */
	public boolean allowEdit();

	/**
	 * @return onko tietolähteeseen salittu elokuvien lisääminen
	 */
	public boolean allowImport();

	/**
	 * @return onko tietolähteestä sallittu elokuvien poistaminen
	 */
	public boolean allowDelete();

	/**
	 * @return kaikki tietolähteessä olevat näyttelijät ja muut tuotantoryhmän
	 *         jäsenet
	 * @throws MovieDataRetrieveException elokuvan tietojen haussa tapahtunut virhe
	 */
	public ArrayList<String> getAllCast() throws MovieDataRetrieveException;

	/**
	 * @return kaikki tietolähteessä olevat ohjaajat
	 * @throws MovieDataRetrieveException elokuvan tietojen haussa tapahtunut virhe
	 */
	public ArrayList<String> getAllDirectors() throws MovieDataRetrieveException;

	/**
	 * @return kaikki tietolähteessä olevat genret
	 * @throws MovieDataRetrieveException elokuvan tietojen haussa tapahtunut virhe
	 */
	public ArrayList<String> getAllGenres() throws MovieDataRetrieveException;

	/**
	 * @return kaikki tietolähteessä olevat tuotantovuodet
	 * @throws MovieDataRetrieveException elokuvan tietojen haussa tapahtunut virhe
	 */
	public ArrayList<String> getAllYears() throws MovieDataRetrieveException;
	
}
