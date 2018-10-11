package io.hamo.qdio;

import org.junit.Test;

import java.util.Arrays;

import io.hamo.qdio.TestUtil.MusicData;
import io.hamo.qdio.information.MusicObjectFactory;
import io.hamo.qdio.model.SongHistory;
import io.hamo.qdio.model.music.Track;

import static org.junit.Assert.*;

public class SongHistoryTest {

    @Test
    public void getPlaybackHistory() {
        SongHistory playbackHistory = new SongHistory();
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        playbackHistory.add(t1);
        playbackHistory.add(t2);
        assertEquals(playbackHistory.getPlaybackHistory(), Arrays.asList(t1, t2));
    }
}