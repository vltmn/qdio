package io.hamo.qdio.model.room;

import io.hamo.qdio.model.SongHistory;
import io.hamo.qdio.model.SongQueueList;
import io.hamo.qdio.model.music.Track;

public class RoomData {

    private final SongQueueList queueList;
    private final SongHistory history;
    private Track currentTrack;

    public RoomData() {
        this.queueList = new SongQueueList();
        this.history = new SongHistory();
    }

    public void addToQueue(Track track){
        queueList.addSong(track);
    }

    public SongQueueList getQueueList() {
        return queueList;
    }

    public SongHistory getHistory() {
        return history;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }
}
