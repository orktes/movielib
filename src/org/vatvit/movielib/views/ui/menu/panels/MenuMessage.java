package org.vatvit.movielib.views.ui.menu.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.Dimension;
import javax.swing.JLabel;

import org.vatvit.movielib.settings.SettingsLoader;
import org.vatvit.movielib.views.ui.View;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MenuMessage extends View {

	private static final long serialVersionUID = 1L;
	private JLabel message = null;
	private JLabel guide = null;
	private ActionListener actionListener = null;
	private String messageContent = null;

	/**
	 * This is the default constructor
	 */
	public MenuMessage(String messageContent) {
		super();
		
		this.messageContent = messageContent;
		
		initialize();
		
		getActionMap().put("back", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				actionListener.actionPerformed(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED, "back"));

			}
		});
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "back");
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "back");
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		guide = new JLabel();
		guide.setText(SettingsLoader.getValue("lang.press_enter_to_go_back", "Paina ESC -painiketta palataksesi edelliseen näkymään"));
		guide.setForeground(Color.white);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		message = new JLabel();
		message.setText(messageContent);
		message.setForeground(Color.white);
		message.setFont(new Font("Lucida Grande", Font.PLAIN, 36));
		this.setSize(648, 226);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.BLACK);
		this.add(message, gridBagConstraints);
		this.add(guide, gridBagConstraints1);
		
		
	}
	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	
	public void focus() {
		setFocusable(true);
		requestFocusInWindow();
		requestFocus();
	}

}  //  @jve:decl-index=0:visual-constraint="365,169"
