package io.hamo.qdio.communication;


import android.arch.lifecycle.LiveData;

import java.util.Queue;

import io.hamo.qdio.communication.entity.CommandMessage;

public interface Communicator {

    LiveData<Queue<CommandMessage>> getIncomingMessages();
    void sendCommand(CommandMessage commandMessage);




}
