package io.hamo.qdio;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import io.hamo.qdio.TestUtil.MusicData;

import io.hamo.qdio.music.MusicObject;
import io.hamo.qdio.music.Track;

import static org.junit.Assert.*;

public class SongQueueListTest {


    @Test
    public void testAddSong() {
        SongQueueList queueList = new SongQueueList();
        Track t1 = new Track(MusicData.getInstance().getTestTrack());
        Track t2 = new Track(MusicData.getInstance().getTestTrack());
        queueList.addSong(t1);
        queueList.addSong(t2);
        assertEquals(queueList.popSong(), t1);
        assertEquals(queueList.popSong(), t2);
    }

    @Test
    public void testPeekSong() {
        SongQueueList queueList = new SongQueueList();
        Track t1 = new Track(MusicData.getInstance().getTestTrack());
        Track t2 = new Track(MusicData.getInstance().getTestTrack());
        queueList.addSong(t1);
        queueList.addSong(t2);
        assertEquals(queueList.peekSong(), t1);

    }

    @Test
    public void testGetAsList() {
        SongQueueList queueList = new SongQueueList();
        Track t1 = new Track(MusicData.getInstance().getTestTrack());
        Track t2 = new Track(MusicData.getInstance().getTestTrack());
        queueList.addSong(t1);
        queueList.addSong(t2);
        assertEquals(queueList.getAsList(), Arrays.asList(t1, t2));

    }
}