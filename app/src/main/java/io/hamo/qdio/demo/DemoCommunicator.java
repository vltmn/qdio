package io.hamo.qdio.demo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.ImageView;

import java.util.Queue;

import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.entity.CommandMessage;

public class DemoCommunicator implements Communicator {
    private final LiveData<Queue<CommandMessage>> incomingMessages;


    public DemoCommunicator(LiveData<Queue<CommandMessage>> incomingMessages) {
        this.incomingMessages = incomingMessages;
    }

    @Override
    public LiveData<Queue<CommandMessage>> getIncomingMessages() {
        return incomingMessages;
    }

    @Override
    public void sendCommand(CommandMessage commandMessage) {
        Log.w(getClass().getSimpleName(), "Command message was sent to democommunicator, message was: " + commandMessage.toString());
    }
}
