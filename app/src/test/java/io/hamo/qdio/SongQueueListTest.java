package io.hamo.qdio;

import org.junit.Test;

import io.hamo.qdio.TestUtil.MusicData;

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
    public void testPopSong() {

    }
}