package org.vatvit.movielib.views;

/**
 * Elokuva ryhmittelyiden ohjaaja, näyttelijät, genret ja vuodet kuuntelija
 */
public interface GroupListener {
	/**
	 * Valinta valittu
	 * @param view näkymä jolta ilmoitus tuli
	 */
	public void selected(MovieView view);
	/**
	 * Listausta pyydetty
	 * @param view näkymä jolta ilmoitus tuli
	 */
	public void listRequest(MovieView view);
	/**
	 * Esikatselu pyyntö
	 * @param view näkymä jolta ilmoitus tuli
	 */
	public void previewRequest(MovieView view);
}
