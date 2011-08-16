package org.vatvit.movielib.tools;

import java.util.ArrayList;

import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.objects.MovieSubtitle;

/**
 * MovieCast, MovieSubtitle ja MovieGenre kokoelmien vertailuun tarkoitettuja
 * työkaluja sisältävä apuluokka.
 * 
 */
public class EqualsTools {
	/**
	 * Vertaa kahta MovieCast ArrayList- kokoelmaa toisiinsa
	 * 
	 * @param a
	 * @param b
	 * @return ovatko kokoelmat samat tiedot
	 */
	public static boolean castArrayEquals(ArrayList<MovieCast> a,
			ArrayList<MovieCast> b) {
		if (a.size() != b.size())
			return false;

		for (int i = 0; i < a.size(); i++) {
			
			if (!a.get(i).equals(b.get(i))) {
				return false;
			}

		}

		return true;
	}

	/**
	 * Vertaa kahta MovieSubtitle ArrayList- kokoelmaa toisiinsa
	 * 
	 * @param a
	 * @param b
	 * @return sisältävätkö kokoelmat samat tiedot
	 */
	public static boolean subtitlesArrayEquals(ArrayList<MovieSubtitle> a,
			ArrayList<MovieSubtitle> b) {
		if (a.size() != b.size())
			return false;

		for (int i = 0; i < a.size(); i++) {
			
			if (!a.get(i).equals(b.get(i))) {
				return false;
			}

		}

		return true;
	}

	/**
	 * Vertaa kahta MovieGenre ArrayList- kokoelmaa toisiinsa
	 * 
	 * @param a
	 * @param b
	 * @return sisältävätkö kokoelmat samat tiedot
	 */
	public static boolean genreArrayEquals(ArrayList<MovieGenre> a,
			ArrayList<MovieGenre> b) {
		if (a.size() != b.size())
			return false;

		for (int i = 0; i < a.size(); i++) {

			if (!a.get(i).equals(b.get(i))) {
				return false;
			}

		}

		return true;
	}
}
