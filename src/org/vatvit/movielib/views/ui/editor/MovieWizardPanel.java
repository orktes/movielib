package org.vatvit.movielib.views.ui.editor;

import java.awt.CardLayout;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;

import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.objects.MovieInfoResult;
import org.vatvit.movielib.objects.MovieSubtitle;
import org.vatvit.movielib.tools.MovieInfoTools;
import org.vatvit.movielib.views.ui.editor.panels.MovieCastPanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieDetailsPanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieFilesPanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieGenrePanel;
import org.vatvit.movielib.views.ui.editor.panels.MovieInformationSelectPanel;
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

public class MovieWizardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MovieNamePanel movieName = null;
	private MovieInformationSelectPanel movieInformationSelect = null;
	private MovieDetailsPanel movieDetailsPanel = null;
	private MovieCastPanel movieCastPanel = null;
	private MovieSubtitlesPanel movieSubtitlesPanel = null;
	private MovieFilesPanel movieFilesPanel = null;
	private MovieGenrePanel movieGenrePanel = null;

	private MovieInfoResult selectedMovieInfo = null;

	private ActionListener al = null;

	// @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public MovieWizardPanel() {
		this(new MovieInfoResult(new Movie(), null, null));
	}

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

	}

	private JPanel getMovieNamePanel() {
		movieName = new MovieNamePanel();
		if (selectedMovieInfo.getMovie() != null
				&& selectedMovieInfo.getMovie().getTitle() != null) {
			movieName.setMovieName(selectedMovieInfo.getMovie().getTitle());
			movieName.validate();
		}

		final MovieWizardPanel self = this;
		movieName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("cancel")) {
					self.setVisible(false);
				} else if (arg0.getActionCommand().equalsIgnoreCase("search")) {
					String movieTitle = movieName.getMovieName();
					ArrayList<MovieInfoResult> movies = MovieInfoTools
							.getMovieInfo(movieTitle);

					if (movies.size() == 1) {
						selectedMovieInfo = movies.get(0);
						self.add(getMovieDetailsPanel("moviename"),
								"moviedetails");
						CardLayout layout = (CardLayout) self.getLayout();
						layout.show(self, "moviedetails");
					} else if (movies.size() > 1) {
						self.add(getMovieInformationSelectPanel(movies),
								"movieselect");
						CardLayout layout = (CardLayout) self.getLayout();
						layout.show(self, "movieselect");
					} else {

						if (selectedMovieInfo.getMovie() == null) {
							selectedMovieInfo.setMovie(new Movie());
						}

						selectedMovieInfo.getMovie().setTitle(movieTitle);
						self.add(getMovieDetailsPanel("moviename"),
								"moviedetails");
						CardLayout layout = (CardLayout) self.getLayout();
						layout.show(self, "moviedetails");
					}

				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					String movieTitle = movieName.getMovieName();

					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}

					selectedMovieInfo.getMovie().setTitle(movieTitle);
					self.add(getMovieDetailsPanel("moviename"), "moviedetails");
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviedetails");

				}

			}

		});

		return movieName;
	}

	private JPanel getMovieInformationSelectPanel(
			ArrayList<MovieInfoResult> movies) {
		movieInformationSelect = new MovieInformationSelectPanel(movies);
		final MovieWizardPanel self = this;
		movieInformationSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviename");
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					selectedMovieInfo = movieInformationSelect
							.getSelectedMovie();
					self.add(getMovieDetailsPanel("movieselect"),
							"moviedetails");
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviedetails");
				}

			}

		});
		return movieInformationSelect;
	}

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
				System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, previousCard);
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
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviegenres");

				}

			}

		});
		return movieDetailsPanel;

	}

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
				System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviedetails");
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}
					selectedMovieInfo.getMovie().setGenres(
							movieGenrePanel.getMovieGenre());

					self.add(getMovieCastPanel(), "moviecast");
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviecast");

				}

			}

		});

		return movieGenrePanel;

	}

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
				System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviegenres");
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}
					selectedMovieInfo.getMovie().setCast(
							movieCastPanel.getMovieCast());

					self.add(getMovieSubtitlesPanel(), "moviesubtitles");
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviesubtitles");

				}

			}

		});

		return movieCastPanel;

	}

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
				System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviecast");
				} else if (arg0.getActionCommand().equalsIgnoreCase("next")) {
					if (selectedMovieInfo.getMovie() == null) {
						selectedMovieInfo.setMovie(new Movie());
					}
					selectedMovieInfo.getMovie().setSubtitles(
							movieSubtitlesPanel.getMovieSubtitle());

					self.add(getMovieFilesPanel(), "moviefiles");
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviefiles");

				}

			}

		});

		return movieSubtitlesPanel;

	}

	private JPanel getMovieFilesPanel() {
		movieFilesPanel = new MovieFilesPanel();

		if (selectedMovieInfo.getMovie() != null) {
			movieFilesPanel.setVideoLocation(selectedMovieInfo.getMovie()
					.getLocation());
		}
		movieFilesPanel.setCoverLocation(selectedMovieInfo.getCover());
		movieFilesPanel.setBackdropLocation(selectedMovieInfo.getBackdrop());

		final MovieWizardPanel self = this;
		movieFilesPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(arg0.getActionCommand());
				if (arg0.getActionCommand().equalsIgnoreCase("back")) {
					CardLayout layout = (CardLayout) self.getLayout();
					layout.show(self, "moviesubtitles");
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

					if (al != null) {
						ActionEvent actionEvent = new ActionEvent(this,
								ActionEvent.ACTION_PERFORMED, "savemovie");
						al.actionPerformed(actionEvent);
					}

				}

			}

		});

		return movieFilesPanel;

	}

	public void setActionListener(ActionListener al) {
		this.al = al;
	}

	public void setMovie(Movie movie) {
		selectedMovieInfo.setMovie(movie);
	}

	public Movie getMovie() {
		return selectedMovieInfo.getMovie();
	}

	public void setCover(String cover) {
		selectedMovieInfo.setCover(cover);
	}

	public void setBackdrop(String backdrop) {
		selectedMovieInfo.setBackdrop(backdrop);
	}

	public String getCover() {
		return selectedMovieInfo.getCover();
	}

	public String getBackdrop() {
		return selectedMovieInfo.getBackdrop();
	}

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

	public static void main(String[] args) {
		MovieWizardPanel wizard = new MovieWizardPanel();
		JFrame frame = new JFrame();
		frame.add(wizard);
		frame.setSize(544, 600);
		frame.setVisible(true);
	}

} // @jve:decl-index=0:visual-constraint="169,17"
