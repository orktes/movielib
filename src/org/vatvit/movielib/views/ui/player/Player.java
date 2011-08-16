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
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import org.vatvit.movielib.models.MovieModel.ImageSize;
import org.vatvit.movielib.objects.Movie;
import org.vatvit.movielib.views.ui.View;
import org.vatvit.movielib.views.ui.menu.panels.MovieInfo;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * Videosoitin paneeli
 */
public class Player extends View {
	private EmbeddedMediaPlayer mediaPlayer;
	private MediaPlayerFactory mediaPlayerFactory;
	private ActionListener actionListener;
	private MovieInfo movieInfo;
	private PlayDetails playDetails;

	/* (non-Javadoc)
	 * @see org.vatvit.movielib.views.ui.View#focus()
	 */
	public void focus() {

	}

	/**
	 * Luo uusi soitin.
	 */
	public Player() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("%");
		
		setBackground(new Color(0, 0, 0));
		setLayout(new BorderLayout());

		Canvas canvas = new Canvas();
		add(canvas, BorderLayout.CENTER);
		
		playDetails = new PlayDetails();
		playDetails.setVisible(false);
		
		add(playDetails, BorderLayout.SOUTH);

		 mediaPlayerFactory = new MediaPlayerFactory(
				new String[] { "--no-video-title-show" });
		mediaPlayer =  mediaPlayerFactory.newEmbeddedMediaPlayer(null);
		mediaPlayer.setPlaySubItems(true);
	

		mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(canvas));
		mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventListener() {

			@Override
			public void backward(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void buffering(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void endOfSubItems(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void error(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void finished(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void forward(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void lengthChanged(MediaPlayer arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaChanged(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaDurationChanged(MediaPlayer arg0,
					long arg1) {
				playDetails.setDuration(formatIntoHHMMSS(arg1/1000));
				
			}

			@Override
			public void mediaFreed(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaMetaChanged(MediaPlayer arg0,
					int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaParsedChanged(MediaPlayer arg0,
					int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaStateChanged(MediaPlayer arg0,
					int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaSubItemAdded(MediaPlayer arg0,
					libvlc_media_t arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void newMedia(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void opening(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void pausableChanged(MediaPlayer arg0,
					int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void paused(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void playing(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void positionChanged(MediaPlayer arg0,
					float arg1) {
				if(playDetails.isVisible()) {
					playDetails.setPostion((int)(arg1*1000));
				}
				
			}

			@Override
			public void seekableChanged(MediaPlayer arg0,
					int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void snapshotTaken(MediaPlayer arg0,
					String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void stopped(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void subItemFinished(MediaPlayer arg0,
					int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void subItemPlayed(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void timeChanged(MediaPlayer arg0, long arg1) {
				
				if(playDetails.isVisible()) {
					playDetails.setTime(formatIntoHHMMSS(arg1/1000));
					
				}
			}

			@Override
			public void titleChanged(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});

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
		getActionMap().put("backward", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mediaPlayer.setTime(mediaPlayer.getTime()-1000*5);

			}
		});
		getActionMap().put("forward", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mediaPlayer.setTime(mediaPlayer.getTime()+1000*5);
			}
		});
		
		getActionMap().put("info", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(movieInfo!=null) {
					if(movieInfo.isVisible()) {
						movieInfo.setVisible(false);
						movieInfo.stopScrollProcess();	
					} else {
						movieInfo.setVisible(true);
						movieInfo.startScrollProcess();
						
					}
				}
				if(playDetails!=null) {
					if(playDetails.isVisible()) {
						playDetails.setVisible(false);
					} else {
						playDetails.setVisible(true);
					}
					validate();
				}

			}
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("SPACE"), "start");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "back");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "info");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "backward");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "forward");

	}

	/**
	 * Palauttaa soittimen EmbeddedMediaPlayer
	 * @return EmbeddedMediaPlayer
	 */
	public EmbeddedMediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	/**
	 * Aseta soittimen EmbeddedMediaPlayer
	 * @param mediaPlayer EmbeddedMediaPlayer
	 */
	public void setMediaPlayer(EmbeddedMediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	/**
	 * Palauttaa tapahtumakuuntelijan
	 * @return
	 */
	public ActionListener getActionListener() {
		return actionListener;
	}

	/**
	 * Asettaa tapahtumakuuntelijan
	 * @param actionListener
	 */
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	/**
	 * Toistaa videon
	 * @param video videotiedoston nimi
	 */
	public void play(String video) {
		play(video, null);
	}

	/**
	 * Toistaa videon tekstityksillä
	 * @param video videotiedoston nimi
	 * @param subtitle tekstitystiedoston nimi
	 */
	public void play(String video, String subtitle) {
		
		
		mediaPlayer.playMedia(video);
		
		if (subtitle != null && subtitle.length() > 0) {
			mediaPlayer.setSubTitleFile(subtitle);
		}
		

	}

	/**
	 * Vapauttaa soittimen käyttämän resurssit ja lopettaa infopaneelin scrollin
	 */
	public void release() {
		mediaPlayer.release();
		mediaPlayerFactory.release();
		if(movieInfo!=null) {
			movieInfo.stopScrollProcess();
		}
	}
	
	/**
	 * Asettaa infopaneelissa näytettävän elokuvan tiedot
	 * @param movie
	 */
	public void setMovie(Movie movie) {
		movieInfo = new MovieInfo(movie, movie.getCover(ImageSize.MEDIUM));
		movieInfo.setVisible(false);
		movieInfo.startScrollProcess();
		add(movieInfo, BorderLayout.WEST);
		
	}
	/**
	 * Muuntaa long muotoisen numeron aikaleimaksi
	 * @param secsIn
	 * @return aikaleima
	 */
	private String formatIntoHHMMSS(long secsIn)
	{

	int hours = (int) (secsIn / 3600),
	remainder = (int) (secsIn % 3600),
	minutes = remainder / 60,
	seconds = remainder % 60;

	return ( (hours < 10 ? "0" : "") + hours
	+ ":" + (minutes < 10 ? "0" : "") + minutes
	+ ":" + (seconds< 10 ? "0" : "") + seconds );

	}


}
