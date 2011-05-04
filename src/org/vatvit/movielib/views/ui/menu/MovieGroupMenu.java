package org.vatvit.movielib.views.ui.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vatvit.movielib.dao.QueryResult;
import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.views.ui.View;
import org.vatvit.movielib.views.ui.menu.panels.MenuImage;

public class MovieGroupMenu extends BasicMenu {

	public MovieGroupMenu(ArrayList<String> values) {
		super(values);
	}
	
	public void displayInfo(ArrayList<Movie> movies) {
		JPanel infoPanel = getInfoPanel();
			infoPanel.setLayout(new java.awt.FlowLayout());
		infoPanel.removeAll();
		if(infoPanel.getGraphics()!=null) {
			infoPanel.getGraphics().clearRect(0, 0, infoPanel.getWidth(), infoPanel.getWidth());
		}
		for(Movie movie : movies) {
			BufferedImage image = movie.getCover(MovieModel.ImageSize.SMALL);
			if(image!=null) {
				MenuImage imagePanel = new MenuImage(image, true);
				infoPanel.add(imagePanel);
			}
		}
		validate();
	}
	
}
