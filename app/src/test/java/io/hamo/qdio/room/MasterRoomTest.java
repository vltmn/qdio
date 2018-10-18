package io.hamo.qdio.room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayDeque;
import java.util.Queue;

import io.hamo.qdio.testutil.MusicData;
import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.information.MusicObjectFactory;
import io.hamo.qdio.model.communication.CommandMessage;
import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.playback.Player;
import io.hamo.qdio.playback.PlayerFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(PlayerFactory.class)
public class MasterRoomTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();
    @Mock
    Communicator communicator;
    @Mock
    Player player;

    @Before
    public void setUp() throws Exception {
        Queue<CommandMessage> queue = new ArrayDeque<>();
        MutableLiveData<Queue<CommandMessage>> messages = new MutableLiveData<>();
        messages.setValue(queue);
        when(communicator.getIncomingMessages()).thenReturn(messages);
        PowerMockito.mockStatic(PlayerFactory.class);
        PowerMockito.when(PlayerFactory.getPlayer()).thenReturn(player);

    }

    @Test
    public void addToQueue() {
        MasterRoom masterRoom = new MasterRoom(communicator);
        Track t1 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t2 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t3 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        Track t4 = MusicObjectFactory.createTrack(MusicData.getInstance().getTestTrack());
        masterRoom.addToQueue(t1);
        assertEquals(t1, masterRoom.getCurrentSong());
        masterRoom.addToQueue(t2);
        masterRoom.addToQueue(t3);
        masterRoom.addToQueue(t4);
        assertTrue(!masterRoom.getQueueList().isEmpty());
        assertEquals(3, masterRoom.getQueueList().size());

    }

    @Test
    public void getType() {
    }

    private MasterRoom getRoom() {
        return new MasterRoom(communicator);
    }
}