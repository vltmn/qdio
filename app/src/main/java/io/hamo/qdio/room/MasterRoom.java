package io.hamo.qdio.room;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.Queue;

import io.hamo.qdio.information.MusicDataServiceFactory;
import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.JsonUtil;
import io.hamo.qdio.model.communication.CommandAction;
import io.hamo.qdio.model.communication.CommandMessage;
import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.model.room.RoomData;
import io.hamo.qdio.model.room.RoomType;
import io.hamo.qdio.model.room.SerializableRoom;
import io.hamo.qdio.playback.Player;
import io.hamo.qdio.playback.PlayerFactory;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * Room implementation in the case of a master
 */
public class MasterRoom implements Room {

    private final Communicator communicator;
    private final RoomData roomData;
    private static final RoomType type = RoomType.MASTER;

    /**
     * Observes incoming CommandMessage from Communicator/slaves and depending on CommandAction
     * it either adds song to queue or breaks if bad command
     */
    public MasterRoom(Communicator com) {
        this.communicator = com;
        roomData = new RoomData();


        com.getIncomingMessages().observeForever(new Observer<Queue<CommandMessage>>() {
            @Override
            public void onChanged(@Nullable Queue<CommandMessage> commandMessages) {
                while (!commandMessages.isEmpty()) {
                    CommandMessage cmdMsg = commandMessages.poll();
                    switch (cmdMsg.getAction()) {
                        case ADD_SONG:
                            try {
                                addToQueue(MusicDataServiceFactory
                                        .getService()
                                        .getTrackFromUri(cmdMsg.getValue())
                                        .call());
                            } catch (Exception e) {
                                Log.e(getClass().getSimpleName(), e.getMessage());
                            }
                            sendNotifyUpdate();
                            break;
                        case NOTIFY_UPDATE:
                            break;
                        default:
                            Log.w(getClass().getSimpleName(), "Bad command message");
                            break;
                    }
                }
            }
        });
        PlayerFactory.getPlayer().setOnSongEndCallback(new Player.OnSongEndCallback() {
            @Override
            public Track onSongEnd() {
                roomData.addToHistory(roomData.getCurrentTrack());
                Track nextTrack = roomData.popSongFromQueue();
                roomData.setCurrentTrack(nextTrack);
                sendNotifyUpdate();
                return nextTrack;
            }
        });

    }

    private void sendNotifyUpdate() {
        CommandMessage notify = new CommandMessage(CommandAction.NOTIFY_UPDATE,
                JsonUtil.getInstance().serializeRoom(
                        new SerializableRoom(roomData)));
        communicator.sendCommand(notify);
    }

    @Override
    public void addToQueue(Track track) {
        if (roomData.peekSongFromQueue() == null && roomData.getCurrentTrack() == null) {
            roomData.setCurrentTrack(track);
            PlayerFactory.getPlayer().play(track);
        } else {
            roomData.addToQueue(track);
        }
        sendNotifyUpdate();
    }

    @Override
    public List<Track> getQueueList() {
        return roomData.getQueueAsList();
    }

    @Override
    public List<Track> getHistory() {
        return roomData.getHistoryAsList();
    }

    @Override
    public Track getCurrentSong() {
        return roomData.getCurrentTrack();
    }

    @Override
    public RoomType getType() {
        return type;
    }


}
