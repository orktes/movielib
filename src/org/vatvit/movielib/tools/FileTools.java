package org.vatvit.movielib.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileTools {

	/**
	 * Kopioi tiedoston
	 * @param orgin Tiedosto joka kopioidaan
	 * @param target Kohde mihin kopioidaan
	 * @throws IOException
	 */
	public static void copyFile(File orgin, File target) throws IOException {
		System.out.println(orgin.getAbsolutePath());
		System.out.println(target.getAbsolutePath());
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
