package org.vatvit.movielib.views.ui.player;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSlider;

/**
 * Toiston tilasta kertova paneeli.
 * Paneeli näyttää elokuvan keston ja kohdan, jossa toisto kulloinkin on
 */
public class PlayDetails extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel timeLabel = null;
	private JSlider slider = null;
	private JLabel duration = null;

	/**
	 * This is the default constructor
	 */
	public PlayDetails() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 2;
		gridBagConstraints2.gridy = 0;
		duration = new JLabel();
		duration.setText("Duration");
		duration.setForeground(Color.WHITE);
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		timeLabel = new JLabel();
		timeLabel.setText("Time");
		timeLabel.setForeground(Color.WHITE);
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.BLACK);
		this.add(timeLabel, gridBagConstraints);
		this.add(getSlider(), gridBagConstraints1);
		this.add(duration, gridBagConstraints2);
	}

	/**
	 * This method initializes slider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getSlider() {
		if (slider == null) {
			slider = new JSlider();
			slider.setMinimum(0);
			slider.setMaximum(1000);
			slider.setBackground(Color.BLACK);
			slider.setFocusable(false);
		}
		return slider;
	}
	/**
	 * Aseta aika
	 * @param time aika
	 */
	public void setTime(String time) {
		timeLabel.setText(time);
	}
	/**
	 * Aseta kesto 
	 * @param time aika
	 */
	public void setDuration(String time) {
		duration.setText(time);
	}
	/**
	 * Aseta toiston kohta 1 - 1000
	 * @param position
	 */
	public void setPostion(int position) {
		slider.setValue(position);
	}
}
