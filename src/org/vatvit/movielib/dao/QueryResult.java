package org.vatvit.movielib.dao;

import java.util.ArrayList;


/**
 * Geneerinen tietokannan hakuvastaus.
 *
 * @param <T> vastauksen sisältämien olioiden tyyppi
 */
public class QueryResult<T> {
	private int total;
	private ArrayList<T> results;
	private int page;
	private int limit;
	private String field;
	private String search;
	private String orderBy;
	private boolean desc;

	/**
	 * Palauttaa minkä kentän mukaan tiedot on järjestetty
	 * @return kenttä
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Asetaa minkä kentän mukaan tiedot on järjestettu
	 * @param orderBy kenttä
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Onko tiedot käänteisessä järjestyksessä
	 * @return totuusarvo
	 */
	public boolean isDesc() {
		return desc;
	}

	/**
	 * Asettaa ovatko tiedot käänteisessä järjestyksessä
	 * @param desc
	 */
	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	/**
	 * Palauttaaa hakuehtoja vastaavien tietueiden kokonaismäärä
	 * @return hakuehtoja vastaavien tietueiden kokonaismäärä
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * Asettaa hakuehtoja vastaavien tietueiden kokonaismäärän
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * Palauttaa tietokannan palauttamat tietueet ArrayList kokelmassa
	 * @return tietokannan palauttamat tietueet ArrayList kokelmassa
	 */
	public ArrayList<T> getResults() {
		return results;
	}

	/**
	 * Asettaa tietokannan palauttamat tietueet
	 * @param results Tietueet ArrayList kokoelmassa
	 */
	public void setResults(ArrayList<T> results) {
		this.results = results;
	}

	/**
	 * Palauttaa tietokannan palauttamia tietueita vastaava sivu
	 * @return tietokannan palauttamia tietueita vastaava sivu
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Asettaa tietokannan tieuteita vastaavan sivun
	 * @param page sivu
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * Palauttaa hakuvastauksen tietueiden maksimimäärä
	 * @return hakuvastauksen tietueiden maksimimäärä
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * Asettaa hakuvastauksen tietueiden maksimimäärän
	 * @param limit raja
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * Palauttaa haussa käytetyn kentän
	 * @return kenttä (null = ei käytetty)
	 */
	public String getField() {
		return field;
	}

	/**
	 * Asettaa haussa käytetyn kentän
	 * @param field kenttä
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Palauttaa kyselyssä käytetyn hakusanan
	 * @return hakusana
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * Asettaa kyselyssä käytetyn hakusanan
	 * @param search hakusana
	 */
	public void setSearch(String search) {
		this.search = search;
	}

}
