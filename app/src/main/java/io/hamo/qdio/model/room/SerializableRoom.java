package io.hamo.qdio.model.room;

import java.util.ArrayList;
import java.util.List;

import io.hamo.qdio.model.SongHistory;
import io.hamo.qdio.model.SongQueueList;
import io.hamo.qdio.model.music.Track;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * This is used to represent a 'thin' class that should be used for transfer.
 * A complete RoomData object may be generated from this and an internet connection.
 */
public class SerializableRoom {
    private final List<String> queueList = new ArrayList<>();
    private final List<String> historyList = new ArrayList<>();
    private final String currentTrackURI;


    private SerializableRoom(SongQueueList songQueueList,
                             SongHistory historyList,
                             Track currentTrack) {
        for (Track track : songQueueList.getAsList()) {
            queueList.add(track.getURI());
        }
        for (Track track : historyList.getPlaybackHistory()) {
            this.historyList.add(track.getURI());
        }
        currentTrackURI = currentTrack == null ? null : currentTrack.getURI();
    }

    public SerializableRoom(RoomData roomData) {
        this(roomData.getQueueList(), roomData.getHistory(), roomData.getCurrentTrack());

    }


    public List<String> getQueueList() {
        return queueList;
    }

    public List<String> getHistoryList() {
        return historyList;
    }

    public String getCurrentTrackURI() {
        return currentTrackURI;
    }
}
