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

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.vatvit.movielib.views.ui.View;

public class MenuImage extends View {
	private BufferedImage image;
	private boolean center;
	private int paddingV;
	private int paddingH;
	
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
	public MenuImage(String filename) {
		this(filename, true, 0);
	}
	public MenuImage(String filename, int padding) {
		this(filename, true, padding);
	}
	public MenuImage(String filename, boolean center) {
		this(filename, center, 0);
		
	}
	public MenuImage(String filename, boolean center, int padding) {
		this(filename, center, padding, padding);
	}
	public MenuImage(String filename, boolean center, int paddingV, int paddingH) {
		setBackground(new Color(0, 0, 0));
		setImage(filename);
		this.center=center;
		this.paddingH=paddingH;
		this.paddingV=paddingV;
	}
	public MenuImage(BufferedImage image) {
		this(image, true, 0);
	}
	public MenuImage(BufferedImage image, int padding) {
		this(image, true, padding);
	}
	public MenuImage(BufferedImage image, boolean center) {
		this(image, center, 0);
		
	}
	public MenuImage(BufferedImage image, boolean center, int padding) {
		this(image, center, padding, padding);
	}
	public MenuImage(BufferedImage image, boolean center, int paddingV, int paddingH) {
		setBackground(new Color(0, 0, 0));
		this.image=image;
		this.center=center;
		this.paddingH=paddingH;
		this.paddingV=paddingV;
	}

	private BufferedImage resizeImage(BufferedImage img, int newW, int newH) {

		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
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
