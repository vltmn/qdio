package io.hamo.qdio.room;

import java.util.List;

import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.model.room.RoomType;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * Serves as a middle hand between Rooms and their communicators. The different Rooms observes incoming CommandMessages via ICommunicator.
 * SlaveRoom gets playback status via MasterRoom and the Room interface
 */
public interface Room {

    /**
     * Adds a track to queue unless currentTrack == null. Then track starts playing
     *
     * @param track
     */
    void addToQueue(Track track);

    /**
     * Get the queue list for the current room
     *
     * @return the queue
     */
    List<Track> getQueueList();

    /**
     * Get the history of this room, all songs that have been player
     *
     * @return a list of the songs that have been played
     */
    List<Track> getHistory();

    /**
     * Gets the current song that is playing
     *
     * @return the song
     */
    Track getCurrentSong();

    /**
     * Get the type of the room
     *
     * @return the room type
     */
    RoomType getType();

}
