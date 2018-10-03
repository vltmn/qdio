package io.hamo.qdio;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import io.hamo.qdio.music.MusicObject;

public class SongQueueList {
    private Queue<MusicObject> queueList = new ArrayDeque<>();


    public SongQueueList() {
    }

    public void addSong(MusicObject musicObject) {
        queueList.add(musicObject);
    }

    public MusicObject popSong() {
        return queueList.poll();
    }

    public MusicObject peekSong() {
        return queueList.peek();
    }

    public List<MusicObject> getAsList() {
        return new ArrayList<>(queueList);
    }

}
