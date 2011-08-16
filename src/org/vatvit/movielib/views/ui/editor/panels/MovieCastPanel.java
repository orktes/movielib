package org.vatvit.movielib.views.ui.editor.panels;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.settings.SettingsLoader;

/**
 * Elokuvan näyttelijöiden ja muun tuotantoryhmän muokkaamiseen tarkoitettu
 * paneeli
 */
public class MovieCastPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList castList = null;
	private JPanel controlsPanel = null;
	private JTextField nameField = null;
	private JLabel nameLabel = null;
	private JLabel roleOrJobLabel = null;
	private JTextField roleOrJobField = null;
	private JLabel actorLabel = null;
	private JCheckBox actorCheckBox = null;
	private JPanel addRemoveButtonsPanel = null;
	private JButton addButton = null;
	private JButton removeButton = null;
	private JPanel buttonsPanel = null;
	private JButton backButton = null;
	private JButton nextButton = null;
	private JButton editButton = null;

	private Vector<MovieCast> castData = null;
	private JPanel header = null; // @jve:decl-index=0:visual-constraint="520,33"

	// @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public MovieCastPanel() {
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
		listScroll.setViewportView(getCastList());

		this.add(listScroll, gridBagConstraints);
		this.add(getControlsPanel(), gridBagConstraints1);

	}

	/**
	 * This method initializes castList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getCastList() {
		if (castList == null) {
			castList = new JList();
			castList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					editButton.setVisible(true);
					removeButton.setVisible(true);

					MovieCast selectedCast = (MovieCast) castList
							.getSelectedValue();
					nameField.setText(selectedCast.getActor());
					if (selectedCast.getJob().equalsIgnoreCase("actor")) {
						roleOrJobField.setText(selectedCast.getRole());
						actorCheckBox.setSelected(true);
					} else {
						roleOrJobField.setText(selectedCast.getJob());
						actorCheckBox.setSelected(false);
					}
				}

			});

		}
		return castList;
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
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.gridy = 3;
			actorLabel = new JLabel();
			actorLabel.setText(SettingsLoader.getValue("lang.actor_checkbox",
					"Näyttelijä:"));
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.gridy = 2;
			roleOrJobLabel = new JLabel();
			roleOrJobLabel.setText(SettingsLoader.getValue("lang.role_or_job",
					"Rooli/Työ:"));
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.gridy = 1;
			nameLabel = new JLabel();
			nameLabel.setText(SettingsLoader.getValue("lang.name", "Nimi:"));
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridx = 1;
			controlsPanel = new JPanel();
			controlsPanel.setLayout(new GridBagLayout());
			controlsPanel.add(getNameField(), gridBagConstraints11);
			controlsPanel.add(nameLabel, gridBagConstraints2);
			controlsPanel.add(roleOrJobLabel, gridBagConstraints3);
			controlsPanel.add(getRoleOrJobField(), gridBagConstraints4);
			controlsPanel.add(actorLabel, gridBagConstraints5);
			controlsPanel.add(getActorCheckBox(), gridBagConstraints6);
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
	 * This method initializes roleOrJobField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getRoleOrJobField() {
		if (roleOrJobField == null) {
			roleOrJobField = new JTextField();
		}
		return roleOrJobField;
	}

	/**
	 * This method initializes actorCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getActorCheckBox() {
		if (actorCheckBox == null) {
			actorCheckBox = new JCheckBox();
		}
		return actorCheckBox;
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
			addButton.setText(SettingsLoader.getValue("lang.add", "Lisää"));
			addButton.setActionCommand("add");
			addButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (nameField.getText().length() > 0) {
						MovieCast cast = new MovieCast();
						cast.setActor(nameField.getText());
						cast.setJob(actorCheckBox.isSelected() ? "Actor"
								: roleOrJobField.getText());
						cast.setRole(actorCheckBox.isSelected() ? roleOrJobField
								.getText() : "");
						castData.add(cast);
						castList.setListData(castData);
						castList.revalidate();
						repaint();
					} else {
						JOptionPane.showMessageDialog(null, SettingsLoader.getValue("lang.fill_all_fields", "Täytä kaikki kentät"));
					}
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
			removeButton.setText(SettingsLoader.getValue("lang.remove",
					"Poista"));
			removeButton.setActionCommand("remove");
			removeButton.setVisible(false);
			removeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					int i = castList.getSelectedIndex();
					castData.remove(i);
					castList.revalidate();
					repaint();

					if (i > 0) {
						castList.setSelectedIndex(i - 1);
					} else if (castData.size() > 0) {
						castList.setSelectedIndex(0);
					} else {
						nameField.setText("");
						roleOrJobField.setText("");
						actorCheckBox.setSelected(false);
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
			backButton.setText(SettingsLoader
					.getValue("lang.back", "Edellinen"));
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
			nextButton
					.setText(SettingsLoader.getValue("lang.next", "Seuraava"));
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
			editButton
					.setText(SettingsLoader.getValue("lang.save", "Tallenna"));
			editButton.setActionCommand("edit");
			editButton.setVisible(false);
			editButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					MovieCast selectedCast = (MovieCast) castList
							.getSelectedValue();
					selectedCast.setActor(nameField.getText());
					selectedCast.setJob(actorCheckBox.isSelected() ? "Actor"
							: roleOrJobField.getText());
					selectedCast.setRole(actorCheckBox.isSelected() ? roleOrJobField
							.getText() : "");
					castList.revalidate();
					repaint();
				}

			});
		}
		return editButton;
	}

	/**
	 * Aseta elokuvan näyttelijät/tuontantoryhmä
	 * 
	 * @param cast
	 *            näyttelijät/tuontantoryhmä
	 */
	public void setMovieCast(ArrayList<MovieCast> cast) {
		castData = new Vector<MovieCast>(cast);
		castList.setListData(castData);

	}

	/**
	 * Palauta elokuvan näyttelijät/tuotantorymä
	 * 
	 * @return näyttelijät/tuotantorymä
	 */
	public ArrayList<MovieCast> getMovieCast() {

		return new ArrayList<MovieCast>(castData);
	}

	/**
	 * Lisää tapahtumakuuntelija kuuntelemaan back, next nappien tapahtumia.
	 * 
	 * @param al
	 *            tapahtumakuunteli
	 */
	public void addActionListener(ActionListener al) {
		nextButton.addActionListener(al);
		backButton.addActionListener(al);
	}

	/**
	 * Poista back ja next nappuloilta tapahtumakuuntelija
	 * 
	 * @param al
	 */
	public void removeActionListener(ActionListener al) {
		nextButton.removeActionListener(al);
		backButton.removeActionListener(al);
	}

}
