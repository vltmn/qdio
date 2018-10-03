package io.hamo.qdio;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.hamo.qdio.TestUtil.MusicData;
import io.hamo.qdio.music.MusicObject;
import io.hamo.qdio.music.Track;

import static org.junit.Assert.*;

public class SongHistoryTest {

    @Test
    public void getPlaybackHistory() {
        SongHistory playbackHistory = new SongHistory();
        Track t1 = new Track(MusicData.getInstance().getTestTrack());
        Track t2 = new Track(MusicData.getInstance().getTestTrack());
        playbackHistory.add(t1);
        playbackHistory.add(t2);
        assertEquals(playbackHistory.getPlaybackHistory(), Arrays.asList(t1, t2));
    }
}