package org.vatvit.movielib.views.ui.menu;

/**
 * Elokuvan toistoasetusvalikon MovieSettingsMenu valintojen esittämiseen käytettävä transfer objekti
 */
public class PlayInformation {
	public String videoLocation;
	public String subtitleLocaltion;
	public String title;
	
	
	
	/**
	 * Luo uuden toisto objektin
	 * @param title otsikko
	 * @param videoLocation videotiedosto
	 * @param subtitleLocaltion tekstitystiedosto
	 */
	public PlayInformation(String title, String videoLocation, String subtitleLocaltion) {
		super();
		this.title = title;
		this.videoLocation = videoLocation;
		this.subtitleLocaltion = subtitleLocaltion;
	}
	/**
	 * Palauttaa videon sijainnin
	 * @return sijainti
	 */
	public String getVideoLocation() {
		return videoLocation;
	}
	/**
	 * Palauttaa elokuvan sijainnin
	 * @param videoLocation
	 */
	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}
	/**
	 * Palauttaa tekstitystiedoston sijainnin
	 * @return teksitystiedosto
	 */
	public String getSubtitleLocaltion() {
		return subtitleLocaltion;
	}
	/**
	 * Asettaa tekstitystiedoston
	 * @param subtitleLocaltion tekstitystiedosto
	 */
	public void setSubtitleLocaltion(String subtitleLocaltion) {
		this.subtitleLocaltion = subtitleLocaltion;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return title;
	}
	
	
	
}
