package org.vatvit.movielib.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vatvit.movielib.tools.FileTools;

/**
 * Asetusten lataamisen JSON -muotoisesta asetustiedosta tarkoitettu luokka.
 * 
 */
public class SettingsLoader {

	private static JSONObject settingsObject = null;

	/**
	 * Palauttaa asetustiedoston sisällön JSON -muodossa
	 */
	public static String getJSON() {
		return settingsObject.toString();
	}

	/**
	 * Asettaa asetustiedoston sijainnin
	 * 
	 * @param file
	 *            asetustiedosto
	 * @return oliko tiedosto olemassa / onnistuiko asetus
	 */
	public static boolean setSource(File file) {
		if (file.exists()) {
			loadData(file.getAbsolutePath());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Asettaa asetustiedoston String muotoisena JSON -objektina
	 * 
	 * @param jsonObject
	 *            JSON -objekti String -muodossa.
	 * @return onnistuiko tiedoston asetus
	 */
	public static boolean setSource(String jsonObject) {
		try {
			settingsObject = new JSONObject(jsonObject);
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Lataa asetustiedoston sisällön JSON -objektissa
	 * 
	 * @param filename
	 *            asetustiedosto
	 */
	private static void loadData(String filename) {

		File file = new File(filename);

		if (file.exists()) {
			StringBuffer contents = new StringBuffer();
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(file));
				String text = null;

				while ((text = reader.readLine()) != null) {
					contents.append(text).append(
							System.getProperty("line.separator"));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				settingsObject = new JSONObject(contents.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else {
			settingsObject = new JSONObject();
		}

	}

	/**
	 * Asettaa oletus asetustiedoston (movielib.conf) käyttöön
	 */
	private static void init() {
		if (settingsObject == null) {
			loadData(FileTools.getProgramDirectory()+File.separator+"movielib.conf");
		}
	}

	/**
	 * Hakee asetustiedostosta pyydetyn asetuksen
	 * 
	 * Esimerkit: { "key" : "value" } getValue("key") = "value"
	 * 
	 * { "key" : { "sub1" : "value1", "sub2" : "value2" } } getValue("key.sub2")
	 * = "value2"
	 * 
	 * @param name
	 *            asetuksen nimi
	 * @return arvo
	 */
	public static String getValue(String name) {
		try {
			init();
			String[] steps = name.split("\\.");
			JSONObject lastObject = settingsObject;
			for (int i = 0; i < steps.length - 1; i++) {
				lastObject = lastObject.getJSONObject(steps[i]);
			}
			return lastObject.getString(steps[steps.length - 1]);
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Hakee asetustiedostosta pyydetyn listamuotoisen asetuksen
	 * 
	 * Esimerkit: { "key" : ["value1", "value2"] } getValues("key") = ["value1",
	 * "value2"]
	 * 
	 * { "key" : { "sub1" : ["value1", "value2"], "sub2" : ["value3", "value4"]
	 * } } getValues("key.sub2") = ["value3", "value4"]
	 * 
	 * @param name
	 *            asetuksen nimi
	 * @return arvo
	 */
	public static String[] getValues(String name) {
		try {
			init();
			String[] steps = name.split("\\.");
			JSONObject lastObject = settingsObject;
			for (int i = 0; i < steps.length - 1; i++) {
				lastObject = lastObject.getJSONObject(steps[i]);
			}
			JSONArray array = lastObject.getJSONArray(steps[steps.length - 1]);
			String[] values = new String[array.length()];
			for (int i = 0; i < array.length(); i++) {
				values[i] = array.getString(i);
			}
			return values;
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Hakee asetustiedostosta asetusta. Mikäli annetulla nimellä ei läöydy
	 * asetusta palautetaan oletusarvo.
	 * 
	 * Esimerkit:
	 * katso kohta getValue();
	 * 
	 * @param name arvon nimi
	 * @param defaultValue oletusarvo
	 * @return arvo
	 */
	public static String getValue(String name, String defaultValue) {
		String value = getValue(name);
		if (value == null) {
			System.out.println(name + " ei asetettu käytetään oletusta: "
					+ defaultValue);
			return defaultValue;
		} else {
			return value;
		}
	}

	/**
	 * Hakee asetustiedostosta asetusta. Mikäli annetulla nimellä ei löydy
	 * asetusta palautetaan oletusarvo.
	 * 
	 * Esimerkit:
	 * katso kohta getValues();
	 * 
	 * @param name arvon nimi
	 * @param defaultValue oletusarvotaulukko
	 * @return arvo
	 */
	public static String[] getValues(String name, String[] defaultValue) {
		String[] value = getValues(name);
		if (value == null) {
			System.out.println(name + " ei asetettu käytetään oletusta");
			return defaultValue;
		} else {
			return value;
		}
	}

	public static void main(String[] args) {
		// {
		// "testi" : "testiFIRST",
		// "testi0": {
		// "testi1" : "testi123",
		// "testi2" : {
		// "testi3" : "testi321"
		// }
		// },
		// "testi4":["t1","t2","t3","t4"]
		// }

		String json = "{\"testi\" : \"testiFIRST\",\"testi0\": {\"testi1\" : \"testi123\",\"testi2\" : {\"testi3\" : \"testi321\"}},\"testi4\":[\"t1\",\"t2\",\"t3\",\"t4\"]}";
		SettingsLoader.setSource(json);

		System.out.println(SettingsLoader.getJSON());

		System.out.println(SettingsLoader.getValue("testi")); // TULOSTAA
																// testiFIRST
		System.out.println(SettingsLoader.getValue("testi0.testi1")); // TULOSTAA
																		// testi123
		System.out.println(SettingsLoader.getValue("testi0.testi2.testi3")); // TULOSTAA
																				// testi321
		System.out.println(Arrays.toString(SettingsLoader.getValues("testi4"))); // TULOSTAA
																					// [t1,
																					// t2,
																					// t3,
																					// t4]
	}
}
