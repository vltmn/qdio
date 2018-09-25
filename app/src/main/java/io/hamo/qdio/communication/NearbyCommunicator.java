package io.hamo.qdio.communication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.hamo.qdio.communication.entity.CommandMessage;


public class NearbyCommunicator implements Communicator {
    public static final String SERVICE_ID = "Qdio";
    public static final Strategy STRATEGY = Strategy.P2P_STAR;
    public static final String roomAdmin = "admin";

    private final ConnectionsClient connectionsClient;

    private final MutableLiveData<Boolean> advertising;
    private final MutableLiveData<Boolean> discovering;
    private final MutableLiveData<Map<String, DiscoveredEndpointInfo>> endpointsFound;
    private final Queue<CommandMessage> inputMessageQueue = new ConcurrentLinkedQueue<>();

    public NearbyCommunicator(ConnectionsClient connectionsClient) {
        this.connectionsClient = connectionsClient;
        advertising = new MutableLiveData<>();
        advertising.setValue(false);
        discovering = new MutableLiveData<>();
        discovering.setValue(false);
        endpointsFound = new MutableLiveData<>();
        endpointsFound.setValue(new HashMap<String, DiscoveredEndpointInfo>());

    }

    private void startAdvertising() {
        connectionsClient.startAdvertising(
                roomAdmin,
                SERVICE_ID,
                mConnectionLifecycleCallback,
                new AdvertisingOptions.Builder().setStrategy(STRATEGY).build())
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unusedResult) {
                                // We're advertising!
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We were unable to start advertising.
                            }
                        });
    }

    private final EndpointDiscoveryCallback mEndpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(
                        @NonNull String endpointId, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                    // An endpoint was found!
                    //TODO
                    Log.i(getClass().getCanonicalName(), "found endpoint with ID: " + endpointId);
                    Map<String, DiscoveredEndpointInfo> endpoints = endpointsFound.getValue();
                    endpoints.put(endpointId, discoveredEndpointInfo);
                    endpointsFound.setValue(endpoints);
                }

                @Override
                public void onEndpointLost(@NonNull String endpointId) {
                    // A previously discovered endpoint has gone away.
                    //TODO
                    Log.i(getClass().getCanonicalName(), "lost endpoint with ID: " + endpointId);
                    Map<String, DiscoveredEndpointInfo> endpoints = endpointsFound.getValue();
                    endpoints.remove(endpointId);
                    endpointsFound.setValue(endpoints);
                }
            };

    private void startDiscovery() {
        connectionsClient.startDiscovery(
                SERVICE_ID,
                mEndpointDiscoveryCallback,
                new DiscoveryOptions.Builder().setStrategy(STRATEGY).build())
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unusedResult) {
                                // We're discovering!
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We were unable to start discovering.
                            }
                        });
    }


    private final ConnectionLifecycleCallback mConnectionLifecycleCallback =
            new ConnectionLifecycleCallback() {

                @Override
                public void onConnectionInitiated(
                        @NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
                    // Automatically accept the connection on both sides.
                    connectionsClient.acceptConnection(endpointId, mPayloadCallback);
                }

                @Override
                public void onConnectionResult(@NonNull String endpointId, ConnectionResolution result) {
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.
                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
                            break;
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    // We've been disconnected from this endpoint. No more data can be
                    // sent or received.
                }
            };



    private final PayloadCallback mPayloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(@NonNull String msg, @NonNull Payload payload) {
                    if (payload.getType() != Payload.Type.BYTES) {
                        throw new RuntimeException(getClass().getSimpleName() + " received load was not byte");
                    }
                    CommandMessage commandMessage = JsonUtil.getInstance().deSerializeMessage(msg);
                    inputMessageQueue.add(commandMessage);
                    //TODO
                }

                @Override
                public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {
                    //TODO
                }
            };

    private void sendPayload(String endpointId, Payload payload) {
        if (payload.getType() != Payload.Type.BYTES) {
            throw new RuntimeException(getClass().getSimpleName() + " received load was not Byte");
        }

    }

    public void sendMessage(CommandMessage msg) {
        String serialized = JsonUtil.getInstance().serialize(msg);
        //TODO sendMessage
    }




    @Override
    public void sendCommand(CommandMessage commandMessage) {

    }

    public LiveData<Boolean> getAdvertising() {
        return advertising;
    }

    public LiveData<Boolean> getDiscovering() {
        return discovering;
    }
}