package org.vatvit.movielib.views.ui.menu.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieCast;

public class MovieDetails extends JPanel {
	

	private Movie movie;

	
	
	
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	private JLabel createText(String content, String font, int style, int size) {
		JLabel label = new JLabel(content);
		label.setForeground(new Color(255, 255, 255));
		label.setFont(new Font(font, style, size));
		return label;
	}
	
	
	public MovieDetails(Movie movie) {
		super();
		this.movie=movie;
		
		setBackground(new Color(0, 0, 0));

		setLayout(new javax.swing.BoxLayout(this,
				javax.swing.BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(450, 10000));
		
		

		add(createText("<html>" + movie.toString() + "</html>",
				"Lucida Grande", Font.PLAIN, 26));

		
		
		add(createText("<html>" + movie.toHTML() + "</html>",
				"Lucida Grande", Font.PLAIN, 18));
		
		
	}
	
	
	
}
