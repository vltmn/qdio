package io.hamo.qdio;

import java.util.LinkedList;
import java.util.List;

import io.hamo.qdio.music.MusicObject;
import io.hamo.qdio.music.Track;

/**
 * Songs that have been played end up here in a playback history list
 */
public class SongHistory {
    private List<Track> playbackHistory = new LinkedList<>();

    public List<Track> getPlaybackHistory() {
        return playbackHistory;
    }

    public void add(Track track) {
        playbackHistory.add(track);
    }


}
