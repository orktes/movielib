package org.vatvit.movielib.controllers;


import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.vatvit.movielib.dao.QueryResult;
import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.settings.SettingsLoader;

import org.vatvit.movielib.views.CloseListener;
import org.vatvit.movielib.views.GroupListener;


import org.vatvit.movielib.views.MovieListener;

import org.vatvit.movielib.views.MovieView;

public class MovieController {

private MovieModel model = null;
private MovieView view = null;

//KUUNTELIJAT
class MovieListenerImpl implements MovieListener {

	@Override
	public void movieSaved(MovieView view) {
		Movie movie = view.getMovie();
		BufferedImage cover = view.getCover();
		BufferedImage backdrop = view.getBackdrop();
		
		if((movie.getId()>0&&model.allowEdit())||movie.getId()==0&&model.allowImport()) {
			model.saveMovie(movie, cover, backdrop);
		} else {
			view.showError(SettingsLoader.getValue("lang.edit_not_allowed","Elokuvien muokkaaminen tai lisäys ei ole sallittu"));
		}
	}

	@Override
	public void movieSelected(MovieView view) {
		if(view.getMovie()==null) System.out.println("Elokuva on null");
		Movie movie = view.getMovie();
		if(movie.getCast()==null||movie.getSubtitles()==null||movie.getGenres()==null) {
			movie = model.getMovie(movie.getId());
		}
		view.showMovie(movie);
	}

	@Override
	public void movieDeleted(MovieView view) {
		Movie movie = view.getMovie();
		if(model.allowDelete()) {
			model.deleteMovie(movie);
		} else {
			view.showError(SettingsLoader.getValue("lang.delete_not_allowed","Elokuvien poisto ei ole sallittu"));
		}
	}

	@Override
	public void movieList(MovieView view) {
		
		QueryResult<Movie> movieResult = model.getMovies(null, null, 0, 0, MovieModel.Field.TITLE, false, true, true, true);
		if(movieResult.getResults().size()>0) {
			view.showMovies(movieResult.getResults());
		} else {
			view.showError(SettingsLoader.getValue("lang.no_movies_found","Elokuvia ei löytynyt"));
		}
	}
};

class CastListener implements GroupListener {

	@Override
	public void listRequest(MovieView view) {
		ArrayList<String> cast = model.getAllCast();
		if(cast.size()>0) {
			view.showCast(cast);
		} else {
			view.showError(SettingsLoader.getValue("lang.no_cast_found","Näyttelijöitä ei löytynyt"));
		}
	}

	@Override
	public void selected(MovieView view) {
		String cast = view.getCast();
		QueryResult<Movie> movieResult = model.getMovies(MovieModel.Field.CAST, cast, 0, 0, MovieModel.Field.TITLE, false, true, true, true);
		if(movieResult.getResults().size()>0) {
			view.showMovies(movieResult.getResults());
		} else {
			view.showError(SettingsLoader.getValue("lang.no_movies_found","Elokuvia ei löytynyt"));
		}
		
	}

	@Override
	public void previewRequest(MovieView view) {
		String cast = view.getCast();
		QueryResult<Movie> movieResult = model.getMovies(MovieModel.Field.CAST, cast, 0, 24, MovieModel.Field.TITLE, false, true, true, true);
		view.showCastPreview(movieResult.getResults());
		
	}

	
};
class GenreListener implements GroupListener {

	@Override
	public void listRequest(MovieView view) {
		ArrayList<String> genres = model.getAllGenres();
	
		if(genres.size()>0) {
			view.showGenres(genres);
		} else {
			view.showError(SettingsLoader.getValue("lang.no_genres_found","Kategorioita ei löytynyt"));
		}
	}

	@Override
	public void selected(MovieView view) {
		String genre = view.getGenre();
		QueryResult<Movie> movieResult = model.getMovies(MovieModel.Field.GENRE, genre, 0, 0, MovieModel.Field.TITLE, false, true, true, true);
		if(movieResult.getResults().size()>0) {
			view.showMovies(movieResult.getResults());
		} else {
			view.showError(SettingsLoader.getValue("lang.no_movies_found","Elokuvia ei löytynyt"));
		}
		
	}

