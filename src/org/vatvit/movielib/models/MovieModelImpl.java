package org.vatvit.movielib.models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import org.vatvit.movielib.dao.MovieDAO;
import org.vatvit.movielib.dao.QueryResult;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieSubtitle;
import org.vatvit.movielib.tools.FileTools;
import org.vatvit.movielib.tools.ImageTools;

/**
 * Tietokantaa tietovarastonaan käyttävä toteutus MovieModel rajapinnasta.
 * Säilöö elokuvien tiedot tietokantaan ja siirtää media-tiedostot data
 * kansioon.
 * 
 * @author Jaakko
 * 
 */
public class MovieModelImpl implements MovieModel {

	// Kansio johon tiedostot tallennetaan
	private File mediaDir;

	// Kuva, joka näytetään kun elokuvan kantta ei ole saatavilla
	private BufferedImage notFoundCover = null;
	private BufferedImage notFoundBackdrop = null;

	
	public MovieModelImpl() {
		mediaDir = new File(FileTools.getProgramDirectory() + File.separator
				+ "data" + File.separator);
	}

	/**
	 * Säilöö elokuvan tiedot tietokantaan
	 * 
	 * @param movie
	 *            Tallennettava elokuva
	 * @return Palauttaa kokonaislukuna elokuvan id:n tietokannassa
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private int addMovie(Movie movie) throws SQLException,
			ClassNotFoundException {

		return MovieDAO.addMovie(movie);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#allowDelete()
	 */
	@Override
	public boolean allowDelete() {
		// Sallitaanko elokuvien poisto
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#allowEdit()
	 */
	@Override
	public boolean allowEdit() {
		// Sallitaan tietojen muokakus
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#allowImport()
	 */
	@Override
	public boolean allowImport() {
		// Sallitaan tietojen lisäys
		return true;
	}

	/**
	 * Palauttaa vastauksena parametrinä annetun tiedostonimen suhteessa
	 * kansioon, josta ohjelma ajetaan
	 * 
	 * @param filename
	 *            Muunnettava tiedostonimi
	 * @return Muunnettu tiedostonimi merkkijonona
	 */
	private String cleanFilename(String filename) {
		File thisFolder = new File(".");
		File theFile = new File(filename);
		filename = theFile.getAbsolutePath().substring(
				thisFolder.getAbsolutePath().length() - 1);
		return filename;
	}

	/**
	 * Kopioi elokuvan videotiedoston videos kansioon
	 * 
	 * @param movie
	 *            Kopioitava elokuva
	 * @throws IOException
	 */
	private void copyVideoFile(Movie movie) throws IOException {

		// Kopioidaan videotiedosto
		String fileExt = getFileExtension(movie.getLocation());
		File vidOrgin = new File(movie.getLocation());
		File vidTarget = new File(mediaDir.getAbsolutePath() + File.separator
				+ "videos" + File.separator + movie.getId() + "." + fileExt);

		FileTools.copyFile(vidOrgin, vidTarget);

		// Asetetaan videotiedosto
		movie.setLocation(cleanFilename(vidTarget.getAbsolutePath()));
	}

	/**
	 * Poistaa parametrinä annetun tiedoston
	 * 
	 * @param file
	 *            poistettava tiedosto
	 */
	private boolean removeFile(File file) {
		return file.delete();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.models.MovieModel#deleteMovie(org.vatvit.movielib
	 * .objects.Movie)
	 */
	@Override
	public boolean deleteMovie(Movie movie) throws MovieDeleteException {
		int id = movie.getId();
		movie = this.getMovie(id);

		try {
			MovieDAO.deleteMovie(id);
		} catch (SQLException e) {
			throw new MovieDeleteException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new MovieDeleteException(e.getMessage());
		}
		// Poista videotiedosto
		removeFile(new File(movie.getLocation()));
		// Poista kuvatiedostot
		removeFile(new File(mediaDir.getAbsolutePath() + File.separator
				+ "covers" + File.separator + movie.getId() + "_small.png"));
		removeFile(new File(mediaDir.getAbsolutePath() + File.separator
				+ "backdrops" + File.separator + movie.getId() + "_small.png"));
		removeFile(new File(mediaDir.getAbsolutePath() + File.separator
				+ "covers" + File.separator + movie.getId() + "_medium.png"));
		removeFile(new File(mediaDir.getAbsolutePath() + File.separator
				+ "backdrops" + File.separator + movie.getId() + "_medium.png"));
		removeFile(new File(mediaDir.getAbsolutePath() + File.separator
				+ "covers" + File.separator + movie.getId() + "_large.png"));
		removeFile(new File(mediaDir.getAbsolutePath() + File.separator
				+ "backdrops" + File.separator + movie.getId() + "_large.png"));
		// Tekstitykset
		for (MovieSubtitle sub : movie.getSubtitles()) {
			removeFile(new File(sub.getLocation()));
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#getAllActors()
	 */
	@Override
	public ArrayList<String> getAllCast() throws MovieDataRetrieveException {

		try {
			return MovieDAO.getAllCast();
		} catch (SQLException e) {
			throw new MovieDataRetrieveException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new MovieDataRetrieveException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#getAllDirectors()
	 */
	@Override
	public ArrayList<String> getAllDirectors()
			throws MovieDataRetrieveException {

		try {
			return MovieDAO.getAllDirectors();
		} catch (SQLException e) {
			throw new MovieDataRetrieveException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new MovieDataRetrieveException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#getAllGenres()
	 */
	@Override
	public ArrayList<String> getAllGenres() throws MovieDataRetrieveException {

		try {
			return MovieDAO.getAllGenres();
		} catch (SQLException e) {
			throw new MovieDataRetrieveException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new MovieDataRetrieveException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#getAllYears()
	 */
	@Override
	public ArrayList<String> getAllYears() throws MovieDataRetrieveException {

		try {
			return MovieDAO.getAllYears();
		} catch (SQLException e) {
			throw new MovieDataRetrieveException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new MovieDataRetrieveException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.models.MovieModel#getBackdropForMovie(org.vatvit.
	 * movielib.objects.Movie, org.vatvit.movielib.models.MovieModel.ImageSize)
	 */
	@Override
	public BufferedImage getBackdropForMovie(Movie movie, ImageSize size) {

		try {
			File backdropFile = new File(mediaDir.getAbsolutePath()
					+ File.separator + "backdrops" + File.separator
					+ movie.getId() + "_" + size.name().toLowerCase() + ".png");

			if (backdropFile.exists()) {
				return ImageIO.read(backdropFile);
			} else {
				return getNotFoundBackdrop();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.models.MovieModel#getCoverForMovie(org.vatvit.movielib
	 * .objects.Movie, org.vatvit.movielib.models.MovieModel.ImageSize)
	 */
	@Override
	public BufferedImage getCoverForMovie(Movie movie, ImageSize size) {
		try {
			File coverFile = new File(mediaDir.getAbsolutePath()
					+ File.separator + "covers" + File.separator
					+ movie.getId() + "_" + size.name().toLowerCase() + ".png");
			if (coverFile.exists()) {
				return ImageIO.read(coverFile);
			} else {
				return getNotFoundCover();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Parsii tiedostonimestä tiedoston päätteet
	 * 
	 * @param filename
	 *            parsittava tiedosto
	 * @return tiedoston pääte mikäli sellainen löytyy tai tiedoston nimi
	 */
	private String getFileExtension(String filename) {
		File file = new File(filename);
		return file.getName().substring(file.getName().lastIndexOf(".") + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#getModelName()
	 */
	@Override
	public String getModelName() {
		// Modelin nimi
		return "Paikallinen tietokanta";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#getMovie(int)
	 */
	@Override
	public Movie getMovie(int id) {

		try {
			Movie movie = MovieDAO.getMovieById(id);
			movie.setModel(this);
			return movie;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.models.MovieModel#getMovies(org.vatvit.movielib.models
	 * .MovieModel.Field, java.lang.String, int, int,
	 * org.vatvit.movielib.models.MovieModel.Field, boolean, boolean, boolean,
	 * boolean)
	 */
	@Override
	public QueryResult<Movie> getMovies(Field searchField, String search,
			int limit, int page, Field orderBy, boolean desc,
			boolean includeCast, boolean includeGenres, boolean includeSubtitles)
			throws MovieDataRetrieveException {

		try {
			QueryResult<Movie> result = MovieDAO.searchMovieBy(searchField,
					search, limit, page, orderBy, desc, includeCast,
					includeGenres, includeSubtitles);
			for (Movie movie : result.getResults()) {
				movie.setModel(this);
			}
			return result;
		} catch (Exception e) {
			throw new MovieDataRetrieveException(e.getMessage());
		}

	}

	/**
	 * Singleton puuttuvaa kantta edustavalle kuvalle
	 * 
	 * @return kuva, joka edustaa puuttuvaa kantta
	 */
	private BufferedImage getNotFoundCover() {
		if (notFoundCover == null) {
			try {
				notFoundCover = ImageIO.read(new File("images" + File.separator
						+ "notfound.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return notFoundCover;
	}

	/**
	 * Singleton puuttuvaa taustaa edustavalle kuvalle
	 * 
	 * @return kuva, joka edustaa puuttuvaa taustaa
	 */
	private BufferedImage getNotFoundBackdrop() {
		if (notFoundBackdrop == null) {
			try {
				notFoundBackdrop = ImageIO.read(new File("images"
						+ File.separator + "notfound.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return notFoundBackdrop;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.models.MovieModel#saveMovie(org.vatvit.movielib.objects
	 * .Movie, java.awt.image.BufferedImage, java.awt.image.BufferedImage)
	 */
	public int saveMovie(Movie movie, BufferedImage coverImage,
			BufferedImage backdropImage) throws MovieException {

		try {

			if (movie.getId() == 0) {
				// Lis�t��n elokuva tietokantaan
				this.addMovie(movie);
			} else {
				MovieDAO.updateMovie(movie);
			}

			// Jos polku eri kuin data kansiossa
			String fileExt = getFileExtension(movie.getLocation());

			if (!movie.getLocation().equalsIgnoreCase(
					cleanFilename(mediaDir.getAbsolutePath() + File.separator
							+ "videos" + File.separator + movie.getId() + "."
							+ fileExt))) {
				copyVideoFile(movie);
			}

			// Tekstitykset
			for (MovieSubtitle sub : movie.getSubtitles()) {

				String target = mediaDir.getAbsolutePath()
						+ File.separator
						+ "subtitles"
						+ File.separator
						+ movie.getId()
						+ "_"
						+ sub.getId()
						+ "."
						+ sub.getLocation().substring(
								sub.getLocation().lastIndexOf(".") + 1);

				// Tarkistetaan onko tekstitys uusi tai osoittaako se uuteen
				// paikkaan

				if (sub.getId() == 0
						|| !sub.getLocation().equalsIgnoreCase(
								cleanFilename(target))) {

					File subOrgin = new File(sub.getLocation());
					File subTarget = new File(target);

					sub.setLocation(cleanFilename(subTarget.getAbsolutePath()));

					// KOPIOIDAAN
					FileTools.copyFile(subOrgin, subTarget);

				}
			}

			// Kuvatiedostot
			if (coverImage != null) {
				ImageIO.write(ImageTools.resizeImage(coverImage, 132, 195),
						"png", new File(mediaDir.getAbsolutePath()
								+ File.separator + "covers" + File.separator
								+ movie.getId() + "_small.png"));
				ImageIO.write(
						ImageTools.resizeImage(coverImage, 132 * 2, 195 * 2),
						"png", new File(mediaDir.getAbsolutePath()
								+ File.separator + "covers" + File.separator
								+ movie.getId() + "_medium.png"));
				ImageIO.write(
						ImageTools.resizeImage(coverImage, 132 * 3, 195 * 3),
						"png", new File(mediaDir.getAbsolutePath()
								+ File.separator + "covers" + File.separator
								+ movie.getId() + "_large.png"));
			}

			if (backdropImage != null) {
				ImageIO.write(ImageTools.resizeImage(backdropImage, 300, 169),
						"png", new File(mediaDir.getAbsolutePath()
								+ File.separator + "backdrops" + File.separator
								+ movie.getId() + "_small.png"));
				ImageIO.write(
						ImageTools.resizeImage(backdropImage, 300 * 2, 169 * 2),
						"png", new File(mediaDir.getAbsolutePath()
								+ File.separator + "backdrops" + File.separator
								+ movie.getId() + "_medium.png"));
				ImageIO.write(
						ImageTools.resizeImage(backdropImage, 300 * 3, 169 * 3),
						"png", new File(mediaDir.getAbsolutePath()
								+ File.separator + "backdrops" + File.separator
								+ movie.getId() + "_large.png"));
			}

			MovieDAO.updateMovie(movie);
			return movie.getId();

		} catch (FileNotFoundException e) {
			throw new MovieException(e.getMessage());
		} catch (IOException e) {
			throw new MovieException(e.getMessage());
		} catch (SQLException e) {
			throw new MovieException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new MovieException(e.getMessage());
		}

	}

}
