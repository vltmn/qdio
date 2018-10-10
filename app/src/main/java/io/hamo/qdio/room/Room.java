package io.hamo.qdio.room;

import io.hamo.qdio.model.SongHistory;
import io.hamo.qdio.model.SongQueueList;
import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.model.room.RoomType;

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
