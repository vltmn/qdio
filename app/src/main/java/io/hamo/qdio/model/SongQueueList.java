package io.hamo.qdio.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import io.hamo.qdio.model.music.Track;

/**
 * Holds the queue of songs as a List
 */
public class SongQueueList {
    private Queue<Track> queueList = new ArrayDeque<>();


    public SongQueueList() {
    }

    public void addSong(Track track) {
        queueList.add(track);
    }

    public Track popSong() {
        return queueList.poll();
    }

    public Track peekSong() {
        return queueList.peek();
    }

    public List<Track> getAsList() {
        return new ArrayList<>(queueList);
    }

}
