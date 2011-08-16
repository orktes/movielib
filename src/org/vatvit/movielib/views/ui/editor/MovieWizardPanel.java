package org.vatvit.movielib.views.ui.editor;

import java.awt.CardLayout;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;

import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.objects.MovieInfoResult;
import org.vatvit.movielib.objects.MovieSubtitle;
import org.vatvit.movielib.settings.SettingsLoader;
import org.vatvit.movielib.tools.FileTools;
import org.vatvit.movielib.views.ui.editor.panels.MovieCastPanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieDetailsPanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieFilesPanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieGenrePanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieNamePanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieSubtitlesPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Elokuvien lisäämiseen ja muokkaamiseen tarkoitettu wizard työkalu.
 * 
 */
public class MovieWizardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MovieNamePanel movieName = null;
	private MovieDetailsPanel movieDetailsPanel = null;
	private MovieCastPanel movieCastPanel = null;
	private MovieSubtitlesPanel movieSubtitlesPanel = null;
	private MovieFilesPanel movieFilesPanel = null;
	private MovieGenrePanel movieGenrePanel = null;
	private MovieInfoResult selectedMovieInfo = null;
	private ActionListener viewChangeListener = null; // @jve:decl-index=0:
	private String currentView = null;

	private ActionListener al = null;

	// @jve:decl-index=0:

	/**
	 * Luo uusi instanssi
	 */
	public MovieWizardPanel() {
		this(new MovieInfoResult(new Movie(), null, null));
	}

	/**
	 * Luo uusi instanssi ja alusta se elokuvan tiedoilla
	 * 
	 * @param movie
	 *            elokuvan tiedot
	 */
	public MovieWizardPanel(MovieInfoResult movie) {
		super();
		this.selectedMovieInfo = movie;

		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(544, 221);

		this.setLayout(new CardLayout());
		this.add(getMovieNamePanel(), "moviename");
		showView("moviename");
	}

	/**
	 * Näyttää kyseisen näkymän
	 * 
	 * @param view
	 *            nimi
	 */
	private void showView(String view) {
		this.currentView = view;
		CardLayout layout = (CardLayout) this.getLayout();
		layout.show(this, view);
		if (viewChangeListener != null) {
			// Kerrotaan kuuntelijalle että näkymä on vaihtunut
			ActionEvent actionEvent = new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED, view);
			viewChangeListener.actionPerformed(actionEvent);
		}

	}

	/**
	 * Luo uusi elokuvan nimeä kysyvä paneeli.
	 * 
	 * @return Paneeli
	 */
	private JPanel getMovieNamePanel() {
		movieName = new MovieNamePanel();
		// Mikäli elokuva on asetettu ja sillä on nimi asetetaan paneelin
		// oletusarvoksi jo määritetty nimi.
		if (selectedMovieInfo.getMovie() != null
				&& selectedMovieInfo.getMovie().getTitle() != null) {
			movieName.setMovieName(selectedMovieInfo.getMovie().getTitle());
			movieName.validate();
		}

		final MovieWizardPanel self = this;
		// Lisätään kuuntelija kuuntelemaan näppäinpainalluksia
		movieName.addActionListener(new ActionListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.
			 * ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("cancel")) {
					if (al != null) {
						ActionEvent actionEvent = new ActionEvent(this,
								ActionEvent.ACTION_PERFORMED, "cancel");
						al.actionPerformed(actionEvent);
					}
				}  else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					// Siirrytään seuraavaan vaiheeseen ilman
					// tarkistusta/tietojen hakua
					String movieTitle = movieName.getMovieName();

					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}

					selectedMovieInfo.getMovie().setTitle(movieTitle);
					self.add(getMovieDetailsPanel("moviename"), "moviedetails");

					showView("moviedetails");

				}

			}

		});

		return movieName;
	}

	/**
	 * Luo perustietojen täyttö paneeli.
	 * 
	 * @param previousCard
	 *            viimeksi ollut näykmä
	 * @return paneeli
	 */
	private JPanel getMovieDetailsPanel(final String previousCard) {
		movieDetailsPanel = new MovieDetailsPanel();
		final MovieWizardPanel self = this;
		if (selectedMovieInfo.getMovie() != null) {
			movieDetailsPanel.setMovieTitle(selectedMovieInfo.getMovie()
					.getTitle());
			movieDetailsPanel.setMovieSlogan(selectedMovieInfo.getMovie()
					.getSlogan());
			movieDetailsPanel.setMovieDirector(selectedMovieInfo.getMovie()
					.getDirector());
			movieDetailsPanel.setMovieYear(selectedMovieInfo.getMovie()
					.getYear());
			movieDetailsPanel.setMovieTrailer(selectedMovieInfo.getMovie()
					.getTrailerUrl());
			movieDetailsPanel.setMovieYear(selectedMovieInfo.getMovie()
					.getYear());
			movieDetailsPanel.setMovieRating(selectedMovieInfo.getMovie()
					.getRating());
			movieDetailsPanel.setMovieDescription(selectedMovieInfo.getMovie()
					.getDescription());

		}
		movieDetailsPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {

					showView(previousCard);
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}
					selectedMovieInfo.getMovie().setTitle(
							movieDetailsPanel.getMovieTitle());
					selectedMovieInfo.getMovie().setSlogan(
							movieDetailsPanel.getMovieSlogan());
					selectedMovieInfo.getMovie().setDirector(
							movieDetailsPanel.getMovieDirector());
					selectedMovieInfo.getMovie().setYear(
							movieDetailsPanel.getMovieYear());
					selectedMovieInfo.getMovie().setTrailerUrl(
							movieDetailsPanel.getMovieTrailer());
					selectedMovieInfo.getMovie().setRating(
							movieDetailsPanel.getMovieRating());
					selectedMovieInfo.getMovie().setDescription(
							movieDetailsPanel.getMovieDescription());

					self.add(getMovieGenrePanel(), "moviegenres");

					showView("moviegenres");

				}

			}

		});
		return movieDetailsPanel;

	}

	/**
	 * Luo genrejen editointi paneeli
	 * 
	 * @return paneeli
	 */
	private JPanel getMovieGenrePanel() {
		movieGenrePanel = new MovieGenrePanel();
		if (selectedMovieInfo.getMovie() != null
				&& selectedMovieInfo.getMovie().getGenres() != null) {
			movieGenrePanel.setMovieGenre(selectedMovieInfo.getMovie()
					.getGenres());
		} else {
			movieGenrePanel.setMovieGenre(new ArrayList<MovieGenre>());
		}
		final MovieWizardPanel self = this;
		movieGenrePanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {

					showView("moviedetails");
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}
					selectedMovieInfo.getMovie().setGenres(
							movieGenrePanel.getMovieGenre());

					self.add(getMovieCastPanel(), "moviecast");

					showView("moviecast");

				}

			}

		});

		return movieGenrePanel;

	}

	/**
	 * Luo näyttelijöiden muokkaus näkymä
	 * 
	 * @return paneeli
	 */
	private JPanel getMovieCastPanel() {
		movieCastPanel = new MovieCastPanel();
		if (selectedMovieInfo.getMovie() != null
				&& selectedMovieInfo.getMovie().getCast() != null) {
			movieCastPanel.setMovieCast(selectedMovieInfo.getMovie().getCast());
		} else {
			movieCastPanel.setMovieCast(new ArrayList<MovieCast>());
		}
		final MovieWizardPanel self = this;
		movieCastPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {

					showView("moviegenres");
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}
					selectedMovieInfo.getMovie().setCast(
							movieCastPanel.getMovieCast());

					self.add(getMovieSubtitlesPanel(), "moviesubtitles");

					showView("moviesubtitles");

				}

			}

		});

		return movieCastPanel;

	}

	/**
	 * Luo tekstitysten muokkaus näkymä
	 * 
	 * @return paneeli
	 */
	private JPanel getMovieSubtitlesPanel() {
		movieSubtitlesPanel = new MovieSubtitlesPanel();
		if (selectedMovieInfo.getMovie() != null
				&& selectedMovieInfo.getMovie().getSubtitles() != null) {
			movieSubtitlesPanel.setMovieSubtitle(selectedMovieInfo.getMovie()
					.getSubtitles());
		} else {
			movieSubtitlesPanel
					.setMovieSubtitle(new ArrayList<MovieSubtitle>());
		}

		final MovieWizardPanel self = this;
		movieSubtitlesPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {

					showView("moviecast");
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}
					selectedMovieInfo.getMovie().setSubtitles(
							movieSubtitlesPanel.getMovieSubtitle());

					self.add(getMovieFilesPanel(), "moviefiles");

					showView("moviefiles");

				}

			}

		});

		return movieSubtitlesPanel;

	}

	/**
	 * Luo elokuvan tiedostojen (videotiedosto, kansi, tausta) muokkaus paneeli
	 * 
	 * @return paneeli
	 */
	private JPanel getMovieFilesPanel() {
		movieFilesPanel = new MovieFilesPanel();

		if (selectedMovieInfo.getMovie() != null) {
			movieFilesPanel.setVideoLocation(selectedMovieInfo.getMovie()
					.getLocation());
		}
		movieFilesPanel.setCoverLocation(selectedMovieInfo.getCover());
		movieFilesPanel.setBackdropLocation(selectedMovieInfo.getBackdrop());

		movieFilesPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {

					showView("moviesubtitles");
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}
					selectedMovieInfo.getMovie().setLocation(
							movieFilesPanel.getVideoLocation());
					selectedMovieInfo.setCover(movieFilesPanel
							.getCoverLocation());
					selectedMovieInfo.setBackdrop(movieFilesPanel
							.getBackdropLocation());

					if (movieFilesPanel.getVideoLocation().length() > 0
							&& ((new File(movieFilesPanel.getVideoLocation())
									.exists()) || (new File(FileTools
									.getProgramDirectory()
									+ File.separator
									+ movieFilesPanel.getVideoLocation())
									.exists()))) {
						if (movieFilesPanel.getCoverLocation().length() == 0) {
							JOptionPane.showMessageDialog(
									null,
									SettingsLoader
											.getValue(
													"lang.cover_not_set_adding_without",
													"Kantta ei ole asetettu. Lisätään ilman."));
						}

						if (movieFilesPanel.getBackdropLocation().length() == 0) {
							JOptionPane.showMessageDialog(
									null,
									SettingsLoader
											.getValue(
													"lang.backdrop_not_set_adding_without",
													"Tausta ei ole asetettu. Lisätään ilman."));
						}
						if (al != null) {
							ActionEvent actionEvent = new ActionEvent(this,
									ActionEvent.ACTION_PERFORMED, "save");
							al.actionPerformed(actionEvent);
						}
					} else {
						JOptionPane.showMessageDialog(null, SettingsLoader
								.getValue("lang.file_doesnt_exists",
										"Tiedostoa ei ole olemassa"));
					}

				}

			}

		});

		return movieFilesPanel;

	}

	/**
	 * Aseta tapahtumakuunteli
	 * 
	 * @param al
	 */
	public void setActionListener(ActionListener al) {
		this.al = al;
	}

	/**
	 * Aseta elokuva
	 * 
	 * @param movie
	 *            elokuva
	 */
	public void setMovie(Movie movie) {
		selectedMovieInfo.setMovie(movie);
	}

	/**
	 * Palauta elokuva
	 * 
	 * @return elokuva
	 */
	public Movie getMovie() {
		return selectedMovieInfo.getMovie();
	}

	/**
	 * Aseta kansikuva
	 * 
	 * @param cover
	 *            kansikuva
	 */
	public void setCover(String cover) {
		selectedMovieInfo.setCover(cover);
	}

	/**
	 * Aseta taustakuva
	 * 
	 * @param backdrop
	 */
	public void setBackdrop(String backdrop) {
		selectedMovieInfo.setBackdrop(backdrop);
	}

	/**
	 * Palauta kansikuva
	 * 
	 * @return kansikuvan osoite
	 */
	public String getCover() {
		return selectedMovieInfo.getCover();
	}

	/**
	 * Palauta kansikuva
	 * 
	 * @return kansikuvan osoite
	 */
	public String getBackdrop() {
		return selectedMovieInfo.getBackdrop();
	}

	/**
	 * Palauttaa kansikuvan BufferedImagena
	 * 
	 * @return kansi
	 */
	public BufferedImage getCoverAsBufferedImage() {
		if (this.getCover() != null && this.getCover().length() > 0) {
			try {
				URL coverUrl = new URL(this.getCover());
				try {
					return ImageIO.read(coverUrl);
				} catch (IOException e) {
					return null;
				}
			} catch (MalformedURLException e) {
				File coverFile = new File(this.getCover());
				try {
					return ImageIO.read(coverFile);
				} catch (IOException e1) {
					return null;
				}
			}

		}
		return null;
	}

	/**
	 * Palauttaa taustakuvan BufferedImagena
	 * 
	 * @return kansi
	 */
	public BufferedImage getBackdropAsBufferedImage() {
		if (this.getBackdrop() != null && this.getBackdrop().length() > 0) {
			try {
				URL backdropUrl = new URL(this.getBackdrop());
				try {
					return ImageIO.read(backdropUrl);
				} catch (IOException e) {
					return null;
				}
			} catch (MalformedURLException e) {
				File backdropFile = new File(this.getBackdrop());
				try {
					return ImageIO.read(backdropFile);
				} catch (IOException e1) {
					return null;
				}
			}

		}
		return null;
	}

	/**
	 * Asettaa tapahtumakuuntelijan seuraamaan näkymän vaihtoja
	 * 
	 * @param viewChangeListener
	 */
	public void setViewChangeListener(ActionListener viewChangeListener) {
		this.viewChangeListener = viewChangeListener;
		ActionEvent actionEvent = new ActionEvent(this,
				ActionEvent.ACTION_PERFORMED, currentView);
		viewChangeListener.actionPerformed(actionEvent);
	}

	public static void main(String[] args) {
		MovieWizardPanel wizard = new MovieWizardPanel();
		JFrame frame = new JFrame();
		frame.add(wizard);
		frame.setSize(544, 600);
		frame.setVisible(true);
	}

} // @jve:decl-index=0:visual-constraint="169,17"
