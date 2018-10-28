package io.hamo.qdio.playback;

import io.hamo.qdio.model.music.MusicObject;
import io.hamo.qdio.model.music.Track;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 *
 *
 * Interface that describes an object playing music
 */

public interface Player {
    /**
     * Starts playing music from device where it was paused
     */
    void resume();

    /**
     * pauses the music playing from the device
     */
    void pause();

    /**
     * forwards or rewinds in specified milliseconds in the music playing on the device
     *
     * @param ms the amoount of milliseconds that seek forward or backwards in the music
     */
    void seek(long ms);

    /**
     * Starts playing the music from the device
     *
     * @param obj interface that exposes logic to get uri for the song to play
     */
    void play(MusicObject obj);

    /**
     * Skips to the next track in the queue
     */
    void nextTrack();

    /**
     * Fetching the track that is playing
     *
     * @return trackobject
     */
    Track getCurrentTrack();

    /**
     * Gives the position in the track playing currently playing
     *
     * @return Returns a long that descripes postion in milliseconds
     */
    Long getCurrentPosition();

    /**
     * Gives value of the state of the music playing on device
     *
     * @return enum STOPPED, PAUSED, PLAYING
     */
    PlayerState getPlayerState();

    /**
     * sets a callback to be executed when a song has ended
     *
     * @param onSongEndCallback callback to be executed
     */

    void setOnSongEndCallback(OnSongEndCallback onSongEndCallback);

    /**
     * Interfacet that describes what to do when a song has ended
     */
    interface OnSongEndCallback {

        Track onSongEnd();

    }


}
