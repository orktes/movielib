package org.vatvit.movielib.views;

/**
 * Sovelluksen sulkupyyntöjen kuuntelua varten tarkoitettu kuuntelija
 */
public interface CloseListener {
	
	/**
	 * Sulkupyyntö
	 * @param view näkymä, jolta pyyntö tuli
	 */
	public void close(MovieView view);
}
