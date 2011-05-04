package org.vatvit.movielib.views.ui.editor.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.event.ListDataListener;

import org.vatvit.movielib.objects.MovieInfoResult;

public class MovieInformationSelectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox movieSelect = null;
	private JLabel jLabel = null;
	private JPanel buttonPanel = null;
	private JButton next = null;
	private JButton previous = null;
	private ArrayList<MovieInfoResult> movies;

	/**
	 * This is the default constructor
	 */
	public MovieInformationSelectPanel(ArrayList<MovieInfoResult> movies) {
		super();
		this.movies=movies;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 6;
		gridBagConstraints3.gridy = 2;
		gridBagConstraints3.anchor = GridBagConstraints.EAST;
		gridBagConstraints3.fill = GridBagConstraints.NONE;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 5;
		gridBagConstraints1.anchor = GridBagConstraints.EAST;
		gridBagConstraints1.gridy = 1;
		jLabel = new JLabel();
		jLabel.setText("Valitse oikea elokuva:");
		jLabel.setPreferredSize(new Dimension(50,20));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridx = 6;
		this.setSize(472, 194);
		
		this.setLayout(new BorderLayout());
		
		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		
		content.add(getMovieSelect(), gridBagConstraints);
		content.add(jLabel, gridBagConstraints1);
		
		this.add(content, BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes movieSelect	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getMovieSelect() {
		if (movieSelect == null) {
			if(movies!=null) {
				movieSelect = new JComboBox(movies.toArray());
			} else {
				movieSelect = new JComboBox();
			}
			
		
		}
		return movieSelect;
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.add(getPrevious());
			buttonPanel.add(getNext());
			
		
		}
		return buttonPanel;
	}

	/**
	 * This method initializes next	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNext() {
		if (next == null) {
			next = new JButton("Seuraava");
			next.setText("Seuraava");
			next.setActionCommand("next");
		}
		return next;
	}

	/**
	 * This method initializes previous	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPrevious() {
		if (previous == null) {
			previous = new JButton("Edellinen");
			previous.setText("Edellinen");
			previous.setActionCommand("back");
		}
		return previous;
	}
	public MovieInfoResult getSelectedMovie() {
		return (MovieInfoResult) movieSelect.getSelectedItem();
		
	}
	public void addActionListener(ActionListener al) {
		next.addActionListener(al);
		previous.addActionListener(al);
	}
	public void removeActionListener(ActionListener al) {
		next.removeActionListener(al);
		previous.removeActionListener(al);
	}
	
}  //  @jve:decl-index=0:visual-constraint="463,20"
