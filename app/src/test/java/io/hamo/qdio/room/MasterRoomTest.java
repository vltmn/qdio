package io.hamo.qdio.room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.DriverManager;
import java.util.ArrayDeque;
import java.util.Queue;

import io.hamo.qdio.TestUtil.MusicData;
import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.entity.CommandMessage;
import io.hamo.qdio.music.Track;
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
        Track t1 = new Track(MusicData.getInstance().getTestTrack());
        Track t2 = new Track(MusicData.getInstance().getTestTrack());
        masterRoom.addToQueue(t1);
        assertEquals(t1, masterRoom.getCurrentSong());
        masterRoom.addToQueue(t2);
        assertEquals(masterRoom.getQueueList().peekSong(), t2 );
    }

    @Test
    public void getQueueList() {

    }

    @Test
    public void getHistory() {
    }

    @Test
    public void getType() {
    }

    private MasterRoom getRoom() {
        MasterRoom masterRoom = new MasterRoom(communicator);
        return masterRoom;
    }
}