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

import org.vatvit.movielib.views.CloseListener;
import org.vatvit.movielib.views.GroupListener;
import org.vatvit.movielib.views.MovieListener;
import org.vatvit.movielib.views.MovieView;

import org.vatvit.movielib.views.ui.menu.MovieGroupMenu;
import org.vatvit.movielib.views.ui.menu.MainMenu;
import org.vatvit.movielib.views.ui.menu.MovieSettingsMenu;
import org.vatvit.movielib.views.ui.menu.MoviesMenu;
import org.vatvit.movielib.views.ui.menu.PlayInformation;
import org.vatvit.movielib.views.ui.menu.panels.MenuImage;
import org.vatvit.movielib.views.ui.menu.panels.MenuMessage;
import org.vatvit.movielib.views.ui.player.Player;

public class MovieViewImpl extends JFrame implements MovieView {

	private MovieListener movieListener;
	private CloseListener closeListener;
	private GroupListener castListener;
	private GroupListener yearListener;
	private GroupListener directorListener;
	private GroupListener genreListener;
	
	//NÄKYMÄT
	private MainMenu mainMenu;
	private MoviesMenu moviesMenu;
	private MovieGroupMenu castMenu;
	private MovieGroupMenu directorsMenu;
	private MovieGroupMenu yearsMenu;
	private MovieGroupMenu genresMenu;
	private MovieSettingsMenu movieSettingsMenu;
	private MenuMessage menuMessage;
	
	private Player moviePlayer;
	private String currentView;

	public MovieViewImpl() {

	}

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

	private void initComponents() {
		setTitle("MovieLib 0.1");
		setBackground(new Color(0, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new java.awt.CardLayout());

		createMainMenu();
		
	}
	private void addCard(JPanel panel, String name) {
		currentView=name;
		getContentPane().add(panel, name);
	}
	
	private void showCard(String name) {
		currentView=name;
		CardLayout layout = (CardLayout)getContentPane().getLayout();
		layout.show(getContentPane(), name);	
		if(name.equals("moviesmenu")) {
			moviesMenu.focus();
		} else if(name.equals("castmenu")) {
			castMenu.focus();
		} else if(name.equals("genresmenu")) {
			genresMenu.focus();
		} else if(name.equals("yearsmenu")) {
			yearsMenu.focus();
		} else if(name.equals("directorsmenu")) {
			directorsMenu.focus();
		} else if(name.equals("mainmenu")) {
			mainMenu.focus();
		} else if(name.equals("moviesettingsmenu")) {
			movieSettingsMenu.focus();
		}else if(name.equals("movieplayer")) {
			moviePlayer.focus();
		}else if(name.equals("message")) {
			menuMessage.focus();
		}
	}
	private void createMainMenu() {

		
		
		ArrayList<String> menuItems = new ArrayList<String>();
		menuItems.add(SettingsLoader.getValue("lang.movies","Elokuvat"));
		menuItems.add(SettingsLoader.getValue("lang.cast","Näyttelijät"));
		menuItems.add(SettingsLoader.getValue("lang.directors","Ohjaajat"));
		menuItems.add(SettingsLoader.getValue("lang.genres","Genret"));
		menuItems.add(SettingsLoader.getValue("lang.years","Vuodet"));
		menuItems.add(SettingsLoader.getValue("lang.quit","Lopeta"));
		
		mainMenu = new MainMenu(menuItems);
		final MovieViewImpl self = this;
		mainMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase(SettingsLoader.getValue("lang.quit","Lopeta"))||menuItem.equalsIgnoreCase("back")) {
					if (closeListener != null)
						closeListener.close(self);
				} else if (menuItem.equalsIgnoreCase(SettingsLoader.getValue("lang.movies","Elokuvat"))) {
					if (movieListener != null)
						movieListener.movieList(self);
				} else if (menuItem.equalsIgnoreCase(SettingsLoader.getValue("lang.cast","Näyttelijät"))) {	
					if (castListener != null)
						castListener.listRequest(self);
				} else if (menuItem.equalsIgnoreCase(SettingsLoader.getValue("lang.genres","Genret"))) {
					if (genreListener != null)
						genreListener.listRequest(self);
				} else if (menuItem.equalsIgnoreCase(SettingsLoader.getValue("lang.years","Vuodet"))) {
					if (yearListener != null)
						yearListener.listRequest(self);
				}else if (menuItem.equalsIgnoreCase(SettingsLoader.getValue("lang.directors","Ohjaajat"))) {
					if (directorListener != null)
						directorListener.listRequest(self);
				}

			}
			

		});
		
		mainMenu.getMenu().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String item = (String) mainMenu.getMenu().getSelectedValue();
				JPanel infoPanel = mainMenu.getInfoPanel();
				infoPanel.setLayout(new java.awt.GridLayout());
				infoPanel.removeAll();
				infoPanel.add(new MenuImage("images"
						+ File.separator
						+ item.toLowerCase().replaceAll("ä", "a").replaceAll("ö", "o")
								.replaceAll("å", "o") + ".png", true));
				infoPanel.validate();
			}
			
		});
		
		//Asetetaan ensimmöinen kuva
		String item = (String) mainMenu.getMenu().getModel().getElementAt(0);
		JPanel infoPanel = mainMenu.getInfoPanel();
		infoPanel.setLayout(new java.awt.GridLayout());
		infoPanel.removeAll();
		infoPanel.add(new MenuImage("images"
				+ File.separator
				+ item.toLowerCase().replaceAll("ä", "a").replaceAll("ö", "o")
						.replaceAll("å", "o") + ".png", true));
		infoPanel.validate();
		
		addCard(mainMenu, "mainmenu");
		

	}

	private void createMoviesMenu(ArrayList<Movie> movies) {
		
	
		final MovieViewImpl self = this;
		moviesMenu = new MoviesMenu(movies);
		moviesMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					moviesMenu.stopScrollProcess();
					showCard(moviesMenu.getPreviusCard());
				} else {
					moviesMenu.stopScrollProcess();
					Movie movie = (Movie)moviesMenu.getMenu().getSelectedValue();
					movieListener.movieSelected(self);
					
				}
			}

		});
		
		
		addCard(moviesMenu, "moviesmenu");
		

	}
	private void createMovieSettingsMenu(Movie movie) {
		movieSettingsMenu = new MovieSettingsMenu(movie, movie.getBackdrop(MovieModel.ImageSize.LARGE));
		
		
		movieSettingsMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(movieSettingsMenu.getPreviusCard());
					moviesMenu.startScrollProcess();
				} else {
					
					PlayInformation info = (PlayInformation)movieSettingsMenu.getMenu().getSelectedValue();
					createMoviePlayer(info.getVideoLocation(), info.getSubtitleLocaltion());
					moviePlayer.setPreviusCard("moviesettingsmenu");
					showCard("movieplayer");
					
				}
			}

		});
		
		
		
		addCard(movieSettingsMenu, "moviesettingsmenu");
		validate();

	}
	private void createMoviePlayer(String video, String subtitle) {
		
		moviePlayer = new Player(this);
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
		
		addCard(moviePlayer, "movieplayer");	
		moviePlayer.play(video, subtitle);	
		
		
		
		validate();
		

	}
