package org.vatvit.movielib.views.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Yleinen valikkonäkymä
 *
 */
public class View extends JPanel {
	private String previusCard;

	/**
	 * Palauttaa näkymää edeltäneen näkymän
	 * @return näkymän nimi
	 */
	public String getPreviusCard() {
		return previusCard;
	}

	/**
	 * Asettaa näkymää edeltäneen näkymän
	 * @param previusCard näkymä
	 */
	public void setPreviusCard(String previusCard) {
		this.previusCard = previusCard;
	}
	
	/**
	 * Asettaa näkymän valituksi
	 */
	public void focus() {
		requestFocus();
	}
	
	
}
