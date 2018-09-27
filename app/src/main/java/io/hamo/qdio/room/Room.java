package io.hamo.qdio.room;

import io.hamo.qdio.SongHistory;
import io.hamo.qdio.SongQueueList;
import io.hamo.qdio.music.Track;

public interface Room {

    void addToQueue(Track track);

    SongQueueList getQueueList();

    SongHistory getHistory();

    Track getCurrentSong();

    RoomType getType();

}
