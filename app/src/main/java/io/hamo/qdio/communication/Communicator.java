package io.hamo.qdio.communication;



import android.arch.lifecycle.LiveData;

import io.hamo.qdio.communication.entity.CommandMessage;

public interface Communicator {

    LiveData<CommandMessage> getIncomingMessages();
    void sendCommand(CommandMessage commandMessage);



}
