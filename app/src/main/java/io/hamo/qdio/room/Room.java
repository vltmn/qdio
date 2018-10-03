package io.hamo.qdio.room;

import io.hamo.qdio.SongHistory;
import io.hamo.qdio.SongQueueList;
import io.hamo.qdio.music.Track;

/**
 * Serves as a middle hand between Rooms and their communicators. The different Rooms observes incoming CommandMessages via ICommunicator.
 * SlaveRoom gets playback status via MasterRoom and the Room interface
 */
public interface Room {

    void addToQueue(Track track);

    SongQueueList getQueueList();

    SongHistory getHistory();

    Track getCurrentSong();

    RoomType getType();

}
