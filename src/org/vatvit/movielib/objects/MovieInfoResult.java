package org.vatvit.movielib.objects;
/**
 * TMDb -kyselystä vastauksena saatua elokuvaa edustava luokka.
 *
 */
public class MovieInfoResult  {
		private Movie movie;
		private String cover;
		private String backdrop;
		
		/**
		 * Vastauksena saadun elokuvan tiedot asetettuna Movie olioon
		 * @return elokuva
		 */
		public Movie getMovie() {
			return movie;
		}
		/**
		 * Asettaa TMDb:stä palautetun elokuvan.
		 * @param movie elokuva
		 */
		public void setMovie(Movie movie) {
			this.movie = movie;
		}
		/**
		 * Palauttaa TMDb:stä saadun kansikuvan osoitteen
		 * @return kansikuvan osoite
		 */
		public String getCover() {
			return cover;
		}
		/**
		 * Asettaa TMDb:stä saadun kansikuvan osoitteen
		 * @param cover kansikuvan osoite
		 */
		public void setCover(String cover) {
			this.cover = cover;
		}
		/**
		 * Palauttaa TMDb:stä saadun taustakuvan osoitteen
		 * @return taustakuvan osoite
		 */
		public String getBackdrop() {
			return backdrop;
		}
		/**
		 * Asettaa TMDb:stä saadun taustakuvan osoitteen
		 * @param backdrop taustakuvankuvan osoite
		 */
		public void setBackdrop(String backdrop) {
			this.backdrop = backdrop;
		}
		/**
		 * Muodostaa MovieInfoResult olion annetuilla parametreillä.
		 * @param movie elokuvan tiedot Movie -muodossa
		 * @param cover kansikuvan url-osoite
		 * @param backdrop taustakuvan url-osoite
		 */
		public MovieInfoResult(Movie movie, String cover, String backdrop) {
			super();
			this.movie = movie;
			this.cover = cover;
			this.backdrop = backdrop;
		}
		@Override
		public String toString() {
			
			return this.movie.toString();
		}
		
		
		
	}