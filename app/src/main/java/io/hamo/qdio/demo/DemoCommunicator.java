package io.hamo.qdio.demo;

import android.arch.lifecycle.LiveData;
import android.widget.ImageView;

import java.util.Queue;

import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.entity.CommandMessage;

public class DemoCommunicator implements Communicator {


    public DemoCommunicator() {
    }

    @Override
    public LiveData<Queue<CommandMessage>> getIncomingMessages() {
        
        return null;


    }

    @Override
    public void sendCommand(CommandMessage commandMessage) {

    }
}
