package io.hamo.qdio.room.init;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.SlaveCommunicator;
import io.hamo.qdio.room.Room;
import io.hamo.qdio.room.SlaveRoom;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 *
 *
 * Used to discover open rooms and connecting to these
 */
public class RoomDiscoveryService {
    private final MutableLiveData<Map<String, String>> endpointsAvailable = new MutableLiveData<>();
    private final Context context;

    public RoomDiscoveryService(Context context) {
        this.context = context.getApplicationContext();
        endpointsAvailable.setValue(new HashMap<String, String>());
    }

    /**
     * Start discovering rooms, they are exposed through the getEndpointsAvailable method
     */
    public void startDiscovering() {
        EndpointDiscoveryCallback endpointDiscoveryCallback = new EndpointDiscoveryCallback() {

            @Override
            public void onEndpointFound(@NonNull String endpointId, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                String endpointName = discoveredEndpointInfo.getEndpointName();
                Map<String, String> endpoints = endpointsAvailable.getValue();
                endpoints.put(endpointId, endpointName);
                endpointsAvailable.postValue(endpoints);
            }

            @Override
            public void onEndpointLost(@NonNull String endpointId) {
                Map<String, String> endpoints = endpointsAvailable.getValue();
                endpoints.remove(endpointId);
                endpointsAvailable.postValue(endpoints);
            }
        };
        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(NearbyUtil.STRATEGY).build();
        Nearby.getConnectionsClient(context)
                .startDiscovery(NearbyUtil.SERVICE_ID, endpointDiscoveryCallback, discoveryOptions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(getClass().getSimpleName(), "Started discovering!", null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(getClass().getSimpleName(), "Could not start discovering");
                    }
                });
    }

    /**
     * get the available rooms
     *
     * @return a livedata of a map consisting of the room name as key and the room ID as the value
     */
    public LiveData<Map<String, String>> getEndpointsAvailable() {
        return endpointsAvailable;
    }

    protected MutableLiveData<Queue<Payload>> connectToMaster(final String endpointId) {
        Map<String, String> endpoints = endpointsAvailable.getValue();
        if (!endpoints.containsKey(endpointId)) {
            throw new RuntimeException("Tried to connect to an endpoint that does not exist");
        }
        final MutableLiveData<Queue<Payload>> incomingPayloadQueue = new MutableLiveData<>();
        incomingPayloadQueue.setValue(new ArrayDeque<Payload>());
        final PayloadCallback payloadCallback = new RoomPayloadCallback(incomingPayloadQueue);

        ConnectionLifecycleCallback lifecycleCallback = new ConnectionLifecycleCallback() {
            @Override
            public void onConnectionInitiated(@NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
                Nearby.getConnectionsClient(context).acceptConnection(endpointId, payloadCallback);
            }

            @Override
            public void onConnectionResult(@NonNull String s, @NonNull ConnectionResolution connectionResolution) {
                switch (connectionResolution.getStatus().getStatusCode()) {
                    case ConnectionsStatusCodes.STATUS_OK:
                        Log.i(getClass().getSimpleName(), "Connected");
                        break;
                    default:
                        Log.e(getClass().getSimpleName(), "Failed to connect to " + endpointId + " connectionResolution was: " + connectionResolution.toString());
                }
            }

            @Override
            public void onDisconnected(@NonNull String s) {

            }
        };

        Nearby.getConnectionsClient(context)
                .requestConnection(getMyName(), endpointId, lifecycleCallback)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(getClass().getSimpleName(), "Successfully Connected to: " + endpointId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(getClass().getSimpleName(), "Failed to connect to endpoint: " + endpointId, e);

                    }
                });
        return incomingPayloadQueue;
    }

    /**
     * Connect to a specified room by id
     *
     * @param endpointId the id of the room to connect tio
     * @return a freshly instantiated slaveroom with a connection to the room with the id supplied
     */
    public Room connectToRoom(final String endpointId) {
        MutableLiveData<Queue<Payload>> queueMutableLiveData = connectToMaster(endpointId);
        ConnectionsClient connectionsClient = Nearby.getConnectionsClient(context);
        Communicator communicator = new SlaveCommunicator(endpointId, queueMutableLiveData, connectionsClient);
        return new SlaveRoom(communicator);
    }

    private String getMyName() {
        return "TEST";
    }


}
