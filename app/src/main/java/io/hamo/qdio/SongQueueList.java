package io.hamo.qdio;

import java.util.PriorityQueue;
import java.util.Queue;

import io.hamo.qdio.music.MusicObject;

public class SongQueueList {
    private Queue<MusicObject> queueList = new PriorityQueue<>();


    public SongQueueList(Queue<MusicObject> queueList) {
        this.queueList = queueList;
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

}
