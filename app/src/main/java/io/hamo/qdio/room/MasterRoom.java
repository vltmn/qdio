package io.hamo.qdio.room;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Queue;

import io.hamo.qdio.information.MusicDataServiceFactory;
import io.hamo.qdio.model.SongHistory;
import io.hamo.qdio.model.SongQueueList;
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

public class MasterRoom implements Room {

    private final Communicator communicator;
    private final RoomData roomData;
    private static final RoomType type = RoomType.MASTER;


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
                roomData.getHistory().add(roomData.getCurrentTrack());
                Track nextTrack = roomData.getQueueList().popSong();
                roomData.setCurrentTrack(nextTrack);
                return nextTrack;
            }
        });

    }

    private void sendNotifyUpdate(){
        CommandMessage notify = new CommandMessage(CommandAction.NOTIFY_UPDATE,
                JsonUtil.getInstance().serializeRoom(
                        new SerializableRoom(
                                roomData.getQueueList(),
                                roomData.getHistory(),
                                roomData.getCurrentTrack())
                ));
        communicator.sendCommand(notify);
    }

    @Override
    public void addToQueue(Track track) {
        if (roomData.getQueueList().peekSong() == null && roomData.getCurrentTrack() == null) {
            roomData.setCurrentTrack(track);
            PlayerFactory.getPlayer().play(track);
        } else {
            roomData.addToQueue(track);
        }
    }

    @Override
    public SongQueueList getQueueList() {
        return roomData.getQueueList();
    }

    @Override
    public SongHistory getHistory() {
        return roomData.getHistory();
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
