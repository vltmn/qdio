package io.hamo.qdio.room;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Queue;

import io.hamo.qdio.SongHistory;
import io.hamo.qdio.SongQueueList;
import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.entity.CommandAction;
import io.hamo.qdio.communication.entity.CommandMessage;
import io.hamo.qdio.music.Track;

public class SlaveRoom implements Room {

    Communicator communicator;
    Track currentSong;
    SongQueueList queueList;
    SongHistory history;
    RoomType type = RoomType.SLAVE;

    public SlaveRoom(Communicator com){
        this.communicator = com;
        com.getIncomingMessages().observeForever(new Observer<Queue<CommandMessage>>() {
            @Override
            public void onChanged(@Nullable Queue<CommandMessage> commandMessages) {
                while(!commandMessages.isEmpty()){
                    CommandMessage cmdMsg = commandMessages.poll();
                    switch (cmdMsg.getAction()){
                        case NOTIFY_UPDATE:
                            Log.i(getClass().getSimpleName(), cmdMsg.getValue());
                            // TODO: Handle command
                            break;
                        case ADD_SONG:
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
        communicator.sendCommand(
                new CommandMessage(CommandAction.ADD_SONG, track.getURI())
        );
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
        return currentSong;
    }

    @Override
    public RoomType getType() {
        return type;
    }
}
