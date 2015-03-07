/**
 * Copyright (c) 2013, ControlsFX
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of ControlsFX, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package controlsfx.controlsfx.control.cell;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import controlsfx.controlsfx.control.GridCell;
import controlsfx.controlsfx.control.GridView;

/**
 * A {@link GridCell} that can be used to show media (i.e. movies) inside the 
 * {@link GridView} control.
 *
 * @see GridView
 */
public class MediaImageCell extends GridCell<Media> {
	
	private MediaPlayer mediaPlayer;
	private final MediaView mediaView;
	
	/**
	 * Creates a default MediaGridCell instance.
	 */
	public MediaImageCell() {
		getStyleClass().add("media-grid-cell"); //$NON-NLS-1$
		
		mediaView = new MediaView();
		mediaView.setMediaPlayer(mediaPlayer);
        mediaView.fitHeightProperty().bind(heightProperty());
        mediaView.fitWidthProperty().bind(widthProperty());
        mediaView.setMediaPlayer(mediaPlayer);
	}
	
	/**
	 * Pauses the media player inside this cell.
	 */
	public void pause() {
		if(mediaPlayer != null) {
			mediaPlayer.pause();
		}
	}
	
	/**
     * Starts playing the media player inside this cell.
     */
	public void play() {
		if(mediaPlayer != null) {
			mediaPlayer.play();
		}
	}
	
	/**
     * Stops playing the media player inside this cell.
     */
	public void stop() {
		if(mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override protected void updateItem(Media item, boolean empty) {
	    super.updateItem(item, empty);
	    
	    getChildren().clear();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
	    
	    if (empty) {
	        setGraphic(null);
	    } else {
	        mediaPlayer = new MediaPlayer(item);
	        mediaView.setMediaPlayer(mediaPlayer);
	        setGraphic(mediaView);
	    }
	}
}