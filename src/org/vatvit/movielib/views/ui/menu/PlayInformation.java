package org.vatvit.movielib.views.ui.menu;

public class PlayInformation {
	public String videoLocation;
	public String subtitleLocaltion;
	public String title;
	
	
	
	public PlayInformation(String title, String videoLocation, String subtitleLocaltion) {
		super();
		this.title = title;
		this.videoLocation = videoLocation;
		this.subtitleLocaltion = subtitleLocaltion;
	}
	public String getVideoLocation() {
		return videoLocation;
	}
	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}
	public String getSubtitleLocaltion() {
		return subtitleLocaltion;
	}
	public void setSubtitleLocaltion(String subtitleLocaltion) {
		this.subtitleLocaltion = subtitleLocaltion;
	}
	public String toString() {
		return title;
	}
	
	
	
}
