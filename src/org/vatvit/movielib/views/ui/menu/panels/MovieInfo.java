package org.vatvit.movielib.views.ui.menu.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.objects.Movie;

public class MovieInfo extends JScrollPane {
	private Thread scrollProcess;
	private boolean stopScroll;
	public MovieInfo(Movie movie, BufferedImage coverImage) {
		super();
		setBackground(new Color(0, 0, 0));

		setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setBorder(BorderFactory.createEmptyBorder());
		setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(500, 4000));
		content.setMaximumSize(new Dimension(500, 10000));
		content.setLayout(new FlowLayout());
		content.setBackground(new Color(0, 0, 0));
		MenuImage cover = new MenuImage(coverImage, true, 20, 12000);
		content.add(cover);

		MovieDetails details = new MovieDetails(movie);
		content.add(details);

		this.setViewportView(content);
		
	}

	public void stopScrollProcess() {
		
		stopScroll=true;
		
	}
	
	public void startScrollProcess() {
		
		
		stopScroll=false;
		scrollProcess = new Thread(new Runnable() {
			@Override
			protected void finalize() throws Throwable {
				
			}

			public void run() {
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				
				//Scrollaataan alas
				while(stopScroll==false && getVerticalScrollBar().getValue() < ( getVerticalScrollBar().getMaximum() - getVerticalScrollBar().getVisibleAmount())) {
					getVerticalScrollBar().setValue(getVerticalScrollBar().getValue()+1);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//Asetetaan scrolli takaisin alkuun
				getVerticalScrollBar().setValue(0);
			}
		});
		scrollProcess.start();
	}
}
