package org.vatvit.movielib.views.ui.editor.panels;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import org.vatvit.movielib.settings.SettingsLoader;
/**
 * Elokuvan tietojen muokkaamiseen tarkoitettu paneeli 
 */
public class MovieDetailsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = null;
	private JTextField titleField = null;
	private JLabel sloganLabel = null;
	private JTextField sloganField = null;
	private JLabel directorLabel = null;
	private JTextField directorField = null;
	private JLabel yearLabel = null;
	private JTextField yearField = null;
	private JLabel trailerLabel = null;
	private JTextField trailerField = null;
	private JLabel ratingLabel = null;
	private JTextField ratingField = null;
	private JLabel descriptionLabel = null;
	private JTextArea descriptionArea = null;
	private JPanel buttonPanel = null;
	private JButton backButton = null;
	private JButton nextButton = null;

	/**
	 * This is the default constructor
	 */
	public MovieDetailsPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
		gridBagConstraints14.gridx = 1;
		gridBagConstraints14.gridy = 8;
		gridBagConstraints14.anchor = GridBagConstraints.EAST;
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.fill = GridBagConstraints.BOTH;
		gridBagConstraints13.gridy = 6;
		gridBagConstraints13.weightx = 1.0;
		gridBagConstraints13.weighty = 1.0;
		gridBagConstraints13.ipadx = 0;
		gridBagConstraints13.ipady = 0;
		gridBagConstraints13.gridwidth = 1;
		gridBagConstraints13.anchor = GridBagConstraints.CENTER;
		gridBagConstraints13.gridx = 1;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints12.gridy = 6;
		descriptionLabel = new JLabel();
		descriptionLabel.setText(SettingsLoader.getValue("lang.description", "Kuvaus:"));
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.fill = GridBagConstraints.BOTH;
		gridBagConstraints11.gridy = 5;
		gridBagConstraints11.weightx = 1.0;
		gridBagConstraints11.gridx = 1;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.anchor = GridBagConstraints.EAST;
		gridBagConstraints10.gridy = 5;
		ratingLabel = new JLabel();
		ratingLabel.setText(SettingsLoader.getValue("lang.rating", "Arvostelu (0.0 - 10.0):"));
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.fill = GridBagConstraints.BOTH;
		gridBagConstraints9.gridy = 4;
		gridBagConstraints9.weightx = 1.0;
		gridBagConstraints9.gridx = 1;
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.anchor = GridBagConstraints.EAST;
		gridBagConstraints8.gridy = 4;
		trailerLabel = new JLabel();
		trailerLabel.setText(SettingsLoader.getValue("lang.trailer", "Trailer (Youtube):"));
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.fill = GridBagConstraints.BOTH;
		gridBagConstraints7.gridy = 3;
		gridBagConstraints7.weightx = 1.0;
		gridBagConstraints7.gridx = 1;
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.anchor = GridBagConstraints.EAST;
		gridBagConstraints6.gridy = 3;
		yearLabel = new JLabel();
		yearLabel.setText(SettingsLoader.getValue("lang.vuosi", "Vuosi:"));
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 2;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.gridx = 1;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.anchor = GridBagConstraints.EAST;
		gridBagConstraints4.gridy = 2;
		directorLabel = new JLabel();
		directorLabel.setText(SettingsLoader.getValue("lang.director", "Ohjaaja:"));
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.BOTH;
		gridBagConstraints3.gridy = 1;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.gridx = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.anchor = GridBagConstraints.EAST;
		gridBagConstraints2.gridy = 1;
		sloganLabel = new JLabel();
		sloganLabel.setText(SettingsLoader.getValue("lang.slogan", "Slogan:"));
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.gridy = 0;
		titleLabel = new JLabel();
		titleLabel.setText(SettingsLoader.getValue("lang.name", "Nimi:"));
		this.setSize(528, 247);
		this.setLayout(new GridBagLayout());
		this.add(titleLabel, gridBagConstraints);
		this.add(getTitleField(), gridBagConstraints1);
		this.add(sloganLabel, gridBagConstraints2);
		this.add(getSloganField(), gridBagConstraints3);
		this.add(directorLabel, gridBagConstraints4);
		this.add(getDirectorField(), gridBagConstraints5);
		this.add(yearLabel, gridBagConstraints6);
		this.add(getYearField(), gridBagConstraints7);
		this.add(trailerLabel, gridBagConstraints8);
		this.add(getTrailerField(), gridBagConstraints9);
		this.add(ratingLabel, gridBagConstraints10);
		this.add(getRatingField(), gridBagConstraints11);
		this.add(descriptionLabel, gridBagConstraints12);
		getDescriptionArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(descriptionArea);
		this.add(scroll, gridBagConstraints13);
		this.add(getButtonPanel(), gridBagConstraints14);
		
	}

	/**
	 * This method initializes titleField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTitleField() {
		if (titleField == null) {
			titleField = new JTextField();
		}
		return titleField;
	}

	/**
	 * This method initializes sloganField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSloganField() {
		if (sloganField == null) {
			sloganField = new JTextField();
		}
		return sloganField;
	}

	/**
	 * This method initializes directorField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getDirectorField() {
		if (directorField == null) {
			directorField = new JTextField();
		}
		return directorField;
	}

	/**
	 * This method initializes yearField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getYearField() {
		if (yearField == null) {
			yearField = new JTextField();
		}
		return yearField;
	}

	/**
	 * This method initializes trailerField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTrailerField() {
		if (trailerField == null) {
			trailerField = new JTextField();
		}
		return trailerField;
	}

	/**
	 * This method initializes ratingField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRatingField() {
		if (ratingField == null) {
			ratingField = new JTextField();
		}
		return ratingField;
	}

	/**
	 * This method initializes descriptionArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getDescriptionArea() {
		if (descriptionArea == null) {
			descriptionArea = new JTextArea();
			descriptionArea.setWrapStyleWord(true);
			descriptionArea.setLineWrap(true);
			}
		return descriptionArea;
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.gridy = 0;
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getBackButton());
			buttonPanel.add(getNextButton(), gridBagConstraints15);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes backButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBackButton() {
		if (backButton == null) {
			backButton = new JButton();
			backButton.setText(SettingsLoader.getValue("lang.back", "Edellinen"));
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
			nextButton.setText(SettingsLoader.getValue("lang.next", "Seuraava"));
			nextButton.setActionCommand("next");
		}
		return nextButton;
	}
	
	/**
	 * Palauttaa elokuvan nimen
	 * @return nimi
	 */
	public String getMovieTitle() {
		return titleField.getText();
	}
	/**
	 * Palauttaa elokuvan sloganin
	 * @return slogan
	 */
	public String getMovieSlogan() {
		return sloganField.getText();
	}
	/**
	 * Palauttaa elokuvan ohjaajan
	 * @return ohjaaja
	 */
	public String getMovieDirector() {
		return directorField.getText();
	}
	/**
	 * Palauttaaa elokuvan vuoden
	 * @return vuosi
	 */
	public int getMovieYear() {
		try {
			return Integer.parseInt(yearField.getText());
		} catch (Exception e ) {
			return 0;
		}
		
	}
	/**
	 * Palauttaa elokuvan trailerin
	 * @return trailer
	 */
	public String getMovieTrailer() {
		return trailerField.getText();
	}
	/**
	 * Palauttaa elokuvan arvostelut
	 * @return arvostelut
	 */
	public double getMovieRating() {
		try {
			return Double.parseDouble(ratingField.getText().replaceAll(",", "."));
		} catch (Exception e ) {
			return 0;
		}
	}
	/**
	 * Palauttaa elokuvan kuvauksen
	 * @return kuvaus
	 */
	public String getMovieDescription() {
		return descriptionArea.getText();
	}
	/**
	 * Asettaa elokuvan nimen
	 * @param title nimi
	 */
	public void setMovieTitle(String title) {
		titleField.setText(title);
	}
	/**
	 * Asettaa elokuvan sloganin
	 * @param slogan
	 */
	public void setMovieSlogan(String slogan) {
		sloganField.setText(slogan);
	}
	/**
	 * Asettaa elokuvan ohjaajan
	 * @param director ohjaaja
	 */
	public void setMovieDirector(String director) {
		directorField.setText(director);
	}
	/**
	 * Asettaa elokuvan tuontanto vuoden
	 * @param year vuosi
	 */
	public void setMovieYear(int year) {
		yearField.setText(year+"");
	}
	/**
	 * Asettaa elokuvan trailerin
	 * @param trailer trailer
	 */
	public void setMovieTrailer(String trailer) {
		trailerField.setText(trailer);
	}
	/**
	 * Asettaa elokuvan arvostelun
	 * @param rating
	 */
	public void setMovieRating(double rating) {
		NumberFormat formatter = new DecimalFormat("#0.0");
		ratingField.setText(formatter.format(rating));
	}
	/**
	 * Asettaa elokuvan kuvauksen
	 * @param description
	 */
	public void setMovieDescription(String description) {
		descriptionArea.setText(description);
	}
	/**
	 * Lisää tapahtumakuuntelija
	 * @param al tapahtumakuuntelija
	 */
	public void addActionListener(ActionListener al) {
		nextButton.addActionListener(al);
		backButton.addActionListener(al);
	}
	/**
	 * Poista tapahtumakuuntelija
	 * @param al tapahtumakuuntelija
	 */
	public void removeActionListener(ActionListener al) {
		nextButton.removeActionListener(al);
		backButton.removeActionListener(al);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
