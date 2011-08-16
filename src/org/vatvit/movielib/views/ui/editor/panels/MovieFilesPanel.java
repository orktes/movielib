package org.vatvit.movielib.views.ui.editor.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;

import org.vatvit.movielib.settings.SettingsLoader;

import java.awt.event.ActionListener;
/**
 * Elokuvan tiedostojen muokkaamiseen tarkoitettu paneeli 
 */
public class MovieFilesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private FileSelectPanel movieFileSelectPanel = null;
	private JLabel movieFileLabel = null;
	private JLabel coverLabel = null;
	private JLabel backgroundLabel = null;
	private JPanel buttonsPanel = null;
	private JButton backButton = null;
	private JButton nextButton = null;
	private FileSelectPanel coverFileSelectPanel = null;
	private FileSelectPanel backdropFileSelectPanel = null;

	/**
	 * This is the default constructor
	 */
	public MovieFilesPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 1;
		gridBagConstraints6.gridy = 3;
		gridBagConstraints6.anchor = GridBagConstraints.EAST;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 1;
		gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints5.gridy = 2;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 1;
		gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints4.gridy = 1;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.anchor = GridBagConstraints.EAST;
		gridBagConstraints3.gridy = 2;
		backgroundLabel = new JLabel();
		backgroundLabel.setText(SettingsLoader.getValue("lang.backdrop", "Taustakuva"));
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.anchor = GridBagConstraints.EAST;
		gridBagConstraints2.gridy = 1;
		coverLabel = new JLabel();
		coverLabel.setText(SettingsLoader.getValue("lang.cover", "Kansikuva:"));
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.anchor = GridBagConstraints.EAST;
		gridBagConstraints1.gridy = 0;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridx = 1;
		movieFileLabel = new JLabel();
		movieFileLabel.setText(SettingsLoader.getValue("lang.videofile", "Video-tiedosto"));
		this.setSize(504, 191);
		this.setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		
		contentPanel.add(movieFileLabel, gridBagConstraints1);
		contentPanel.add(getMovieFileSelectPanel(), gridBagConstraints);
		contentPanel.add(coverLabel, gridBagConstraints2);
		contentPanel.add(backgroundLabel, gridBagConstraints3);
		contentPanel.add(getCoverFileSelectPanel(), gridBagConstraints4);
		contentPanel.add(getBackdropFileSelectPanel(), gridBagConstraints5);
		this.add(contentPanel, BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes movieFileSelectPanel	
	 * 	
	 * @return org.vatvit.movielib.views.ui.editor.panels.FileSelectPanel	
	 */
	private FileSelectPanel getMovieFileSelectPanel() {
		if (movieFileSelectPanel == null) {
			movieFileSelectPanel = new FileSelectPanel();
		}
		return movieFileSelectPanel;
	}

	/**
	 * This method initializes coverFileSelectPanel	
	 * 	
	 * @return org.vatvit.movielib.views.ui.editor.panels.FileSelectPanel	
	 */
	private FileSelectPanel getCoverFileSelectPanel() {
		if (coverFileSelectPanel == null) {
			coverFileSelectPanel = new FileSelectPanel();
		}
		return coverFileSelectPanel;
	}

	/**
	 * This method initializes backdropFileSelectPanel	
	 * 	
	 * @return org.vatvit.movielib.views.ui.editor.panels.FileSelectPanel	
	 */
	private FileSelectPanel getBackdropFileSelectPanel() {
		if (backdropFileSelectPanel == null) {
			backdropFileSelectPanel = new FileSelectPanel();
		}
		return backdropFileSelectPanel;
	}
	
	/**
	 * This method initializes buttonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
			nextButton.setText(SettingsLoader.getValue("lang.ready", "Valmis"));
			nextButton.setActionCommand("next");
		}
		return nextButton;
	}

	/**
	 * Palattaa video-tiedoston sijainnin
	 * @return tiedosto
	 */
	public String getVideoLocation() {
		return movieFileSelectPanel.getFileName();
	}
	/**
	 * Asettaa video-tiedoston sijainnin
	 * @param location
	 */
	public void setVideoLocation(String location) {
		movieFileSelectPanel.setFileName(location);
	}
	/**
	 * Palauttaa kansikuvan sijainnin
	 * @return sijainti
	 */
	public String getCoverLocation() {
		return coverFileSelectPanel.getFileName();
	}
	/**
	 * Asettaa kansikuvan sijainnin
	 * @param location sijain
	 */
	public void setCoverLocation(String location) {
		coverFileSelectPanel.setFileName(location);
	}
	/**
	 * Palauttaa taustakuvan sijainnin
	 * @return siainti
	 */
	public String getBackdropLocation() {
		return backdropFileSelectPanel.getFileName();
	}
	/**
	 * Asettaa kansikuvan sijainnin
	 * @param location sijainti
	 */
	public void setBackdropLocation(String location) {
		backdropFileSelectPanel.setFileName(location);
	}
	/**
	 * Lisää tapahtumakuuntelijan back ja next painikkeille
	 * @param al tapahtumakuuntelija
	 */
	public void addActionListener(ActionListener al) {
		nextButton.addActionListener(al);
		backButton.addActionListener(al);
	}
	/**
	 * Poistaa tapahtumakuuntelijan back ja next painikkeilta
	 * @param al tapahtumakuuntelija
	 */
	public void removeActionListener(ActionListener al) {
		nextButton.removeActionListener(al);
		backButton.removeActionListener(al);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
