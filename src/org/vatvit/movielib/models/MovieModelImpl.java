package org.vatvit.movielib.models;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.stream.Location;

import org.vatvit.movielib.dao.MovieDAO;
import org.vatvit.movielib.dao.QueryResult;
import org.vatvit.movielib.models.MovieModel.Field;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.objects.MovieInfoResult;
import org.vatvit.movielib.objects.MovieSubtitle;
import org.vatvit.movielib.objects.SubtitleImport;
import org.vatvit.movielib.tools.FileTools;
import org.vatvit.movielib.tools.ImageTools;
import org.vatvit.movielib.tools.MovieInfoTools;

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

	/**
	 * Missä kansiossa sovelluksen tiedostot sijaitsevat
	 * 
	 * @return Merkkijonona kansio, jossa sovellus sijaitsee
	 */
	private static String getProgramDirectory() {
		// Jos ajetaan jar tiedostosta
		CodeSource source = Location.class.getProtectionDomain()
				.getCodeSource();
		if (source != null) {
			return source.getLocation().toString();
		} else {
			// Jos ajetaan suoraa class-tiedostoista
			return new File("").getAbsolutePath();
		}
	}

	public static void main(String[] args) {
		// LUODAAN INSTANSSI
		System.out.println("Luodaan instanssi modelista");
		MovieModel model = new MovieModelImpl();

		System.out.println("Luodaan instanssi moviesta");
		Movie movie = new Movie();
		ArrayList<MovieInfoResult> details = MovieInfoTools.getMovieInfo("Starwars");
		if (details.size()>0) {
			movie = details.get(0).getMovie();
		} else {
			movie.setTitle("Big Buck Bunny");
			movie.setDescription("Follow a day of the life of Big Buck Bunny when he meets three bullying rodents: Frank, Rinky, and Gamera. The rodents amuse themselves by harassing helpless creatures by throwing fruits, nuts and rocks at them. After the deaths of two of Bunny's favorite butterflies, and an offensive attack on Bunny himself, Bunny sets aside his gentle nature and orchestrates a complex plan for revenge.");
			movie.setDirector("Sacha Goedegebure");
			movie.setRating(7.8);
			movie.setSlogan("");
			movie.setYear(2008);
			movie.setTrailerUrl("http://www.youtube.com/watch?v=IBTE-RoMsvw");
			movie.setGenres(new ArrayList<MovieGenre>());
			MovieGenre genre = new MovieGenre();
			genre.setTitle("Animation");
			movie.getGenres().add(genre);
		}
		movie.setLocation("/Users/iida/Downloads/The.Vampire.Diaries.S02E17.HDTV.XviD-2HD.avi");

		movie.setSubtitles(new ArrayList<MovieSubtitle>());
		MovieSubtitle subtitle = new MovieSubtitle();
		subtitle.setLabel("Suomi");
		subtitle.setLang("fi-FI");
		subtitle.setLocation("../VODPalvelu/test-data/demo-subtitles.srt");
		//movie.getSubtitles().add(subtitle);
		MovieSubtitle subtitle2 = new MovieSubtitle();
		subtitle2.setLabel("Suomi2");
		subtitle2.setLang("fi-FI");
		subtitle2.setLocation("../VODPalvelu/test-data/demo-subtitles.srt");
		//movie.getSubtitles().add(subtitle2);
		BufferedImage cover = null;
		BufferedImage backdrop = null;
		if (details.size()>0) {
			try {
				cover = ImageIO.read(new URL(details.get(0).getCover()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				backdrop = ImageIO.read(new URL(details.get(0).getBackdrop()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.saveMovie(movie, cover, backdrop);

	}

	public MovieModelImpl() {
		mediaDir = new File(getProgramDirectory() + "/data/");
	}

	/**
	 * Säilöö elokuvan tiedot tietokantaan
	 * 
	 * @param movie
	 *            Tallennettava elokuva
	 * @return Palauttaa kokonaislukuna elokuvan id:n tietokannassa
	 */
	private int addMovie(Movie movie) {

		try {
			return MovieDAO.addMovie(movie);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.models.MovieModel#deleteMovie(org.vatvit.movielib
	 * .objects.Movie)
	 */
	@Override
	public boolean deleteMovie(Movie movie) {
		int id = movie.getId();
		try {
			return MovieDAO.deleteMovie(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.models.MovieModel#getAllActors()
	 */
	@Override
	public ArrayList<String> getAllCast() {

		try {
			return MovieDAO.getAllCast();
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
	 * @see org.vatvit.movielib.models.MovieModel#getAllDirectors()
	 */
	@Override
	public ArrayList<String> getAllDirectors() {

		try {
			return MovieDAO.getAllDirectors();
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
	 * @see org.vatvit.movielib.models.MovieModel#getAllGenres()
	 */
	@Override
	public ArrayList<String> getAllGenres() {

		try {
			return MovieDAO.getAllGenres();
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
	 * @see org.vatvit.movielib.models.MovieModel#getAllYears()
	 */
	@Override
	public ArrayList<String> getAllYears() {

		try {
			return MovieDAO.getAllYears();
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
			boolean includeCast, boolean includeGenres, boolean includeSubtitles) {

		try {
			QueryResult<Movie> result = MovieDAO
			.searchMovieBy(searchField, search, limit, page, orderBy,
					desc, includeCast, includeGenres, includeSubtitles);
			for(Movie movie : result.getResults()) {
				movie.setModel(this);
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
						+ "elokuvat.png"));
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
						+ File.separator + "elokuvat.png"));
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
			BufferedImage backdropImage) {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}
