package org.vatvit.movielib.views.ui.editor.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.views.ui.menu.panels.MenuImage;

public class MovieGenrePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList genreList = null;
	private JPanel controlsPanel = null;
	private JTextField nameField = null;
	private JLabel nameLabel = null;
	private JPanel jPanel = null;
	private JPanel addRemoveButtonsPanel = null;
	private JButton addButton = null;
	private JButton removeButton = null;
	private JPanel buttonsPanel = null;
	private JButton backButton = null;
	private JButton nextButton = null;
	private JButton editButton = null;
	
	private Vector<MovieGenre> genreData = null;
 //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public MovieGenrePanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 2;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridx = 0;
		this.setSize(400, 500);
		this.setLayout(new GridBagLayout());
		JScrollPane listScroll = new JScrollPane();
		listScroll.setViewportView(getGenreList());
		this.add(listScroll, gridBagConstraints);
		
		this.add(getControlsPanel(), gridBagConstraints1);
		
	}

	/**
	 * This method initializes genreList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getGenreList() {
		if (genreList == null) {
			genreList = new JList();
			genreList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					editButton.setVisible(true);
					removeButton.setVisible(true);
					
					MovieGenre selectedGenre = (MovieGenre)genreList.getSelectedValue();
					nameField.setText(selectedGenre.getTitle());
					
				}
				
			});
				
			
		}
		return genreList;
	}

	/**
	 * This method initializes controlsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getControlsPanel() {
		if (controlsPanel == null) {
			
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 5;
			gridBagConstraints12.anchor = GridBagConstraints.EAST;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 4;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 2;
			gridBagConstraints7.gridy = 4;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.gridy = 1;
			nameLabel = new JLabel();
			nameLabel.setText("Nimi:");
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridx = 1;
			controlsPanel = new JPanel();
			controlsPanel.setLayout(new GridBagLayout());
			controlsPanel.add(getNameField(), gridBagConstraints11);
			controlsPanel.add(nameLabel, gridBagConstraints2);
			controlsPanel.add(getAddRemoveButtonsPanel(), gridBagConstraints8);
			controlsPanel.add(getButtonsPanel(), gridBagConstraints12);
			
	
		}
		return controlsPanel;
	}

	/**
	 * This method initializes nameField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
		}
		return nameField;
	}

	/**
	 * This method initializes addRemoveButtonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAddRemoveButtonsPanel() {
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 1;
		gridBagConstraints13.gridy = 0;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 2;
		gridBagConstraints10.gridy = 0;
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.anchor = GridBagConstraints.EAST;
		
		if (addRemoveButtonsPanel == null) {
			addRemoveButtonsPanel = new JPanel();
			addRemoveButtonsPanel.setLayout(new GridBagLayout());
			addRemoveButtonsPanel.add(getAddButton(), gridBagConstraints9);
			addRemoveButtonsPanel.add(getRemoveButton(), gridBagConstraints10);
			addRemoveButtonsPanel.add(getEditButton(), gridBagConstraints13);
		}
		return addRemoveButtonsPanel;
	}

	/**
	 * This method initializes addButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText("Lisää");
			addButton.setActionCommand("add");
			addButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					MovieGenre genre = new MovieGenre();
					genre.setTitle(nameField.getText());
					
					genreData.add(genre);
					genreList.revalidate();
					repaint();
				}
				
			});
		}
		return addButton;
	}

	/**
	 * This method initializes removeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveButton() {
		if (removeButton == null) {
			removeButton = new JButton();
			removeButton.setText("Poista");
			removeButton.setActionCommand("remove");
			removeButton.setVisible(false);
			removeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					int i = genreList.getSelectedIndex();
					genreData.remove(i);
					genreList.revalidate();
					repaint();
					
					if(i>0) {
						genreList.setSelectedIndex(i-1);
					} else if(genreData.size()>0) {
						genreList.setSelectedIndex(0);
					} else {
						nameField.setText("");
						removeButton.setVisible(false);
						editButton.setVisible(false);
					}
				
				}
				
			});
		}
		return removeButton;
	}

	/**
	 * This method initializes buttonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new GridBagLayout());
			buttonsPanel.add(getBackButton());
			buttonsPanel.add(getNextButton());
		}
		return buttonsPanel;
	}

	/**
	 * This method initializes backButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBackButton() {
		if (backButton == null) {
			backButton = new JButton();
			backButton.setText("Edellinen");
			backButton.setActionCommand("back");
		}
		return backButton;
	}

	/**
	 * This method initializes nextButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNextButton() {
		if (nextButton == null) {
			nextButton = new JButton();
			nextButton.setText("Seuraava");
			nextButton.setActionCommand("next");
		}
		return nextButton;
	}

	/**
	 * This method initializes editButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getEditButton() {
		if (editButton == null) {
			editButton = new JButton();
			editButton.setText("Tallenna");
			editButton.setActionCommand("edit");
			editButton.setVisible(false);
			editButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					MovieGenre selectedGenre = (MovieGenre)genreList.getSelectedValue();
					selectedGenre.setTitle(nameField.getText());
					
					genreList.revalidate();
					repaint();
				}
				
			});
		}
		return editButton;
	}
	
	public void setMovieGenre(ArrayList<MovieGenre> genre) {
		genreData = new Vector<MovieGenre>(genre);
		genreList.setListData(genreData);
		
	}
	public ArrayList<MovieGenre> getMovieGenre() {
		
		return new ArrayList<MovieGenre>(genreData);
	}
	public void addActionListener(ActionListener al) {
		nextButton.addActionListener(al);
		backButton.addActionListener(al);
	}
	public void removeActionListener(ActionListener al) {
		nextButton.removeActionListener(al);
		backButton.removeActionListener(al);
	}

}
