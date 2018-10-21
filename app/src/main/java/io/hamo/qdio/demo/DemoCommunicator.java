package io.hamo.qdio.demo;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.Queue;

import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.model.communication.CommandMessage;

/**
 * Communicator to use when using the application in the emulator.
 * This way you can send commands to the application using broadcasted intents containing JSON commandmessages.
 * See the DemoRoomInitService for usage
 */
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
