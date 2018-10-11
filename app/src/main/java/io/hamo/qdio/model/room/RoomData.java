package io.hamo.qdio.model.room;

import java.util.List;

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

    protected SongQueueList getQueueList() {
        return queueList;
    }

    protected SongHistory getHistory() {
        return history;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }

    public void addToHistory(Track track) {
        history.add(track);
    }
    public List<Track> getQueueAsList() {
        return queueList.getAsList();
    }

    public List<Track> getHistoryAsList() {
        return history.getPlaybackHistory();
    }

    public Track popSongFromQueue() {
        return queueList.popSong();
    }

    public Track peepSongFromQueue() {
        return queueList.peekSong();
    }

}
