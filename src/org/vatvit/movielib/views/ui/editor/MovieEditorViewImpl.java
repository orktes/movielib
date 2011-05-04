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

import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieInfoResult;
import org.vatvit.movielib.views.CloseListener;
import org.vatvit.movielib.views.GroupListener;
import org.vatvit.movielib.views.MovieListener;
import org.vatvit.movielib.views.MovieView;

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
	

	@Override
	public void setCloseListener(CloseListener listener) {
		this.closeListener = listener;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void showGenres(ArrayList<String> genres) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDirectors(ArrayList<String> directors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showYears(ArrayList<String> years) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMovies(ArrayList<Movie> movies) {
		moviesList.setListData(new Vector<Movie>(movies));
		validate();
	}

	@Override
	public void showMovie(Movie movie) {
		final MovieEditorViewImpl self = this;
		
		contentPanel.removeAll();
		// contentPanel.add(getMovieWizardPanel(movie), BorderLayout.CENTER);

		JPanel settings = new JPanel();
		settings.setLayout(new BorderLayout());
		settings.setSize(new Dimension(369, 165));

		JLabel movieContent = new JLabel("");
		movieContent.setText("<html><h1>"+movie.toString()+"</h1><br />"+movie.toHTML()+"</html>");
		movieContent.setVerticalAlignment(SwingConstants.TOP);
	
	
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton remove = new JButton();
		remove.setText("Poista");
		remove.addActionListener(new ActionListener(){

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
		edit.setText("Muokkaa");
		edit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MovieWizardPanel wizard = getMovieWizardPanel(selectedMovie);
				contentPanel.removeAll();
				contentPanel.add(wizard);
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

	@Override
	public void showCastPreview(ArrayList<Movie> movies) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showGenrePreview(ArrayList<Movie> movies) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDirectorPreview(ArrayList<Movie> movies) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showYearPreview(ArrayList<Movie> movies) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		setVisible(true);
		setSize(600, 600);
		if (movieListener != null) {
			movieListener.movieList(this);
		}
	}

	@Override
	public void showError(String error) {
		JOptionPane.showMessageDialog(this, error);
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
	public String getViewName() {
		
		return "Editori";
	}

	@Override
	public String getGenre() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDirector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getYear() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie getMovie() {
		return this.selectedMovie;
	}

	@Override
	public BufferedImage getCover() {
		return this.cover;
	}

	@Override
	public BufferedImage getBackdrop() {
		return this.backdrop;
	}

	/**
	 * This is the default constructor
	 */
	public MovieEditorViewImpl() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(652, 256);
		this.setContentPane(getJContentPane());
		this.setTitle("MovieLib 0.1 - Hallinta");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes jContentPane
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
	 * This method initializes movieWizardPanel
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
		movieWizardPanel.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedMovie = movieWizardPanel.getMovie();
				cover = movieWizardPanel.getCoverAsBufferedImage();
				backdrop = movieWizardPanel.getBackdropAsBufferedImage();
				if (movieListener != null) {
					movieListener.movieSaved(self);
				}
				movieListener.movieList(self);
				contentPanel.removeAll();
				contentPanel.repaint();

			}

		});

		return movieWizardPanel;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getContentPanel());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getMoviesList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes moviesList
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

	/**
	 * This method initializes contentPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
		}
		return contentPanel;
	}

	/**
	 * This method initializes buttonPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {

			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			buttonPanel.add(getAddButton());
		}
		return buttonPanel;
	}

	/**
	 * This method initializes addButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText("Lisää");
			addButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					contentPanel.removeAll();
					contentPanel.add(getMovieWizardPanel(null),
							BorderLayout.CENTER);
					contentPanel.validate();
				}

			});
		}
		return addButton;
	}

	

} // @jve:decl-index=0:visual-constraint="92,9"
