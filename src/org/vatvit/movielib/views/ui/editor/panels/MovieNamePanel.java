package org.vatvit.movielib.views.ui.editor.panels;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

public class MovieNamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField field = null;
	private JButton next; 
	private JButton cancel;
	private JButton search;
	
	/**
	 * This is the default constructor
	 */
	public MovieNamePanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
		gridBagConstraints16.fill = GridBagConstraints.BOTH;
		gridBagConstraints16.gridy = 0;
		gridBagConstraints16.weightx = 1.0;
		gridBagConstraints16.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.gridy = 9;
		this.setSize(453, 181);
		this.setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		
		this.add(contentPanel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("Elokuvan nimi:");
		contentPanel.add(label);
		
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(panel, BorderLayout.SOUTH);
		contentPanel.add(getField(), gridBagConstraints16);
		
		cancel = new JButton("Peruuta");
		cancel.setActionCommand("cancel");
		panel.add(cancel);
		
		search = new JButton("Hae tiedot");
		search.setActionCommand("search");
		panel.add(search);
		
		next = new JButton("Seuraava");
		next.setActionCommand("next");
		panel.add(next);
		
	}
	public String getMovieName() {
		return field.getText();
	}
	public void setMovieName(String name) {
		field.setText(name);
	}
	public void addActionListener(ActionListener al) {
		next.addActionListener(al);
		cancel.addActionListener(al);
		search.addActionListener(al);
	}
	public void removeActionListener(ActionListener al) {
		next.removeActionListener(al);
		cancel.removeActionListener(al);
		search.removeActionListener(al);
	}

	/**
	 * This method initializes field1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getField() {
		if (field == null) {
			field = new JTextField();
		}
		return field;
	}
}  //  @jve:decl-index=0:visual-constraint="360,42"
