package io.hamo.qdio.model.room;

import org.junit.Test;

import io.hamo.qdio.TestUtil.MusicData;
import io.hamo.qdio.information.MusicObjectFactory;
import io.hamo.qdio.model.music.Track;

import static org.junit.Assert.*;


public class RoomDataTest {

    @Test
    public void setCurrentTrack() {
        RoomData roomData = new RoomData();
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        roomData.setCurrentTrack(t1);
        assertEquals(t1, roomData.getCurrentTrack());

    }

    @Test
    public void addToHistory() {
        RoomData roomData = new RoomData();
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        roomData.addToHistory(t1);
        roomData.addToHistory(t2);
        assertEquals(2, roomData.getHistoryAsList().size());

    }

    @Test
    public void popSongFromQueue() {
        RoomData roomData = new RoomData();
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        roomData.addToQueue(t1);
        roomData.addToQueue(t2);
        assertEquals(t1, roomData.popSongFromQueue());
    }

    @Test
    public void peekSongFromQueue() {
        RoomData roomData = new RoomData();
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        roomData.addToQueue(t1);
        roomData.addToQueue(t2);
        assertEquals(t1, roomData.peekSongFromQueue());
    }
}