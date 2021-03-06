package org.vatvit.movielib.views.ui.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.objects.MovieSubtitle;
import org.vatvit.movielib.settings.SettingsLoader;
import org.vatvit.movielib.tools.FileTools;
import org.vatvit.movielib.tools.ImageTools;
import org.vatvit.movielib.views.ui.View;
import org.vatvit.movielib.views.ui.menu.panels.MenuImage;

/**
 * Elokuvan toistoasetuksien asetus paneeli.
 * 
 */
public class MovieSettingsMenu extends View {

	private MenuList menu = null;

	private Movie movie = null;

	/**
	 * Palauttaa tapahtumakuuntelijan
	 * 
	 * @return tapahtumakuuntelija
	 */
	public ActionListener getActionListener() {
		return menu.getActionListener();
	}

	/**
	 * Aseta valikolle tapahtumakuuntelija
	 * 
	 * @param actionListener
	 *            tapahtumakuuntelija
	 */
	public void setActionListener(ActionListener actionListener) {
		menu.setActionListener(actionListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vatvit.movielib.views.ui.View#focus()
	 */
	public void focus() {
		menu.requestFocus();

	}

	/**
	 * Luo uuden toistoasetus valikon
	 * 
	 * @param movie
	 *            elokuva
	 * @param image
	 *            kansikuva
	 */
	public MovieSettingsMenu(Movie movie, BufferedImage image) {
		super();
		setBackground(new Color(0, 0, 0));
		setLayout(new GridBagLayout());
		this.movie = movie;
		createMenu();
		validate();

	}

	/**
	 * Luodaan valikko
	 */
	private void createMenu() {

		JScrollPane menuScrollPane = new javax.swing.JScrollPane();
		menuScrollPane.setBorder(BorderFactory
				.createEmptyBorder());
		menuScrollPane
				.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		menuScrollPane
				.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		menuScrollPane.setPreferredSize(new Dimension(650, 500));

		menu = new MenuList();
		menu.setModel(new javax.swing.AbstractListModel() {
			private boolean hasTrailer = false;

			public int getSize() {
				int size = 1; // Normaali katsominen ilman
								// tekstejä
				// Tarkistetaan onko elokuvasta youtube traileria
				if (movie.getTrailerUrl() != null
						&& movie.getTrailerUrl().length() > 0
						&& movie.getTrailerUrl().toLowerCase()
								.indexOf("youtube") > -1) {
					hasTrailer = true;
					size++;
				}
				// Lisätään tekstitysten lukumäärä
				if (movie.getSubtitles() != null) {
					size += movie.getSubtitles().size();
				}

				return size;
			}

			public Object getElementAt(int i) {

				if (i == 0 && hasTrailer) {
					return new PlayInformation("Trailer", movie
							.getTrailerUrl(), null);
				} else if (i == 0 || (i == 1 && hasTrailer)) {
					String label = "";
					if (movie.getSubtitles() != null
							&& movie.getSubtitles().size() > 0) {
						label = SettingsLoader.getValue(
								"lang.watch_without_subtitles",
								"Katso ilman tekstityksiä");
					} else {
						label = SettingsLoader.getValue(
								"lang.watch", "Katso");
					}

					return new PlayInformation(label, FileTools
							.getProgramDirectory()
							+ File.separator
							+ movie.getLocation(), null);
				} else {
					MovieSubtitle sub = movie.getSubtitles()
							.get(i - (hasTrailer ? 2 : 1));
					return new PlayInformation(sub.toString()
							+ SettingsLoader.getValue(
									"lang.with_subtitles",
									" -teksteillä"), FileTools
							.getProgramDirectory()
							+ File.separator
							+ movie.getLocation(), FileTools
							.getProgramDirectory()
							+ File.separator + sub.getLocation());
				}

			}
		});

		menu.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {

			}

		});

		menu.setSelectedIndex(0);

		menuScrollPane.setViewportView(menu);

		add(menuScrollPane);

	}

	/**
	 * Palauttaa valikon käyttämän MenuList:n
	 * 
	 * @return MenuList
	 */
	public MenuList getMenu() {
		return menu;
	}
}
