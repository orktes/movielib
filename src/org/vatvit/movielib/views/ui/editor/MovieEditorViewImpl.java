package org.vatvit.movielib.views.ui.editor;

import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

import org.vatvit.movielib.models.MovieModel.ImageSize;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieInfoResult;
import org.vatvit.movielib.settings.SettingsLoader;
import org.vatvit.movielib.views.CloseListener;
import org.vatvit.movielib.views.GroupListener;
import org.vatvit.movielib.views.MovieListener;
import org.vatvit.movielib.views.MovieView;
import org.vatvit.movielib.views.ui.menu.panels.MovieInfo;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridBagConstraints;

/**
 * Elokuvien muokkaamiseen tarkoitettu käyttöliittymä
 *
 */
public class MovieEditorViewImpl extends JFrame implements MovieView {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private MovieWizardPanel movieWizardPanel = null; // @jve:decl-index=0:visual-constraint="817,37"

	private MovieListener movieListener;
	private CloseListener closeListener; // @jve:decl-index=0:
	private GroupListener castListener;
	private GroupListener yearListener;
	private GroupListener directorListener;
	private GroupListener genreListener;
	private JSplitPane jSplitPane = null;
	private JScrollPane jScrollPane = null;
	private JList moviesList = null;
	private JPanel contentPanel = null;
	private JPanel buttonPanel = null; // @jve:decl-index=0:visual-constraint="862,185"
	private JButton addButton = null;
	private Movie selectedMovie = null;
	private BufferedImage cover = null;
	private BufferedImage backdrop = null;
	private JLabel titleLabel = null;
	private JFrame loadingFrame=null;
	private MovieInfo movieContent = null;

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#setCloseListener(org.vatvit.movielib.views.CloseListener)
	 */
	@Override
	public void setCloseListener(CloseListener listener) {
		this.closeListener = listener;

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#setMovieListener(org.vatvit.movielib.views.MovieListener)
	 */
	@Override
	public void setMovieListener(MovieListener listener) {
		this.movieListener = listener;

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#setCastListener(org.vatvit.movielib.views.GroupListener)
	 */
	@Override
	public void setCastListener(GroupListener listener) {
		this.castListener = listener;

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#setDirectorListener(org.vatvit.movielib.views.GroupListener)
	 */
	@Override
	public void setDirectorListener(GroupListener listener) {
		this.directorListener = listener;

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#setYearListener(org.vatvit.movielib.views.GroupListener)
	 */
	@Override
	public void setYearListener(GroupListener listener) {
		this.yearListener = listener;

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#setGenreListener(org.vatvit.movielib.views.GroupListener)
	 */
	@Override
	public void setGenreListener(GroupListener listener) {
		this.genreListener = listener;

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showCast(java.util.ArrayList)
	 */
	@Override
	public void showCast(ArrayList<String> cast) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showGenres(java.util.ArrayList)
	 */
	@Override
	public void showGenres(ArrayList<String> genres) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showDirectors(java.util.ArrayList)
	 */
	@Override
	public void showDirectors(ArrayList<String> directors) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showYears(java.util.ArrayList)
	 */
	@Override
	public void showYears(ArrayList<String> years) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showMovies(java.util.ArrayList)
	 */
	@Override
	public void showMovies(ArrayList<Movie> movies) {
		moviesList.setListData(new Vector<Movie>(movies));
		validate();
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showMovie(org.vatvit.movielib.objects.Movie)
	 */
	@Override
	public void showMovie(Movie movie) {
		final MovieEditorViewImpl self = this;

		if(movieContent!=null) {
			movieContent.stopScrollProcess();
		}
		
		contentPanel.removeAll();
		// contentPanel.add(getMovieWizardPanel(movie), BorderLayout.CENTER);

		JPanel settings = new JPanel();
		settings.setLayout(new BorderLayout());
		settings.setSize(new Dimension(369, 165));

		movieContent = new MovieInfo(movie, movie.getCover(ImageSize.SMALL));
		movieContent.startScrollProcess();

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton remove = new JButton();
		remove.setText(SettingsLoader.getValue("lang.remove", "Poista"));
		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				movieListener.movieDeleted(self);
				contentPanel.removeAll();
				contentPanel.repaint();
				moviesList.setListData(new Vector<Movie>());
				validate();
				movieListener.movieList(self);
			}

		});
		JButton edit = new JButton();
		edit.setText(SettingsLoader.getValue("lang.edit", "Muokkaa"));
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MovieWizardPanel wizard = getMovieWizardPanel(selectedMovie);
				if(movieContent!=null) {
					movieContent.stopScrollProcess();
				}
				contentPanel.removeAll();
				contentPanel.add(titleLabel, BorderLayout.NORTH);
				contentPanel.add(wizard, BorderLayout.CENTER);
				contentPanel.validate();
			}

		});

		buttons.add(remove);
		buttons.add(edit);
		settings.add(buttons, BorderLayout.SOUTH);
		settings.add(movieContent, BorderLayout.CENTER);

		contentPanel.add(settings);
		contentPanel.validate();
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showCastPreview(java.util.ArrayList)
	 */
	@Override
	public void showCastPreview(ArrayList<Movie> movies) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showGenrePreview(java.util.ArrayList)
	 */
	@Override
	public void showGenrePreview(ArrayList<Movie> movies) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showDirectorPreview(java.util.ArrayList)
	 */
	@Override
	public void showDirectorPreview(ArrayList<Movie> movies) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showYearPreview(java.util.ArrayList)
	 */
	@Override
	public void showYearPreview(ArrayList<Movie> movies) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#init()
	 */
	@Override
	public void init() {
		setVisible(true);
		setSize(1024, 700);
		if (movieListener != null) {
			movieListener.movieList(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showError(java.lang.String)
	 */
	@Override
	public void showError(String error) {
		JOptionPane.showMessageDialog(this, error);
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#showLoading(java.lang.String)
	 */
	@Override
	public void showLoading(String reason) {
		loadingFrame = new JFrame(reason);
		loadingFrame.setLayout(new BorderLayout());
		JLabel messageLabel = new JLabel();
		messageLabel.setText(reason);
		loadingFrame.add(messageLabel, BorderLayout.CENTER);
		loadingFrame.setSize(450, 200);
		loadingFrame.validate();
		loadingFrame.setVisible(true);

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#hideLoading()
	 */
	@Override
	public void hideLoading() {
		if(loadingFrame != null) {
			loadingFrame.setVisible(false);
		}

	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#getViewName()
	 */
	@Override
	public String getViewName() {

		return "Editori";
	}

	@Override
	public String getGenre() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#getCast()
	 */
	@Override
	public String getCast() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#getDirector()
	 */
	@Override
	public String getDirector() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#getYear()
	 */
	@Override
	public String getYear() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#getMovie()
	 */
	@Override
	public Movie getMovie() {
		return this.selectedMovie;
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#getCover()
	 */
	@Override
	public BufferedImage getCover() {
		return this.cover;
	}

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.MovieView#getBackdrop()
	 */
	@Override
	public BufferedImage getBackdrop() {
		return this.backdrop;
	}

	/**
	 * Luo tyhjän ilmentymän
	 */
	public MovieEditorViewImpl() {
		super();
		initialize();
	}

	/**
	 * Alusta näkymä
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(652, 256);
		this.setContentPane(getJContentPane());
		this.setTitle("MovieLib " + SettingsLoader.getValue("version", "")
				+ " - "
				+ SettingsLoader.getValue("lang.management", "Hallinta"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		titleLabel = new JLabel("");

	}

	/**
	 * Luo sisältö paneeli
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * Luo muokkaus/lisäys wizard
	 * 
	 * @return org.vatvit.movielib.views.ui.editor.MovieWizardPanel
	 */
	private MovieWizardPanel getMovieWizardPanel(Movie movie) {
		if (movie == null) {
			movieWizardPanel = new MovieWizardPanel();
		} else {
			movieWizardPanel = new MovieWizardPanel(new MovieInfoResult(movie,
					null, null));
		}

		final MovieEditorViewImpl self = this;
		//Kuunnellaan tapahtumia
		movieWizardPanel.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equalsIgnoreCase("save")) {
					selectedMovie = movieWizardPanel.getMovie();
					cover = movieWizardPanel.getCoverAsBufferedImage();
					backdrop = movieWizardPanel.getBackdropAsBufferedImage();
					if (movieListener != null) {
						movieListener.movieSaved(self);
					}
					movieListener.movieList(self);
					contentPanel.removeAll();
					contentPanel.repaint();
				} else if (e.getActionCommand().equalsIgnoreCase("cancel")) {
					contentPanel.removeAll();
					contentPanel.repaint();
					movieListener.movieList(self);
				}

			}

		});
		//Kuunnellaan näkymän vaihtoja
		movieWizardPanel.setViewChangeListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				titleLabel.setText(SettingsLoader.getValue("lang."+e.getActionCommand()+"_title",e.getActionCommand()));

			}
		});

		return movieWizardPanel;
	}

	
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getContentPanel());
		}
		return jSplitPane;
	}

	
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getMoviesList());
		}
		return jScrollPane;
	}

	/**
	 * Luo elokuva lista
	 * 
	 * @return javax.swing.JList
	 */
	private JList getMoviesList() {

		if (moviesList == null) {
			moviesList = new JList();
			final MovieEditorViewImpl self = this;
			moviesList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					if ((Movie) moviesList.getSelectedValue() != null) {
						selectedMovie = (Movie) moviesList.getSelectedValue();
						cover = null;
						backdrop = null;
						movieListener.movieSelected(self);
					}

				}

			});
		}
		return moviesList;
	}

	
	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
		}
		return contentPanel;
	}

	
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {

			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			buttonPanel.add(getAddButton());
		}
		return buttonPanel;
	}

	
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText(SettingsLoader.getValue("lang.add", "Lisää"));
			addButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					contentPanel.removeAll();
					contentPanel.add(titleLabel, BorderLayout.NORTH);
					contentPanel.add(getMovieWizardPanel(null),
							BorderLayout.CENTER);
					contentPanel.validate();
				}

			});
		}
		return addButton;
	}

} // @jve:decl-index=0:visual-constraint="92,9"
