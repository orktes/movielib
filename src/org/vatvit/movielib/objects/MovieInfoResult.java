package org.vatvit.movielib.objects;
public class MovieInfoResult  {
		private Movie movie;
		private String cover;
		private String backdrop;
		
		public Movie getMovie() {
			return movie;
		}
		public void setMovie(Movie movie) {
			this.movie = movie;
		}
		public String getCover() {
			return cover;
		}
		public void setCover(String cover) {
			this.cover = cover;
		}
		public String getBackdrop() {
			return backdrop;
		}
		public void setBackdrop(String backdrop) {
			this.backdrop = backdrop;
		}
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