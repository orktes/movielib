package org.vatvit.movielib.views.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends JPanel {
	private String previusCard;

	public String getPreviusCard() {
		return previusCard;
	}

	public void setPreviusCard(String previusCard) {
		this.previusCard = previusCard;
	}
	
	public void focus() {
		requestFocus();
	}
	
	
}
