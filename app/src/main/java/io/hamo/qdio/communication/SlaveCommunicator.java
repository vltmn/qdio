package io.hamo.qdio.communication;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Queue;

import io.hamo.qdio.communication.entity.CommandMessage;

public class SlaveCommunicator implements Communicator{
    private final MutableLiveData<Queue<Payload>> incomingPayload;
    private final String masterEndpoint;
    private final ConnectionsClient connectionsClient;


    public SlaveCommunicator(String masterEndpoint, MutableLiveData<Queue<Payload>> incomingPayload, ConnectionsClient connectionsClient) {
        this.incomingPayload = incomingPayload;
        this.masterEndpoint = masterEndpoint;
        this.connectionsClient = connectionsClient;

    }

    @Override
    public LiveData<Queue<CommandMessage>> getIncomingMessages() {
        return null;
    }

    @Override
    public void sendCommand(CommandMessage commandMessage) {
        String serialized = JsonUtil.getInstance().serialize(commandMessage);
        connectionsClient.sendPayload(masterEndpoint, Payload.fromBytes(serialized.getBytes()))
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(getClass().getSimpleName(), "transfer succeeded");
            }
        });

    }

}
