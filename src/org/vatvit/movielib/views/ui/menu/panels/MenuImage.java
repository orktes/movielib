package org.vatvit.movielib.views.ui.menu.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.vatvit.movielib.views.ui.View;

/**
 * Kuvatiedostojen näyttämiseen tarkoitettu paneeli.
 */
/**
 * @author iida
 *
 */
public class MenuImage extends JPanel {
	private BufferedImage image;
	private boolean center;
	private int paddingV;
	private int paddingH;
	
	/**
	 * Aseta kuvatiedostosta
	 * @param filename tiedosto
	 */
	public void setImage(String filename) {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			try {

				image = ImageIO.read(imageFile);
				
			} catch (IOException e) {
			}
		} else {
			JLabel label = new JLabel("Kuvaa " + filename + " ei löytynyt");
			label.setForeground(new Color(255, 255, 255));
			label.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

			add(label);

		}
		
	}

	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth()+paddingH,image.getHeight()+paddingV);
		
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param filename tiedostonimi
	 */
	public MenuImage(String filename) {
		this(filename, true, 0);
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param filename tiedostonimi
	 * @param padding pehmustus pikseleinä
	 */
	public MenuImage(String filename, int padding) {
		this(filename, true, padding);
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param filename tiedostonimi
	 * @param center keskitetty
	 */
	public MenuImage(String filename, boolean center) {
		this(filename, center, 0);
		
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param filename tiedostonimi
	 * @param center keskitetty
	 * @param padding pehmustus pixeleinä
	 */
	public MenuImage(String filename, boolean center, int padding) {
		this(filename, center, padding, padding);
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param filename tiedostonimi
	 * @param center keskitetty
	 * @param paddingV pystysuuntainen pehmustus pikseleinä
	 * @param paddingH sivuttaisuuntainen pehmustus pikeseleinä
	 */
	public MenuImage(String filename, boolean center, int paddingV, int paddingH) {
		setBackground(new Color(0, 0, 0));
		setImage(filename);
		this.center=center;
		this.paddingH=paddingH;
		this.paddingV=paddingV;
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param url osoite
	 */
	public MenuImage(URL url) {
		this(url, true, 0);
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param url osoite
	 * @param padding pehmustus pikseleinä
	 */
	public MenuImage(URL url, int padding) {
		this(url, true, padding);
	}
	
	/**
	 * Luo uusi kuvapaneeli
	 * @param url osoite
	 * @param center keskitetty
	 */
	public MenuImage(URL url, boolean center) {
		this(url, center, 0);
		
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param url osoite
	 * @param center keskitetty
	 * @param padding pehmustus pikeseleinä
	 */
	public MenuImage(URL url, boolean center, int padding) {
		this(url, center, padding, padding);
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param url osoite
	 * @param center keskitetty
	 * @param paddingV pystysuuntainen pehmustus pikseleinä
	 * @param paddingH sivuttaisuuntainen pehmustus pikeseleinä
	 */
	public MenuImage(URL url, boolean center, int paddingV, int paddingH) {
		setBackground(new Color(0, 0, 0));
		try {
			this.image=ImageIO.read(url);
		} catch (IOException e) {
				JLabel label = new JLabel("Kuvaa " + url + " ei löytynyt");
				label.setForeground(new Color(255, 255, 255));
				label.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

				add(label);

			
		}
		this.center=center;
		this.paddingH=paddingH;
		this.paddingV=paddingV;
	}
	
	/**
	 * Luo uusi kuvapaneeli
	 * @param image kuva
	 */
	public MenuImage(BufferedImage image) {
		this(image, true, 0);
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param image kuva
	 * @param padding pehmustus pikeseleinä
	 */
	public MenuImage(BufferedImage image, int padding) {
		this(image, true, padding);
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param image kuva
	 * @param center keskitetty
	 */
	public MenuImage(BufferedImage image, boolean center) {
		this(image, center, 0);
		
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param image kuva
	 * @param center keskitetty
	 * @param padding pehmustus pikeseleinä
	 */
	public MenuImage(BufferedImage image, boolean center, int padding) {
		this(image, center, padding, padding);
	}
	/**
	 * Luo uusi kuvapaneeli
	 * @param image kuva
	 * @param center keskitetty
	 * @param paddingV pystysuuntainen pehmustus pikseleinä
	 * @param paddingH sivuttaisuuntainen pehmustus pikeseleinä
	 */
	public MenuImage(BufferedImage image, boolean center, int paddingV, int paddingH) {
		setBackground(new Color(0, 0, 0));
		this.image=image;
		this.center=center;
		this.paddingH=paddingH;
		this.paddingV=paddingV;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (image != null) {
			Dimension panelDim = this.getSize();
			if (center) {
				// Piirretään kuva keskelle paneelia
				int imageHeight = image.getHeight();
				int imageWidth = image.getWidth();
				g.drawImage(image, (panelDim.width - imageWidth) / 2,
						(panelDim.height - imageHeight) / 2, null);
			} else {

				g.drawImage(image, 0, 0, null);
			}

		}
	}

}
