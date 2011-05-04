package org.vatvit.movielib.views.ui.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.views.ui.View;
import org.vatvit.movielib.views.ui.menu.panels.MovieInfo;

public class MoviesMenu extends View {

	private MenuList menu =null;
	private MovieInfo currentInfo = null;
	
	public ActionListener getActionListener() {
		return menu.getActionListener();
	}
	


	public void setActionListener(ActionListener actionListener) {
		menu.setActionListener(actionListener);
	}

	public void focus() {
		menu.requestFocus();
		
	}
	
	public MoviesMenu(final ArrayList<Movie> movies) {
		super();
		
		setLayout(new java.awt.GridLayout());
		setBackground(new Color(0,0,0));
		
		final JPanel infoPanel = new JPanel();
		infoPanel.setBackground(new Color(0, 0, 0));
		add(infoPanel);
		
		JScrollPane menuScrollPane = new javax.swing.JScrollPane();
		menuScrollPane.setBorder(BorderFactory.createEmptyBorder());
		menuScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		menuScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(0,0,0));
		GridBagLayout gridBag = new java.awt.GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		// Specify horizontal fill, with top-left corner anchoring
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		// Select x- and y-direction weight. Without a non-zero weight,
		// the component will still be centered in the given direction.
		c.weightx = 1;
		c.weighty = 1;


		menuScrollPane.setPreferredSize(new Dimension(490, 650));
		centerPanel.setLayout(gridBag);
	    centerPanel.add(menuScrollPane);
	    
	    gridBag.setConstraints(menuScrollPane, c);
		add(centerPanel);
		
		menu = new MenuList();
		menu.setModel(new javax.swing.AbstractListModel() {
          
            public int getSize() { return movies.size(); }
            public Object getElementAt(int i) { return movies.get(i); }
        });
		
		menu.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Movie movie = (Movie)menu.getSelectedValue();
				
				//Lopetaan vanha scrollprosessi
				if(currentInfo!=null) currentInfo.stopScrollProcess();
				
				infoPanel.setLayout(new java.awt.GridLayout());
				infoPanel.removeAll();
				
				currentInfo = new MovieInfo(movie, movie.getCover(MovieModel.ImageSize.MEDIUM));
				currentInfo.startScrollProcess();
				
				
				infoPanel.add(currentInfo);
				infoPanel.validate();
				
			}
			
		});
		
		menu.setSelectedIndex(0);
		
		
		
		menuScrollPane.setViewportView(menu);
		validate();
		
	}
	public MenuList getMenu() {
		return menu;
	}
	public void stopScrollProcess() {
		if(currentInfo!=null) currentInfo.stopScrollProcess();
	}
	public void startScrollProcess() {
		if(currentInfo!=null) currentInfo.startScrollProcess();
	}
}
