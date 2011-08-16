package org.vatvit.movielib;

import java.io.File;

import org.vatvit.movielib.controllers.MovieController;
import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.settings.SettingsLoader;
import org.vatvit.movielib.tools.FileTools;
import org.vatvit.movielib.views.MovieView;

import com.sun.jna.NativeLibrary;

public class Main {

	/**
	 * Lataa parametrinä annetun mallin
	 * @param modelName modelin luokka
	 * @return palauttaa MovieModel olion
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static MovieModel loadModel(String modelName)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object model = loader.loadClass(modelName).newInstance();
		return (MovieModel) model;
	}

	/**
	 * Lataa parametrinä annetun näkymän
	 * @param viewName viewin luokka
	 * @return palauttaa MovieView olion
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static MovieView loadView(String viewName)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object model = loader.loadClass(viewName).newInstance();
		return (MovieView) model;
	}

	/**
	 * MovieLib sovelluksen main-metodi. Sovellukselle voi antaa parametrinä
	 * modelin (-m luokka) ja viewin (-v luokka).
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(FileTools.getProgramDirectory());
		//Asetetaan asetuksissa määritetty polku vlc -kirjastoille.
		String vlcSearchPath = SettingsLoader.getValue("libvlc", "C:\\Program Files\\VideoLAN\\VLC").replace("{PROGRAM_DIR}", FileTools.getProgramDirectory()+File.separator);
		if(vlcSearchPath!=null) {
			File vspFile = new File(vlcSearchPath);
			if(vspFile.exists()) {
				NativeLibrary.addSearchPath("libvlc", vspFile.getAbsolutePath());
			} 
		}
		
		String modelName = SettingsLoader.getValue("model.class",
				"org.vatvit.movielib.models.MovieModelImpl");
		String viewName = SettingsLoader.getValue("view.class",
				"org.vatvit.movielib.views.ui.MovieViewImpl");
		
		
		//Tarkistetaan onko modelia tai viewiä annettu parametreinä
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-m")) {
					modelName = args[i + 1];
				} else if (args[i].equals("-v")) {
					viewName = args[i + 1];
				} else if (args[i].equals("player")) {
					viewName = "org.vatvit.movielib.views.ui.MovieViewImpl";
				} else if (args[i].equals("editor")) {
					viewName = "org.vatvit.movielib.views.ui.editor.MovieEditorViewImpl";
				}
			}
		}

		//Ladataan model ja view
		MovieModel model = null;
		try {
			model = loadModel(modelName);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MovieView view = null;
		try {
			view = loadView(viewName);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//tarkistetaan onnistuiko modelin ja viewin lataaminen
		if (view != null && model != null) {
			// luodaan controller ladattujen modelin ja viewin avulla
			MovieController controller = new MovieController(model, view);

		} else {
			if (view == null) {
				System.out
						.println(viewName + " nimistä käyttöliittymää ei ole");
			}
			if (model == null) {
				System.out.println(modelName + " nimistä mallia ei ole");
				if (view != null) {
					view.showError(modelName + " nimistä mallia ei ole");
				}
			}
			System.exit(0);
		}

	}

}
