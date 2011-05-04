package org.vatvit.movielib;

import org.vatvit.movielib.controllers.MovieController;
import org.vatvit.movielib.models.MovieModel;
import org.vatvit.movielib.settings.SettingsLoader;
import org.vatvit.movielib.views.MovieView;

public class Main {

	private static MovieModel loadModel(String modelName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object model = loader.loadClass(modelName).newInstance();
		return (MovieModel) model;
	}
	private static MovieView loadView(String viewName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object model = loader.loadClass(viewName).newInstance();
		return (MovieView) model;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String modelName = SettingsLoader.getValue("model.class","org.vatvit.movielib.models.MovieModelImpl");
		//String viewName = SettingsLoader.getValue("view.class","org.vatvit.movielib.views.ui.editor.MovieEditorViewImpl");
		String viewName = SettingsLoader.getValue("view.class","org.vatvit.movielib.views.ui.MovieViewImpl");
		
		if(args.length>0) {
			for(int i =0; i < args.length; i++) {
				if(args[i].equals("-m")) {
					modelName = args[i+1];
				} else if(args[i].equals("-v")) {
					viewName = args[i+1];
				}
			}
		}
		
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
		
		
		if(view!=null&&model!=null) {
		
			
			MovieController controller = new MovieController(model, view);
			
		} else {
			if(view==null) {
				System.out.println(viewName +" nimistä käyttöliittymää ei ole");
			}
			if(model==null) {
				System.out.println(modelName +" nimistä mallia ei ole");
				if(view!=null) {
					view.showError(modelName +" nimistä mallia ei ole");
				}
			}
			System.exit(0);
		}
		
	}

}
