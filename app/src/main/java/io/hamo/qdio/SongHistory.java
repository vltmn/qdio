package io.hamo.qdio;

import java.util.LinkedList;
import java.util.List;

import io.hamo.qdio.music.MusicObject;

public class SongHistory {
    private List<MusicObject> playbackHistory = new LinkedList<>();

    public List<MusicObject> getPlaybackHistory() {
        return playbackHistory;
    }

    public void add(MusicObject musicObject) {
        playbackHistory.add(musicObject);
    }


}
