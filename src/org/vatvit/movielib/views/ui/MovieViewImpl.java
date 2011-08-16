package org.vatvit.movielib.views.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.settings.SettingsLoader;
import org.vatvit.movielib.tools.FileTools;

import org.vatvit.movielib.views.CloseListener;
import org.vatvit.movielib.views.GroupListener;
import org.vatvit.movielib.views.MovieListener;
import org.vatvit.movielib.views.MovieView;

import org.vatvit.movielib.views.ui.menu.BasicMenu;
import org.vatvit.movielib.views.ui.menu.MenuMessage;
import org.vatvit.movielib.views.ui.menu.MovieGroupMenu;
import org.vatvit.movielib.views.ui.menu.MovieSettingsMenu;
import org.vatvit.movielib.views.ui.menu.MoviesMenu;
import org.vatvit.movielib.views.ui.menu.PlayInformation;
import org.vatvit.movielib.views.ui.menu.panels.MenuImage;
import org.vatvit.movielib.views.ui.player.Player;

/**
 * Elokuvien katsomiseen tarkoitettu kokoruudun käyttöliittymä
 * 
 */
public class MovieViewImpl extends JFrame implements MovieView {

	private MovieListener movieListener;

	private CloseListener closeListener;

	private GroupListener castListener;

	private GroupListener yearListener;

	private GroupListener directorListener;

	private GroupListener genreListener;

	// NÄKYMÄT
	private BasicMenu mainMenu;

	private MoviesMenu moviesMenu;

	private MovieGroupMenu castMenu;

	private MovieGroupMenu directorsMenu;

	private MovieGroupMenu yearsMenu;

	private MovieGroupMenu genresMenu;

	private MovieSettingsMenu movieSettingsMenu;

	private MenuMessage menuMessage;

	private Player moviePlayer;

	private String currentView;

	/**
	 * Luo uuden MovieViewImpl olion
	 */
	public MovieViewImpl() {

	}

