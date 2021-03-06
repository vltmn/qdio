package io.hamo.qdio.model.room;

import java.util.List;

import io.hamo.qdio.model.SongHistory;
import io.hamo.qdio.model.SongQueueList;
import io.hamo.qdio.model.music.Track;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 *
 *
 * Contains and handles all the data used in any of the two room types
 */
public class RoomData {

    private final SongQueueList queueList;
    private final SongHistory history;
    private Track currentTrack;

    public RoomData() {
        this.queueList = new SongQueueList();
        this.history = new SongHistory();
    }

    public void addToQueue(Track track) {
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

    public Track peekSongFromQueue() {
        return queueList.peekSong();
    }

    public void clearQueue() {
        while (queueList.peekSong() != null) {
            queueList.popSong();
        }
    }

    public void clearHistory() {
        history.clear();
    }

}
