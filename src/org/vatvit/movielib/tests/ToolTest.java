package org.vatvit.movielib.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.vatvit.movielib.objects.MovieCast;
import org.vatvit.movielib.objects.MovieGenre;
import org.vatvit.movielib.objects.MovieInfoResult;
import org.vatvit.movielib.objects.MovieSubtitle;
import org.vatvit.movielib.tools.EqualsTools;
import org.vatvit.movielib.tools.FileTools;
import org.vatvit.movielib.tools.ImageTools;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ToolTest extends TestCase {
	public void testEqualsToolsMovieCast() {
		ArrayList<MovieCast> castArrayA = new ArrayList<MovieCast>();
		ArrayList<MovieCast> castArrayB = new ArrayList<MovieCast>();

		MovieCast castA = new MovieCast();
		castA.setActor("bla");
		castA.setJob("foo");
		castA.setRole("bar");
		castArrayA.add(castA);

		MovieCast castB = new MovieCast();
		castB.setActor("bla");
		castB.setJob("foo");
		castB.setRole("bar");
		castArrayB.add(castB);

		Assert.assertTrue(EqualsTools.castArrayEquals(castArrayA, castArrayB));

		castArrayA.clear();

		Assert.assertFalse(EqualsTools.castArrayEquals(castArrayA, castArrayB));

	}

	public void testEqualsToolsMovieGenre() {
		ArrayList<MovieGenre> genreArrayA = new ArrayList<MovieGenre>();
		ArrayList<MovieGenre> genreArrayB = new ArrayList<MovieGenre>();

		MovieGenre genreA = new MovieGenre();
		genreA.setTitle("Kauhu");
		genreArrayA.add(genreA);

		MovieGenre genreB = new MovieGenre();
		genreB.setTitle("Kauhu");
		genreArrayB.add(genreB);

		Assert.assertTrue(EqualsTools
				.genreArrayEquals(genreArrayA, genreArrayB));

		genreArrayA.clear();

		Assert.assertFalse(EqualsTools.genreArrayEquals(genreArrayA,
				genreArrayB));

	}

	public void testEqualsToolsMovieSubtitle() {
		ArrayList<MovieSubtitle> subtitleArrayA = new ArrayList<MovieSubtitle>();
		ArrayList<MovieSubtitle> subtitleArrayB = new ArrayList<MovieSubtitle>();

		MovieSubtitle subA = new MovieSubtitle();
		subA.setLabel("label");
		subA.setLang("alng");
		subA.setLocation("location");
		subtitleArrayA.add(subA);

		MovieSubtitle subB = new MovieSubtitle();
		subB.setLabel("label");
		subB.setLang("alng");
		subB.setLocation("location");
		subtitleArrayB.add(subB);

		Assert.assertTrue(EqualsTools.subtitlesArrayEquals(subtitleArrayA,
				subtitleArrayB));

		subtitleArrayA.clear();

		Assert.assertFalse(EqualsTools.subtitlesArrayEquals(subtitleArrayA,
				subtitleArrayB));

	}

	public void testFileTools() throws IOException {
		FileTools.copyFile(new File("database.sqlite"), new File(
				"databaseCopy.sqlite"));
		Assert.assertTrue((new File("databaseCopy.sqlite").exists()));
	}

	public void testImageTools() throws IOException {
		URL imageUrl = new URL("http://www.google.fi/images/nav_logo70.png");
		BufferedImage img = ImageIO.read(imageUrl);
		img = ImageTools.resizeImage(img, 20, 20);
		Assert.assertEquals(20, img.getHeight());
		Assert.assertEquals(20, img.getWidth());

	}

}
