package org.vatvit.movielib.views;

/**
 * Elokuviin liittyviä tapahtumia seuraava kuuntelija
 *
 */
public interface MovieListener {
	/**
	 * Elokuva listausta pyydetty
	 * @param view näkymä, jolta pyyntö tuli
	 */
	public void movieList(MovieView view);
	/**
	 * Elokuva tallennettu
	 * @param view näkymä, jolta pyyntö tuli
	 */
	public void movieSaved(MovieView view);
	/**
	 * Elokuva valittu
	 * @param view näkymä, jolta pyyntö tuli
	 */
	public void movieSelected(MovieView view);
	/**
	 * Elokuvan poistopyyntö
	 * @param view näkymä, jolta pyyntö tuli
	 */
	public void movieDeleted(MovieView view);
}