	/**
	 * Kokeilee ikkunan asettamista kokoruutuun, mikäli
	 * kokoruututila ei ole tuettu asetetaan ikkunan koko 1024 x
	 * 700 ja annetaan virheilmoitus.
	 */
	private void makeFullScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		if (gs.isFullScreenSupported()) {

			setUndecorated(true);
			gs.setFullScreenWindow(this);
			validate();

		} else {
			setSize(1024, 700);
			showError("Kokoruutu tilaa ei ole tuettu!");
			setVisible(true);

		}

	}

	/**
	 * Alustetaan komponentit
	 */
	private void initComponents() {
		setTitle("MovieLib 0.1");
		setBackground(new Color(0, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new java.awt.CardLayout());

		createMainMenu();

	}

	/**
	 * Lisää uusi näkymä/kortti
	 * 
	 * @param panel
	 *            Lisättävä JPanel
	 * @param name
	 *            nimi
	 */
	private void addCard(JPanel panel, String name) {
		currentView = name;
		getContentPane().add(panel, name);
	}

	/**
	 * Näytä näkymä, jonka nimi vastaa parametrinä annettua
	 * merkkijonoa ja aseta se aktiiviseksi (fokukseen)
	 * 
	 * @param name
	 */
	private void showCard(String name) {
		currentView = name;
		CardLayout layout = (CardLayout) getContentPane()
				.getLayout();
		layout.show(getContentPane(), name);
		if (name.equals("moviesmenu")) {
			moviesMenu.focus();
		} else if (name.equals("castmenu")) {
			castMenu.focus();
		} else if (name.equals("genresmenu")) {
			genresMenu.focus();
		} else if (name.equals("yearsmenu")) {
			yearsMenu.focus();
		} else if (name.equals("directorsmenu")) {
			directorsMenu.focus();
		} else if (name.equals("mainmenu")) {
			mainMenu.focus();
		} else if (name.equals("moviesettingsmenu")) {
			movieSettingsMenu.focus();
		} else if (name.equals("movieplayer")) {
			moviePlayer.focus();
		} else if (name.equals("message")) {
			menuMessage.focus();
		}
	}

	/**
	 * Luo päävalikko. Elokuvat, Näyttelijät, Ohjaajat, Genret,
	 * Vuodet, Lopeta
	 */
	private void createMainMenu() {

		// Määritetään valikkoitemit
		ArrayList<String> menuItems = new ArrayList<String>();
		menuItems.add(SettingsLoader.getValue("lang.movies",
				"Elokuvat"));
		menuItems.add(SettingsLoader.getValue("lang.cast",
				"Näyttelijät"));
		menuItems.add(SettingsLoader.getValue("lang.directors",
				"Ohjaajat"));
		menuItems.add(SettingsLoader.getValue("lang.genres",
				"Genret"));
		menuItems.add(SettingsLoader.getValue("lang.years",
				"Vuodet"));
		menuItems.add(SettingsLoader.getValue("lang.quit",
				"Lopeta"));

		mainMenu = new BasicMenu(menuItems);
		final MovieViewImpl self = this;
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// painalluksia
		mainMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase(SettingsLoader
						.getValue("lang.quit", "Lopeta"))
						|| menuItem.equalsIgnoreCase("back")) {
					if (closeListener != null)
						closeListener.close(self); // Kerro
													// ohjaimelle
													// valinnasta
				} else if (menuItem
						.equalsIgnoreCase(SettingsLoader
								.getValue("lang.movies",
										"Elokuvat"))) {
					if (movieListener != null)
						movieListener.movieList(self); // Kerro
														// ohjaimelle
														// valinnasta
				} else if (menuItem
						.equalsIgnoreCase(SettingsLoader
								.getValue("lang.cast",
										"Näyttelijät"))) {
					if (castListener != null)
						castListener.listRequest(self); // Kerro
														// ohjaimelle
														// valinnasta
				} else if (menuItem
						.equalsIgnoreCase(SettingsLoader
								.getValue("lang.genres",
										"Genret"))) {
					if (genreListener != null)
						genreListener.listRequest(self); // Kerro
															// ohjaimelle
															// valinnasta
				} else if (menuItem.equalsIgnoreCase(SettingsLoader
						.getValue("lang.years", "Vuodet"))) {
					if (yearListener != null)
						yearListener.listRequest(self);
				} else if (menuItem
						.equalsIgnoreCase(SettingsLoader
								.getValue("lang.directors",
										"Ohjaajat"))) {
					if (directorListener != null)
						directorListener.listRequest(self); // Kerro
															// ohjaimelle
															// valinnasta
				}

			}

		});

		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// liikkeitä ja näyttämää valintaa vastaava kuva
		mainMenu.getMenu().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(
							ListSelectionEvent arg0) {
						String item = (String) mainMenu
								.getMenu().getSelectedValue();
						JPanel infoPanel = mainMenu
								.getInfoPanel();
						infoPanel
								.setLayout(new java.awt.GridLayout());
						infoPanel.removeAll();
						infoPanel.add(new MenuImage(FileTools
								.getProgramDirectory()
								+ File.separator
								+ "images"
								+ File.separator
								+ item.toLowerCase()
										.replaceAll("ä", "a")
										.replaceAll("ö", "o")
										.replaceAll("å", "o")
								+ ".png", true));
						infoPanel.validate();
					}

				});

		// Näytetään ensimmäisen valinnan kuva
		String item = (String) mainMenu.getMenu().getModel()
				.getElementAt(0);
		JPanel infoPanel = mainMenu.getInfoPanel();
		infoPanel.setLayout(new java.awt.GridLayout());
		infoPanel.removeAll();
		infoPanel.add(new MenuImage(FileTools
				.getProgramDirectory()
				+ File.separator 
				+ "images"
				+ File.separator
				+ item.toLowerCase().replaceAll("ä", "a")
						.replaceAll("ö", "o")
						.replaceAll("å", "o") + ".png", true));
		infoPanel.validate();

		// lisätään kortti
		addCard(mainMenu, "mainmenu");

	}

	/**
	 * Luo elokuvavalikon annetulla kokoelmalla elokuvia.
	 * 
	 * @param movies
	 *            valikkoon asetettavat elokuvat
	 */
	private void createMoviesMenu(ArrayList<Movie> movies) {

		final MovieViewImpl self = this;
		moviesMenu = new MoviesMenu(movies);
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// tapahtumia
		moviesMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {

					// Siirry pois
					moviesMenu.stopScrollProcess();
					showCard(moviesMenu.getPreviusCard());
				} else {

					moviesMenu.stopScrollProcess();

					// Kerro ohjaimelle valinnasta
					movieListener.movieSelected(self);

				}
			}

		});

		addCard(moviesMenu, "moviesmenu");

	}

	/**
	 * Luo elokuvan toistovalinnan valikko
	 * 
	 * @param movie
	 *            elokuva, jolle valikko luodaan
	 */
	private void createMovieSettingsMenu(final Movie movie) {
		movieSettingsMenu = new MovieSettingsMenu(movie,
				movie.getBackdrop(MovieModel.ImageSize.LARGE));
		// Lisätään tapahtuma kuuntelija kuuntelemaan valikon
		// painalluksia
		movieSettingsMenu
				.setActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String menuItem = arg0
								.getActionCommand();
						if (menuItem.equalsIgnoreCase("back")) {
							showCard(movieSettingsMenu
									.getPreviusCard());
							moviesMenu.startScrollProcess();
						} else {

							PlayInformation info = (PlayInformation) movieSettingsMenu
									.getMenu()
									.getSelectedValue();
							createMoviePlayer(
									info.getVideoLocation(),
									info.getSubtitleLocaltion(),
									movie);
							moviePlayer
									.setPreviusCard("moviesettingsmenu");
							showCard("movieplayer");

						}
					}

				});

		// Aseta valikko näkyviin
		addCard(movieSettingsMenu, "moviesettingsmenu");
		validate();

	}

	/**
	 * Luo elokuvasoitin annetulle video ja tekstitystiedostolle
	 * 
	 * @param video
	 *            elokuvan sijainti
	 * @param subtitle
	 *            tekstityksen sijainti
	 * @param movie
	 *            elokuva
	 */
	private void createMoviePlayer(String video,
			String subtitle, Movie movie) {

		moviePlayer = new Player();
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// tapahtumia
		moviePlayer.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {

					showCard(moviePlayer.getPreviusCard());
					moviePlayer.release();

				}

			}

		});

		// Aseta soitin näkyviin
		addCard(moviePlayer, "movieplayer");

		// Käynnistä toisto
		moviePlayer.play(video, subtitle);

		// Asetetaan elokuva
		moviePlayer.setMovie(movie);

		validate();

	}

	/**
	 * Luo viestinäkymä.
	 * 
	 * @param message
	 *            viesti
	 */
	private void createMessage(String message) {

		menuMessage = new MenuMessage(message);
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// tapahtumia
		menuMessage.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				System.out.println(menuItem);
				if (menuItem.equalsIgnoreCase("back")) {

					showCard(menuMessage.getPreviusCard());

				}

			}

		});

		// aseta viesti näkyviin
		addCard(menuMessage, "message");

		validate();

	}

	/**
	 * Luo näyttelijä/ihmiset valikko
	 * 
	 * @param actors
	 *            listattavat ihmiset
	 */
	private void createCastMenu(ArrayList<String> actors) {
		final MovieViewImpl self = this;
		castMenu = new MovieGroupMenu(actors);
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// tapahtumia
		castMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(castMenu.getPreviusCard());
				} else {
					if (castListener != null)
						castListener.selected(self);
				}

			}

		});
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// liikkeitä
		castMenu.getMenu().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(
							ListSelectionEvent arg0) {
						// Pyydä esikatselua
						castListener.previewRequest(self);
					}

				});

		// Pyydä esikatselua ensimmäisestä valinnasta
		castListener.previewRequest(self);

		// Näytä valikko
		add(castMenu, "castmenu");

	}

	/**
	 * Luo genre valikko
	 * 
	 * @param genres
	 *            genret
	 */
	private void createGenresMenu(ArrayList<String> genres) {
		final MovieViewImpl self = this;
		genresMenu = new MovieGroupMenu(genres);
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// liikkeitä
		genresMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(genresMenu.getPreviusCard());
				} else {
					if (genreListener != null)
						genreListener.selected(self); // Kerro
														// ohjaimelle
														// valinnasta
				}

			}

		});

		genresMenu.getMenu().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(
							ListSelectionEvent arg0) {
						genreListener.previewRequest(self); // Pyydä
															// esikatselua
															// valinnasta
					}

				});
		genreListener.previewRequest(self); // Pyydä esikatselua
											// ensimmäisestä
											// valinnasta

		// Näytä valikko
		add(genresMenu, "genresmenu");

	}

	/**
	 * Luo vuosi valikko
	 * 
	 * @param years
	 *            vuodet
	 */
	private void createYearsMenu(ArrayList<String> years) {
		final MovieViewImpl self = this;
		yearsMenu = new MovieGroupMenu(years);
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// liikkeitä
		yearsMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(yearsMenu.getPreviusCard());
				} else {
					if (yearListener != null)
						yearListener.selected(self); // Ilmoita
														// valinnasta
														// ohjaimelle
				}

			}

		});

		yearsMenu.getMenu().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(
							ListSelectionEvent arg0) {
						yearListener.previewRequest(self); // Pyydä
															// esikatselua
															// valinnasta
					}

				});

		yearListener.previewRequest(self);// Pyydä ensimmäisestä
											// esikatselua
											// valinnasta
		// näytä vlikko
		add(yearsMenu, "yearsmenu");

	}

	/**
	 * Luo ohjaajavalikko.
	 * 
	 * @param directors
	 */
	private void createDirectorsMenu(ArrayList<String> directors) {
		final MovieViewImpl self = this;
		directorsMenu = new MovieGroupMenu(directors);
		// Lisätään tapahtumakuuntelija kuuntelemaan valikon
		// liikkeitä
		directorsMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(directorsMenu.getPreviusCard());
				} else {
					if (directorListener != null)
						directorListener.selected(self);// Ilmoita
														// valinnasta
														// ohjaimelle
				}

			}

		});

		directorsMenu.getMenu().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(
							ListSelectionEvent arg0) {
						directorListener.previewRequest(self);// Pyydä
																// esikatselua
																// valinnasta
					}

				});

		directorListener.previewRequest(self);// Pyydä
												// ensimmäisestä
												// esikatselua
												// valinnasta

		add(directorsMenu, "directorsmenu");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#setMovieListener(org
	 * .vatvit.movielib.views.MovieListener)
	 */
	@Override
	public void setMovieListener(MovieListener listener) {
		this.movieListener = listener;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#setCastListener(org
	 * .vatvit.movielib.views.GroupListener)
	 */
	@Override
	public void setCastListener(GroupListener listener) {
		this.castListener = listener;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#setDirectorListener
	 * (org.vatvit.movielib.views.GroupListener)
	 */
	@Override
	public void setDirectorListener(GroupListener listener) {
		this.directorListener = listener;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#setYearListener(org
	 * .vatvit.movielib.views.GroupListener)
	 */
	@Override
	public void setYearListener(GroupListener listener) {
		this.yearListener = listener;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#setGenreListener(org
	 * .vatvit.movielib.views.GroupListener)
	 */
	@Override
	public void setGenreListener(GroupListener listener) {
		this.genreListener = listener;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showCast(java.util
	 * .ArrayList)
	 */
	@Override
	public void showCast(ArrayList<String> cast) {
		String previousView = new String(currentView);
		createCastMenu(cast);
		castMenu.setPreviusCard(previousView);
		showCard("castmenu");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showGenres(java.util
	 * .ArrayList)
	 */
	@Override
	public void showGenres(ArrayList<String> genres) {
		String previousView = new String(currentView);
		createGenresMenu(genres);
		genresMenu.setPreviusCard(previousView);
		showCard("genresmenu");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showDirectors(java
	 * .util.ArrayList)
	 */
	@Override
	public void showDirectors(ArrayList<String> directors) {
		String previousView = new String(currentView);
		createDirectorsMenu(directors);
		directorsMenu.setPreviusCard(previousView);
		showCard("directorsmenu");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showYears(java.util
	 * .ArrayList)
	 */
	@Override
	public void showYears(ArrayList<String> years) {
		String previousView = new String(currentView);
		createYearsMenu(years);
		yearsMenu.setPreviusCard(previousView);
		showCard("yearsmenu");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showMovies(java.util
	 * .ArrayList)
	 */
	@Override
	public void showMovies(ArrayList<Movie> movies) {
		String previousView = new String(currentView);
		createMoviesMenu(movies);
		moviesMenu.setPreviusCard(previousView);
		showCard("moviesmenu");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showMovie(org.vatvit
	 * .movielib.objects.Movie)
	 */
	@Override
	public void showMovie(Movie movie) {

		String previousView = new String(currentView);
		createMovieSettingsMenu(movie);
		movieSettingsMenu.setPreviusCard(previousView);
		showCard("moviesettingsmenu");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showCastPreview(java
	 * .util.ArrayList)
	 */
	@Override
	public void showCastPreview(ArrayList<Movie> movies) {
		if (castMenu != null) {
			castMenu.displayInfo(movies);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showGenrePreview(java
	 * .util.ArrayList)
	 */
	@Override
	public void showGenrePreview(ArrayList<Movie> movies) {
		if (genresMenu != null) {
			genresMenu.displayInfo(movies);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showDirectorPreview
	 * (java.util.ArrayList)
	 */
	@Override
	public void showDirectorPreview(ArrayList<Movie> movies) {
		if (directorsMenu != null) {
			directorsMenu.displayInfo(movies);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showYearPreview(java
	 * .util.ArrayList)
	 */
	@Override
	public void showYearPreview(ArrayList<Movie> movies) {
		if (yearsMenu != null) {
			yearsMenu.displayInfo(movies);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#setCloseListener(org
	 * .vatvit.movielib.views.CloseListener)
	 */
	@Override
	public void setCloseListener(CloseListener listener) {
		this.closeListener = listener;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showError(java.lang
	 * .String)
	 */
	@Override
	public void showError(String message) {
		String previousCard = new String(currentView);
		createMessage(message);
		menuMessage.setPreviusCard(previousCard);
		showCard("message");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#getViewName()
	 */
	@Override
	public String getViewName() {
		return "Graafinen käyttöliittymä";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#getGenre()
	 */
	@Override
	public String getGenre() {
		if (genresMenu != null) {
			return (String) genresMenu.getMenu()
					.getSelectedValue();
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#getCast()
	 */
	@Override
	public String getCast() {
		if (castMenu != null) {
			return (String) castMenu.getMenu()
					.getSelectedValue();
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#getDirector()
	 */
	@Override
	public String getDirector() {
		if (directorsMenu != null) {
			return (String) directorsMenu.getMenu()
					.getSelectedValue();
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#getYear()
	 */
	@Override
	public String getYear() {
		if (yearsMenu != null) {
			return (String) yearsMenu.getMenu()
					.getSelectedValue();
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#getMovie()
	 */
	@Override
	public Movie getMovie() {
		if (moviesMenu != null) {
			return (Movie) moviesMenu.getMenu()
					.getSelectedValue();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#getCover()
	 */
	@Override
	public BufferedImage getCover() {
		// Ei käytetä katselukäyttöliittymässä
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#getBackdrop()
	 */
	@Override
	public BufferedImage getBackdrop() {
		// Ei käytetä katselukäyttöliittymässä
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vatvit.movielib.views.MovieView#showLoading(java.lang
	 * .String)
	 */
	@Override
	public void showLoading(String reason) {
		// Ei käytössä

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#hideLoading()
	 */
	@Override
	public void hideLoading() {
		// Ei käytössä
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.MovieView#init()
	 */
	@Override
	public void init() {
		initComponents();
		makeFullScreen();
	}

}
