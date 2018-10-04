package io.hamo.qdio.room;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Queue;

import io.hamo.qdio.MusicData.MusicDataServiceFactory;
import io.hamo.qdio.SongHistory;
import io.hamo.qdio.SongQueueList;
import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.JsonUtil;
import io.hamo.qdio.communication.entity.CommandAction;
import io.hamo.qdio.communication.entity.CommandMessage;
import io.hamo.qdio.music.Track;
import io.hamo.qdio.playback.Player;
import io.hamo.qdio.playback.PlayerFactory;

public class MasterRoom implements Room {

    private final Communicator communicator;
    private final SongQueueList queueList;
    private final SongHistory history;
    private Track currentTrack;
    private static final RoomType type = RoomType.MASTER;


    public MasterRoom(Communicator com) {
        this.communicator = com;
        queueList = new SongQueueList();
        history = new SongHistory();


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

    }

    private void sendNotifyUpdate(){
        CommandMessage notify = new CommandMessage(CommandAction.NOTIFY_UPDATE,
                JsonUtil.getInstance().serializeRoom(
                        new SerializableRoom(queueList, history, currentTrack)));
        communicator.sendCommand(notify);
    }

    @Override
    public void addToQueue(Track track) {
        if (queueList.peekSong() == null && currentTrack == null) {
            currentTrack = track;
            PlayerFactory.getPlayer().play(track);
        } else {
            queueList.addSong(track);
        }
    }

    @Override
    public SongQueueList getQueueList() {
        return queueList;
    }

    @Override
    public SongHistory getHistory() {
        return history;
    }

    @Override
    public Track getCurrentSong() {
        return currentTrack;
    }

    @Override
    public RoomType getType() {
        return type;
    }
}
