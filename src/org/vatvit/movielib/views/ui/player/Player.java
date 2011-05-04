package org.vatvit.movielib.views.ui.player;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import org.vatvit.movielib.views.ui.View;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class Player extends View {
	private EmbeddedMediaPlayer mediaPlayer;
	private MediaPlayerFactory mediaPlayerFactory;
	private ActionListener actionListener;

	public void focus() {

	}

	public Player(Window window) {

		setBackground(new Color(0, 0, 0));
		setLayout(new BorderLayout());

		Canvas canvas = new Canvas();

		add(canvas, BorderLayout.CENTER);

		 mediaPlayerFactory = new MediaPlayerFactory(
				new String[] { "--no-video-title-show" });
		mediaPlayer =  mediaPlayerFactory.newEmbeddedMediaPlayer(null);
		mediaPlayer.setPlaySubItems(true);
	

		mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvas));

		getActionMap().put("start", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				} else {
					mediaPlayer.play();

				}

			}
		});

		getActionMap().put("back", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mediaPlayer.stop();

				actionListener.actionPerformed(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED, "back"));

			}
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("SPACE"), "start");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "back");

	}

	public EmbeddedMediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(EmbeddedMediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public void play(String video) {
		play(video, null);
	}

	public void play(String video, String subtitle) {
		System.out.println(video);
		
		mediaPlayer.playMedia(video);
		
		if (subtitle != null && subtitle.length() > 0) {
			mediaPlayer.setSubTitleFile(subtitle);
		}
	}

	public void release() {
		mediaPlayer.release();
		mediaPlayerFactory.release();
	}

}
