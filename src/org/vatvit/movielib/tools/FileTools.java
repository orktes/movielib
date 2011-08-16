package org.vatvit.movielib.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;

import javax.xml.stream.Location;

/**
 * Tiedostojen käsittelyyn tarkoitettuja työkaluja sisältävä
 * apuluokka.
 */
public class FileTools {

	/**
	 * Missä kansiossa sovelluksen tiedostot sijaitsevat
	 * 
	 * @return Merkkijonona kansio, jossa sovellus sijaitsee
	 */
	public static String getProgramDirectory() {
		// Jos ajetaan jar tiedostosta
		CodeSource source = FileTools.class
				.getProtectionDomain().getCodeSource();
		if (source != null) {
			try {
				return (new File(source.getLocation().toURI()))
						.getParent();
			} catch (URISyntaxException e) {
			}
		}
		// Jos ajetaan suoraa class-tiedostoista
		return new File("").getAbsolutePath();

	}

	/**
	 * Kopioi tiedoston
	 * 
	 * @param orgin
	 *            Tiedosto joka kopioidaan
	 * @param target
	 *            Kohde mihin kopioidaan
	 * @throws IOException
	 */
	public static void copyFile(File orgin, File target)
			throws IOException {

		InputStream in = new FileInputStream(orgin);
		OutputStream out = new FileOutputStream(target);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

}
