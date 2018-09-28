package io.hamo.qdio.communication;


import android.arch.lifecycle.LiveData;

import java.util.Queue;

import io.hamo.qdio.communication.entity.CommandMessage;

/**
 *Manages communication between Master and Slave via Room services from Master– and SlaveRoom
 */
public interface Communicator {

    /**
     * takes a CommandMessage and serializes it with JsonUtil and Gson. A message is polled from Queue with Payload and posted to notify observers
     * @return messages
     */
    LiveData<Queue<CommandMessage>> getIncomingMessages();
    /**
     * sends requests from one device to another
     * @param commandMessage contains message with request
     */
    void sendCommand(CommandMessage commandMessage);




}
