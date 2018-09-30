package io.hamo.qdio.communication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Queue;

import io.hamo.qdio.communication.entity.CommandMessage;

/**
 * Managing communication from hosts to all units connected to its room
 */
public class MasterCommunicator implements Communicator {

    private final MutableLiveData<Queue<Payload>> payloadQueue;
    private final MutableLiveData<Queue<CommandMessage>> messages;
    private final List<String> endpoints;
    private final ConnectionsClient connectionsClient;

    public MasterCommunicator(MutableLiveData<Queue<Payload>> payloadQueue, List<String> endpoints, ConnectionsClient connectionsClient) {
        this.payloadQueue = payloadQueue;
        this.endpoints = endpoints;
        this.messages = new MutableLiveData<>();
        this.connectionsClient = connectionsClient;
        payloadQueue.observeForever(new Observer<Queue<Payload>>() {
            @Override
            public void onChanged(@Nullable Queue<Payload> payloads) {
                Queue<CommandMessage> value = messages.getValue();

                while (!payloads.isEmpty()) {
                    Payload poll = payloads.poll();
                    String json = new String(poll.asBytes());
                    CommandMessage commandMessage = JsonUtil.getInstance().deserializeCommandMessage(json);
                    value.add(commandMessage);
                }
                //post the value to update the observers
                messages.postValue(value);
            }
        });

    }

    private void sendPayloadToEndpoint(String serializedMsg, String endpoint) {
        connectionsClient.sendPayload(endpoint, Payload.fromBytes(serializedMsg.getBytes())).
                addOnSuccessListener((new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(getClass().getSimpleName(), "transfer succeeded");
                    }
                }));
    }

    @Override
    public void sendCommand(CommandMessage commandMessage) {
        String serialized = JsonUtil.getInstance().serializeCommandMessage(commandMessage);
        for (String endpoint : endpoints) {
            sendPayloadToEndpoint(serialized, endpoint);
        }
    }

    @Override
    public LiveData<Queue<CommandMessage>> getIncomingMessages() {
        return messages;
    }


}