	@Override
	public void previewRequest(MovieView view) {
		String genre = view.getGenre();
		QueryResult<Movie> movieResult = model.getMovies(MovieModel.Field.GENRE, genre, 0, 24, MovieModel.Field.TITLE, false, true, true, true);
		view.showGenrePreview(movieResult.getResults());
		
	}

	
};
class DirectorListener implements GroupListener {

	@Override
	public void selected(MovieView view) {
		String director = view.getDirector();
		QueryResult<Movie> movieResult = model.getMovies(MovieModel.Field.DIRECTOR, director, 0, 0, MovieModel.Field.TITLE, false, true, true, true);
		if(movieResult.getResults().size()>0) {
			view.showMovies(movieResult.getResults());
		} else {
			view.showError(SettingsLoader.getValue("lang.no_movies_found","Elokuvia ei löytynyt"));
		}
		
	}

	@Override
	public void listRequest(MovieView view) {
		ArrayList<String> directors = model.getAllDirectors();
		
		if(directors.size()>0) {
			view.showDirectors(directors);
		} else {
			view.showError(SettingsLoader.getValue("lang.no_directors_found","Ohjaajia ei löytynyt"));
		}
		
	}

	@Override
	public void previewRequest(MovieView view) {
		String director = view.getDirector();
		QueryResult<Movie> movieResult = model.getMovies(MovieModel.Field.DIRECTOR, director, 0, 24, MovieModel.Field.TITLE, false, true, true, true);
		view.showDirectorPreview(movieResult.getResults());
		
	}


	
};
class YearListener implements GroupListener {

	@Override
	public void listRequest(MovieView view) {
		ArrayList<String> years = model.getAllYears();
		if(years.size()>0) {
			view.showYears(years);
		} else {
			view.showError(SettingsLoader.getValue("lang.no_years_found","Vuosia ei löytynyt"));
		}
	}

	@Override
	public void selected(MovieView view) {
		String year = view.getYear();
		QueryResult<Movie> movieResult = model.getMovies(MovieModel.Field.YEAR, year, 0, 0, MovieModel.Field.TITLE, false, true, true, true);
		if(movieResult.getResults().size()>0) {
			view.showMovies(movieResult.getResults());
		} else {
			view.showError(SettingsLoader.getValue("lang.no_movies_found","Elokuvia ei löytynyt"));
		}
		
	}

	@Override
	public void previewRequest(MovieView view) {
		String year = view.getYear();
		QueryResult<Movie> movieResult = model.getMovies(MovieModel.Field.YEAR, year, 0, 24, MovieModel.Field.TITLE, false, true, true, true);
		view.showYearPreview(movieResult.getResults());
		
	}

	
};
public class CloseListenerImpl implements CloseListener {

	@Override
	public void close(MovieView view) {
		System.exit(0);
	}

	
	
}

	public MovieController(MovieModel model, MovieView view) {
		this.model=model;
		this.view=view;
		System.out.println("Käytetään mallia: "+model.getModelName());
		System.out.println("Käytetään käyttöliittymää: "+view.getViewName());
		setListenersForView();
		view.init();
	}
	
	public MovieModel getModel() {
		return model;
	}

	public void setModel(MovieModel model) {
		this.model = model;
	}

	public MovieView getView() {
		return view;
	}

	public void setView(MovieView view) {
		this.view = view;
		setListenersForView();
	}
	
	private void setListenersForView() {
		view.setMovieListener(new MovieListenerImpl());
		view.setCloseListener(new CloseListenerImpl());	
		view.setCastListener(new CastListener());
		view.setDirectorListener(new DirectorListener());
		view.setYearListener(new YearListener());
		view.setGenreListener(new GenreListener());
	}
	
	
	
}
