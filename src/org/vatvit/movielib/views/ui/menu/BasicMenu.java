package org.vatvit.movielib.views.ui.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.views.ui.View;
import org.vatvit.movielib.views.ui.menu.panels.MenuImage;

/**
 * Yleiskäyttöinen valikko.
 *
 */
public class BasicMenu extends View {
	private MenuList menu = null;
	private JPanel infoPanel = null;
	private JPanel centerPanel = null;

	
	
	/**
	 * Valikon vasemman puolen info paneeli
	 * @return paneeli
	 */
	public JPanel getInfoPanel() {
		return infoPanel;
	}

	/**
	 * Asettaa valikon vasemman puolen info paneelin
	 * @param infoPanel paneeli
	 */
	public void setInfoPanel(JPanel infoPanel) {
		this.infoPanel = infoPanel;
	}
	
	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.ui.View#focus()
	 */
	public void focus() {
		menu.requestFocus();

	}

	/**
	 * Palauttaa valikon tapahtumakuuntelijan
	 * @return tapahtumakuuntelija
	 */
	public ActionListener getActionListener() {
		return menu.getActionListener();
	}

	/**
	 * Asettaa valikolle tapahtumakuuntelijan
	 * @param actionListener tapahtumakuuntelija
	 */
	public void setActionListener(ActionListener actionListener) {
		menu.setActionListener(actionListener);
	}

	/**
	 * Luo uuden valikkopaneelin ja asettaa parametrinä annetun ArrayList<String> sisältämät arvot valikkoon
	 * @param values valikon arvot
	 */
	public BasicMenu(final ArrayList<String> values) {
		super();
		
		setLayout(new java.awt.GridLayout());
		setBackground(new Color(0, 0, 0));

		infoPanel = new JPanel();
		infoPanel.setBackground(new Color(0, 0, 0));
		add(infoPanel);

		JScrollPane menuScrollPane = new javax.swing.JScrollPane();
		menuScrollPane.setBorder(BorderFactory.createEmptyBorder());
		menuScrollPane
				.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		menuScrollPane
				.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		//Jos arvot eivät mahdu kerralla ruudulle
		if(values.size()>6) {
			menuScrollPane.setPreferredSize(new Dimension(490, 650));
		}
		
		centerPanel = new JPanel();
		centerPanel.setBackground(new Color(0, 0, 0));
		GridBagLayout gridBag = new java.awt.GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		// Specify horizontal fill, with top-left corner anchoring
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;

		// Select x- and y-direction weight. Without a non-zero weight,
		// the component will still be centered in the given direction.
		c.weightx = 1;
		c.weighty = 1;

		centerPanel.setLayout(gridBag);
		centerPanel.add(menuScrollPane);

		gridBag.setConstraints(menuScrollPane, c);

		add(centerPanel);

		menu = new MenuList();
		menu.setModel(new javax.swing.AbstractListModel() {
			public int getSize() {
				return values.size();
			}

			public Object getElementAt(int i) {
				return values.get(i);
			}
		});


		menu.setSelectedIndex(0);

		menuScrollPane.setViewportView(menu);

		validate();

	}


	/**
	 * Palauttaa valikon sisältämän MenuList:n
	 * @return MenuList
	 */
	public MenuList getMenu() {
		return menu;
	}

}
