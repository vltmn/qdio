package io.hamo.qdio.communication;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Queue;

import io.hamo.qdio.communication.entity.CommandMessage;

/**
 * Managing communication from guests to host
 */
public class SlaveCommunicator implements Communicator {
    private final MutableLiveData<Queue<CommandMessage>> messages;
    private final MutableLiveData<Queue<Payload>> incomingPayload;
    private final String masterEndpoint;
    private final ConnectionsClient connectionsClient;



    public SlaveCommunicator(
            String masterEndpoint, MutableLiveData<Queue<Payload>> incomingPayload, ConnectionsClient connectionsClient) {
        this.incomingPayload = incomingPayload;
        this.masterEndpoint = masterEndpoint;
        this.messages = new MutableLiveData<>();
        this.connectionsClient = connectionsClient;
        incomingPayload.observeForever(new Observer<Queue<Payload>>() {
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

    @Override
    public LiveData<Queue<CommandMessage>> getIncomingMessages() {
        return messages;
    }

    @Override
    public void sendCommand(CommandMessage commandMessage) {
        String serialized = JsonUtil.getInstance().serializeCommandMessage(commandMessage);
        connectionsClient.sendPayload(masterEndpoint, Payload.fromBytes(serialized.getBytes()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(getClass().getSimpleName(), "transfer succeeded");
                    }
                });
    }

}