private void createMessage(String message) {
		
		menuMessage = new MenuMessage(message);
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
		
		addCard(menuMessage, "message");	
		
		
		validate();
		

	}

	private void createCastMenu(ArrayList<String> actors) {
		final MovieViewImpl self = this;
		castMenu = new MovieGroupMenu(actors);
		castMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(castMenu.getPreviusCard());
				} else {
					if(castListener!=null)
						castListener.selected(self);
				}

			}

		});

		castMenu.getMenu().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String item = (String)castMenu.getMenu().getSelectedValue();
				castListener.previewRequest(self);
			}
			
		});
		castListener.previewRequest(self);
		add(castMenu, "castmenu");
		

	}

	private void createGenresMenu(ArrayList<String> genres) {
		final MovieViewImpl self = this;
		genresMenu = new MovieGroupMenu(genres);
		genresMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(genresMenu.getPreviusCard());
				} else {
					if(genreListener!=null)
						genreListener.selected(self);
				}

			}

		});

		genresMenu.getMenu().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String item = (String)genresMenu.getMenu().getSelectedValue();
				genreListener.previewRequest(self);
			}
			
		});
		genreListener.previewRequest(self);
		add(genresMenu, "genresmenu");

	}

	private void createYearsMenu(ArrayList<String> years) {
		final MovieViewImpl self = this;
		yearsMenu = new MovieGroupMenu(years);
		yearsMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(yearsMenu.getPreviusCard());
				} else {
					if(yearListener!=null)
						yearListener.selected(self);
				}

			}

		});
		
	yearsMenu.getMenu().addListSelectionListener(new ListSelectionListener() {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			String item = (String)yearsMenu.getMenu().getSelectedValue();
			yearListener.previewRequest(self);
		}
		
	});

	yearListener.previewRequest(self);
	
		add(yearsMenu, "yearsmenu");

	}

	private void createDirectorsMenu(ArrayList<String> directors) {
		final MovieViewImpl self = this;
		directorsMenu = new MovieGroupMenu(directors);
		directorsMenu.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String menuItem = arg0.getActionCommand();
				if (menuItem.equalsIgnoreCase("back")) {
					showCard(directorsMenu.getPreviusCard());
				} else {
					if(directorListener!=null)
						directorListener.selected(self);
				}

			}

		});
		
		directorsMenu.getMenu().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String item = (String)directorsMenu.getMenu().getSelectedValue();
				directorListener.previewRequest(self);
			}
			
		});
		
		directorListener.previewRequest(self);

		add(directorsMenu, "directorsmenu");

	}
	
	@Override
	public void setMovieListener(MovieListener listener) {
		this.movieListener = listener;

	}
	
	
	
	@Override
	public void setCastListener(GroupListener listener) {
		this.castListener = listener;
		
	}

	@Override
	public void setDirectorListener(GroupListener listener) {
		this.directorListener = listener;
		
	}

	@Override
	public void setYearListener(GroupListener listener) {
		this.yearListener = listener;
		
	}

	@Override
	public void setGenreListener(GroupListener listener) {
		this.genreListener = listener;
		
	}

	@Override
	public void showCast(ArrayList<String> cast) {
		String previousView = new String(currentView);
		createCastMenu(cast);
		castMenu.setPreviusCard(previousView);
		showCard("castmenu");
		
	}

	@Override
	public void showGenres(ArrayList<String> genres) {
		String previousView = new String(currentView);
		createGenresMenu(genres);
		genresMenu.setPreviusCard(previousView);
		showCard("genresmenu");
		
	}

	@Override
	public void showDirectors(ArrayList<String> directors) {
		String previousView = new String(currentView);
		createDirectorsMenu(directors);
		directorsMenu.setPreviusCard(previousView);
		showCard("directorsmenu");
		
	}

	@Override
	public void showYears(ArrayList<String> years) {
		String previousView = new String(currentView);
		createYearsMenu(years);
		yearsMenu.setPreviusCard(previousView);
		showCard("yearsmenu");
		
	}

	@Override
	public void showMovies(ArrayList<Movie> movies) {
		String previousView = new String(currentView);
		createMoviesMenu(movies);
		moviesMenu.setPreviusCard(previousView);
		showCard("moviesmenu");	
		
	}

	@Override
	public void showMovie(Movie movie) {
		
		String previousView = new String(currentView);
		createMovieSettingsMenu(movie);
		movieSettingsMenu.setPreviusCard(previousView);
		showCard("moviesettingsmenu");	
		
	}
	
	

	@Override
	public void showCastPreview(ArrayList<Movie> movies) {
		if(castMenu!=null) {
			castMenu.displayInfo(movies);
		}
	}

	@Override
	public void showGenrePreview(ArrayList<Movie> movies) {
		if(genresMenu!=null) {
			genresMenu.displayInfo(movies);
		}
		
	}

	@Override
	public void showDirectorPreview(ArrayList<Movie> movies) {
		if(directorsMenu!=null) {
			directorsMenu.displayInfo(movies);
		}
		
	}

	@Override
	public void showYearPreview(ArrayList<Movie> movies) {
		if(yearsMenu!=null) {
			yearsMenu.displayInfo(movies);
		}
		
	}

	@Override
	public void setCloseListener(CloseListener listener) {
		this.closeListener = listener;

	}

	@Override
	public void showError(String message) {
		String previousCard = new String(currentView);
		createMessage(message);
		menuMessage.setPreviusCard(previousCard);
		showCard("message");

	}

	@Override
	public String getViewName() {
		return "Graafinen käyttöliittymä";
	}

	
	
	@Override
	public String getGenre() {
		if(genresMenu!=null) {
			return (String)genresMenu.getMenu().getSelectedValue();
		}
		return "";
	}

	@Override
	public String getCast() {
		if(castMenu!=null) {
			return (String)castMenu.getMenu().getSelectedValue();
		}
		return "";
	}

	@Override
	public String getDirector() {
		if(directorsMenu!=null) {
			return (String)directorsMenu.getMenu().getSelectedValue();
		}
		return "";
	}

	@Override
	public String getYear() {
		if(yearsMenu!=null) {
			return (String)yearsMenu.getMenu().getSelectedValue();
		}
		return "";
	}

	@Override
	public Movie getMovie() {
		if(moviesMenu!=null) {
			return (Movie)moviesMenu.getMenu().getSelectedValue();
		}
		return null;
	}

	@Override
	public BufferedImage getCover() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage getBackdrop() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void showLoading(String reason) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideLoading() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void init() {
		initComponents();
		makeFullScreen();
	}




}
