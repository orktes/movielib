package org.vatvit.movielib.controllers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.vatvit.movielib.dao.QueryResult;
import org.vatvit.movielib.models.MovieDataRetrieveException;
import org.vatvit.movielib.models.MovieDeleteException;
import org.vatvit.movielib.models.MovieException;
import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.settings.SettingsLoader;

import org.vatvit.movielib.views.CloseListener;
import org.vatvit.movielib.views.GroupListener;

import org.vatvit.movielib.views.MovieListener;

import org.vatvit.movielib.views.MovieView;

/**
 * Sovelluksen ohjainluokka. Yhdistää näkymän ja mallin näkymälle asetettavien
 * kuuntelijoijen avulla.
 * 
 */
public class MovieController {

	private MovieModel model = null;
	private MovieView view = null;

	// KUUNTELIJAT
	class MovieListenerImpl implements MovieListener {

		@Override
		public void movieSaved(MovieView view) {
			view.showLoading(SettingsLoader.getValue("lang.movie_saveing_in_process",
			"Elokuvan tallennus käynnissä"));
			try {
				Movie movie = view.getMovie();
				BufferedImage cover = view.getCover();
				BufferedImage backdrop = view.getBackdrop();

				if ((movie.getId() > 0 && model.allowEdit())
						|| movie.getId() == 0 && model.allowImport()) {
					
					model.saveMovie(movie, cover, backdrop);
					
					view.showError(SettingsLoader.getValue("lang.movie_saved",
							"Elokuva tallennettu"));
				} else {
					view.showError(SettingsLoader
							.getValue("lang.edit_not_allowed",
									"Elokuvien muokkaaminen tai lisäys ei ole sallittu"));
				}
			} catch (MovieException e) {
				view.showError(e.getMessage());
			}
			view.hideLoading();
		}

		@Override
		public void movieSelected(MovieView view) {
			if (view.getMovie() == null)
				System.out.println("Elokuva on null");
			Movie movie = view.getMovie();
			if (movie.getCast() == null || movie.getSubtitles() == null
					|| movie.getGenres() == null) {
				try {
					movie = model.getMovie(movie.getId());
				} catch (MovieDataRetrieveException e) {
					view.showError(e.getMessage());
				}
			}
			view.showMovie(movie);
		}

		@Override
		public void movieDeleted(MovieView view) {
			Movie movie = view.getMovie();
			if (model.allowDelete()) {
				try {
					model.deleteMovie(movie);
					view.showError(SettingsLoader.getValue(
							"lang.movie_deleted", "Elokuva poistettu"));
				} catch (MovieDeleteException e) {
					view.showError(e.getMessage());
				}

			} else {
				view.showError(SettingsLoader.getValue(
						"lang.delete_not_allowed",
						"Elokuvien poisto ei ole sallittu"));
			}
		}

