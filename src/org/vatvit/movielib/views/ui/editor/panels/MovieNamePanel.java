package org.vatvit.movielib.views.ui.editor.panels;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.vatvit.movielib.settings.SettingsLoader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
/**
 * Elokuvan nimen muokkaamiseen ja tietojen hakemiseen tarkoitettu paneeli 
 */
public class MovieNamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField field = null;
	private JButton next; 
	private JButton cancel;
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
		
		JLabel label = new JLabel(SettingsLoader.getValue("lang.movie_name","Elokuvan nimi:"));
		contentPanel.add(label);
		
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(panel, BorderLayout.SOUTH);
		contentPanel.add(getField(), gridBagConstraints16);
		
		cancel = new JButton("Peruuta");
		cancel.setActionCommand("cancel");
		panel.add(cancel);
		
		
		next = new JButton("Seuraava");
		next.setActionCommand("next");
		panel.add(next);
		
	}
	/**
	 * Palauttaa elokuvan nimen
	 * @return nimi
	 */
	public String getMovieName() {
		return field.getText();
	}
	/**
	 * Asettaa elokuvan nimen
	 * @param name nimi
	 */
	public void setMovieName(String name) {
		field.setText(name);
	}
	/**
	 * Lisää tapahtumakuuntelijan peruuta, haku ja seuraava painikkeille
	 * @param al tapahtumakuuntelija
	 */
	public void addActionListener(ActionListener al) {
		next.addActionListener(al);
		cancel.addActionListener(al);
	}
	/**
	 * Poistaa tapahtumakuuntelijan peruuta, haku ja seuraava painikkeilta
	 * @param al tapahtumakuuntelija
	 */
	public void removeActionListener(ActionListener al) {
		next.removeActionListener(al);
		cancel.removeActionListener(al);
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
