package io.hamo.qdio.room;

import java.util.List;

import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.model.room.RoomType;

/**
 * Serves as a middle hand between Rooms and their communicators. The different Rooms observes incoming CommandMessages via ICommunicator.
 * SlaveRoom gets playback status via MasterRoom and the Room interface
 */
public interface Room {

    /**
     * Adds a track to queue unless currentTrack == null. Then track starts playing
     * @param track
     */
    void addToQueue(Track track);

    List<Track> getQueueList();

    List<Track> getHistory();

    Track getCurrentSong();

    RoomType getType();

}