		@Override
		public void movieList(MovieView view) {

			QueryResult<Movie> movieResult;
			try {
				movieResult = model.getMovies(null, null, 0, 0,
						MovieModel.Field.TITLE, false, true, true, true);
				if (movieResult.getResults().size() > 0) {
					view.showMovies(movieResult.getResults());
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_movies_found", "Elokuvia ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}
	};

	class CastListener implements GroupListener {

		@Override
		public void listRequest(MovieView view) {
			ArrayList<String> cast;
			try {
				cast = model.getAllCast();
				if (cast.size() > 0) {
					view.showCast(cast);
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_cast_found", "Näyttelijöitä ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}
		}

		@Override
		public void selected(MovieView view) {
			try {
				String cast = view.getCast();
				QueryResult<Movie> movieResult = model.getMovies(
						MovieModel.Field.CAST, cast, 0, 0,
						MovieModel.Field.TITLE, false, true, true, true);
				if (movieResult.getResults().size() > 0) {
					view.showMovies(movieResult.getResults());
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_movies_found", "Elokuvia ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

		@Override
		public void previewRequest(MovieView view) {
			try {
				String cast = view.getCast();
				QueryResult<Movie> movieResult = model.getMovies(
						MovieModel.Field.CAST, cast, 0, 24,
						MovieModel.Field.TITLE, false, true, true, true);
				view.showCastPreview(movieResult.getResults());
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

	};

	class GenreListener implements GroupListener {

		@Override
		public void listRequest(MovieView view) {
			try {
				ArrayList<String> genres = model.getAllGenres();

				if (genres.size() > 0) {
					view.showGenres(genres);
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_genres_found", "Kategorioita ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}
		}

		@Override
		public void selected(MovieView view) {
			try {
				String genre = view.getGenre();
				QueryResult<Movie> movieResult = model.getMovies(
						MovieModel.Field.GENRE, genre, 0, 0,
						MovieModel.Field.TITLE, false, true, true, true);
				if (movieResult.getResults().size() > 0) {
					view.showMovies(movieResult.getResults());
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_movies_found", "Elokuvia ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

		@Override
		public void previewRequest(MovieView view) {
			try {
				String genre = view.getGenre();
				QueryResult<Movie> movieResult = model.getMovies(
						MovieModel.Field.GENRE, genre, 0, 24,
						MovieModel.Field.TITLE, false, true, true, true);
				view.showGenrePreview(movieResult.getResults());
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

	};

	class DirectorListener implements GroupListener {

		@Override
		public void selected(MovieView view) {
			try {
				String director = view.getDirector();
				QueryResult<Movie> movieResult = model.getMovies(
						MovieModel.Field.DIRECTOR, director, 0, 0,
						MovieModel.Field.TITLE, false, true, true, true);
				if (movieResult.getResults().size() > 0) {
					view.showMovies(movieResult.getResults());
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_movies_found", "Elokuvia ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

		@Override
		public void listRequest(MovieView view) {
			try {
				ArrayList<String> directors = model.getAllDirectors();

				if (directors.size() > 0) {
					view.showDirectors(directors);
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_directors_found", "Ohjaajia ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

		@Override
		public void previewRequest(MovieView view) {
			try {
				String director = view.getDirector();
				QueryResult<Movie> movieResult = model.getMovies(
						MovieModel.Field.DIRECTOR, director, 0, 24,
						MovieModel.Field.TITLE, false, true, true, true);
				view.showDirectorPreview(movieResult.getResults());
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

	};

	class YearListener implements GroupListener {

		@Override
		public void listRequest(MovieView view) {
			try {
				ArrayList<String> years = model.getAllYears();
				if (years.size() > 0) {
					view.showYears(years);
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_years_found", "Vuosia ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}
		}

		@Override
		public void selected(MovieView view) {
			try {
				String year = view.getYear();
				QueryResult<Movie> movieResult = model.getMovies(
						MovieModel.Field.YEAR, year, 0, 0,
						MovieModel.Field.TITLE, false, true, true, true);
				if (movieResult.getResults().size() > 0) {
					view.showMovies(movieResult.getResults());
				} else {
					view.showError(SettingsLoader.getValue(
							"lang.no_movies_found", "Elokuvia ei löytynyt"));
				}
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

		@Override
		public void previewRequest(MovieView view) {
			try {
				String year = view.getYear();
				QueryResult<Movie> movieResult = model.getMovies(
						MovieModel.Field.YEAR, year, 0, 24,
						MovieModel.Field.TITLE, false, true, true, true);
				view.showYearPreview(movieResult.getResults());
			} catch (MovieDataRetrieveException e) {
				view.showError(e.getMessage());
			}

		}

	};

	public class CloseListenerImpl implements CloseListener {

		@Override
		public void close(MovieView view) {
			System.exit(0);
		}

	}

	/**
	 * Ohjain -luokka. Käsittelee näkymältä tulevat käskyt ja muokkaa tai hakee
	 * tietoa mallista niiden mukaisesti
	 * 
	 * @param model
	 *            Käytettävä malli
	 * @param view
	 *            Käytettävä näkymä
	 */
	public MovieController(MovieModel model, MovieView view) {
		this.model = model;
		this.view = view;
		System.out.println("Käytetään mallia: " + model.getModelName());
		System.out.println("Käytetään käyttöliittymää: " + view.getViewName());
		setListenersForView();
		view.init();
	}

	/**
	 * Palauttaa ohjaimen käyttämän mallin
	 * 
	 * @return malli
	 */
	public MovieModel getModel() {
		return model;
	}

	/**
	 * Asettaa ohjaimen käyttämän mallin
	 * 
	 * @param model
	 */
	public void setModel(MovieModel model) {
		this.model = model;
	}

	/**
	 * Palauttaa ohjaimen käyttämän näkymän
	 * 
	 * @return näykmä
	 */
	public MovieView getView() {
		return view;
	}

	/**
	 * Asetaa ohjaimen käyttämän näkymän
	 * 
	 * @param view
	 *            näkymä
	 */
	public void setView(MovieView view) {
		this.view = view;
		setListenersForView();
	}

	/**
	 * Asetaa kuuntelijat näkymälle
	 */
	private void setListenersForView() {
		view.setMovieListener(new MovieListenerImpl());
		view.setCloseListener(new CloseListenerImpl());
		view.setCastListener(new CastListener());
		view.setDirectorListener(new DirectorListener());
		view.setYearListener(new YearListener());
		view.setGenreListener(new GenreListener());
	}

}
