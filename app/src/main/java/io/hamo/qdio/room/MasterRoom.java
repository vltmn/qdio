package io.hamo.qdio.room;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Queue;

import io.hamo.qdio.MusicData.MusicDataServiceFactory;
import io.hamo.qdio.SongHistory;
import io.hamo.qdio.SongQueueList;
import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.entity.CommandMessage;
import io.hamo.qdio.music.Track;

public class MasterRoom implements Room {

    Communicator communicator;
    SongQueueList queueList;
    SongHistory history;
    RoomType type = RoomType.MASTER;

    public MasterRoom(Communicator com) {
        this.communicator = com;
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

    @Override
    public void addToQueue(Track track) {
        queueList.addSong(track);
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
        return null;
    }

    @Override
    public RoomType getType() {
        return type;
    }
}
