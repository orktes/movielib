package org.vatvit.movielib.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import net.sf.jtmdb.CastInfo;
import net.sf.jtmdb.GeneralSettings;
import net.sf.jtmdb.Genre;
import net.sf.jtmdb.MoviePoster;

import org.json.JSONException;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.objects.MovieInfoResult;

public class MovieInfoTools {
	private static boolean isInitialized;

	private static void initialize() {
		if (!isInitialized) {
			GeneralSettings.setApiKey("25120e01e3a86939f264ba61c7f21418");
			GeneralSettings.setAPILocale(Locale.getDefault());
			isInitialized = true;
		}
	}
	private static ArrayList<MovieInfoResult> convertMoviesToMovieInfoResults(List<net.sf.jtmdb.Movie> movies) {
		ArrayList<MovieInfoResult> details = new ArrayList<MovieInfoResult>();
		if(movies!=null) {
		for (net.sf.jtmdb.Movie mov : movies) {

			Movie movie = new Movie();
			movie.setTitle(mov.getName());
			movie.setDescription(mov.getOverview());
			if (mov.getTrailer() != null) {
				movie.setTrailerUrl(mov.getTrailer().toString());
			} else {
				movie.setTrailerUrl("");
			}
			if (mov.getTagline() != null) {
				movie.setSlogan(mov.getTagline());
			} else {
				movie.setSlogan("");
			}
			movie.setRating(mov.getRating());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(mov.getReleasedDate());
			movie.setYear(calendar.get(Calendar.YEAR));

			movie.setCast(new ArrayList<MovieCast>());

			for (CastInfo castInfo : mov.getCast()) {
				if (castInfo.getJob().equalsIgnoreCase("director")) {
					movie.setDirector(castInfo.getName());
				} else {
					MovieCast cast = new MovieCast();
					cast.setActor((castInfo.getName()!=null)?castInfo.getName():"");
					cast.setRole((castInfo.getCharacterName()!=null)?castInfo.getCharacterName():"");
					cast.setJob((castInfo.getJob()!=null)?castInfo.getJob():"");
					movie.getCast().add(cast);
				}
				
			}

			movie.setGenres(new ArrayList<MovieGenre>());

			for (Genre genreInfo : mov.getGenres()) {
				MovieGenre genre = new MovieGenre();
				genre.setTitle(genreInfo.getName());
				movie.getGenres().add(genre);
			}

			String cover = null;
			String backdrop = null;

			if (mov.getImages().posters.size() > 0) {
				cover = mov.getImages().posters.iterator().next()
						.getLargestImage().toString();
			}

			if (mov.getImages().backdrops.size() > 0) {
				backdrop = mov.getImages().backdrops.iterator().next()
						.getLargestImage().toString();
			}

			details.add(new MovieInfoResult(movie, cover, backdrop));

		}
		}
		return details;
	}
	public static MovieInfoResult getMovieInfoByFile(File file) {
		initialize();	
		
		try {
			List<net.sf.jtmdb.Movie> movies = net.sf.jtmdb.Media.getInfo(OpenSubtitlesHasher.computeHash(file), file.length());
			ArrayList<MovieInfoResult> details = convertMoviesToMovieInfoResults(movies);
			if(details.size()>0) {
				return details.get(0);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return null;
		
	}
	public static ArrayList<MovieInfoResult> getMovieInfo(String movieName) {
		System.out.println(movieName);
		initialize();
		ArrayList<MovieInfoResult> details = new ArrayList<MovieInfoResult>();

		try {
			List<net.sf.jtmdb.Movie> movies = net.sf.jtmdb.Movie
					.deepSearch(movieName);
			details = convertMoviesToMovieInfoResults(movies);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return details;
	}

	public static void main(String[] args) {
		
		
			
		
		ArrayList<MovieInfoResult> details = MovieInfoTools.getMovieInfo("Avatar");
		for(MovieInfoResult  movie : details) {
			System.out.println(movie.getBackdrop());
			System.out.println(movie.getCover());
			System.out.println(movie.getMovie().getTitle());
			System.out.println(movie.getMovie().getDescription());
			System.out.println(movie.getMovie().getDirector());
			System.out.println(movie.getMovie().getSlogan());
			System.out.println(movie.getMovie().getYear());
			for (MovieCast cast : movie.getMovie().getCast()) {
				System.out.println(cast);
			}
			for (MovieGenre genre : movie.getMovie().getGenres()) {
				System.out.println(genre);
			}
			System.out.println(movie.getMovie().getTrailerUrl());
		}
		

	}

}
