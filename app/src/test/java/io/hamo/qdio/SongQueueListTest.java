package io.hamo.qdio;

import org.junit.Test;

import java.util.Arrays;

import io.hamo.qdio.TestUtil.MusicData;

import io.hamo.qdio.information.MusicObjectFactory;
import io.hamo.qdio.model.SongQueueList;
import io.hamo.qdio.model.music.Track;

import static org.junit.Assert.*;

public class SongQueueListTest {


    @Test
    public void testAddSong() {
        SongQueueList queueList = new SongQueueList();
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        queueList.addSong(t1);
        queueList.addSong(t2);
        assertEquals(queueList.popSong(), t1);
        assertEquals(queueList.popSong(), t2);
    }

    @Test
    public void testPeekSong() {
        SongQueueList queueList = new SongQueueList();
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        queueList.addSong(t1);
        queueList.addSong(t2);
        assertEquals(queueList.peekSong(), t1);

    }

    @Test
    public void testGetAsList() {
        SongQueueList queueList = new SongQueueList();
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        queueList.addSong(t1);
        queueList.addSong(t2);
        assertEquals(queueList.getAsList(), Arrays.asList(t1, t2));

    }
}