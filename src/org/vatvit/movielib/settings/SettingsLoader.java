package org.vatvit.movielib.settings;

public class SettingsLoader {
	public static String getValue(String name) {
		return null;
	}
	public static String[] getValues(String name) {
		return null;
	}
	public static String getValue(String name, String defaultValue) {
		String value = getValue(name);
		if(value==null) {
			return defaultValue;
		} else {
			return value;
		}
	}
	public static String[] getValues(String name, String[] defaultValue) {
		String[] value = getValues(name);
		if(value==null) {
			return defaultValue;
		} else {
			return value;
		}
	}
}
