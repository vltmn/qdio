package io.hamo.qdio.communication;

import javax.security.auth.callback.Callback;

import io.hamo.qdio.communication.entity.CommandMessage;

public interface Communicator {

    void sendCommand(CommandMessage commandMessage);
    void onReceiveCommand(Callback callback);
    IncomingMessageQueue getIncomingMessages();



}
