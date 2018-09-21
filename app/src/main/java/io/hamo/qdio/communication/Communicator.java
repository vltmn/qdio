package io.hamo.qdio.communication;

import io.hamo.qdio.communication.entity.CommandMessage;

public interface Communicator {
    void sendCommand(CommandMessage commandMessage);


}
