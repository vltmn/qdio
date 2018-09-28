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
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import io.hamo.qdio.communication.Communicator;
import io.hamo.qdio.communication.SlaveCommunicator;
import io.hamo.qdio.room.Room;
import io.hamo.qdio.room.SlaveRoom;

public class RoomDiscoveryService {
    private final MutableLiveData<List<String>> endpointsAvailable = new MutableLiveData<>();
    private final Context context;

    public RoomDiscoveryService(Context context) {
        this.context = context.getApplicationContext();
        endpointsAvailable.setValue(new ArrayList<String>());
    }

    public void startDiscovering() {
        EndpointDiscoveryCallback endpointDiscoveryCallback = new EndpointDiscoveryCallback() {

            @Override
            public void onEndpointFound(@NonNull String endpointId, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                String endpointName = discoveredEndpointInfo.getEndpointName();
                List<String> endpoints = endpointsAvailable.getValue();
                endpoints.add(endpointName);
                endpointsAvailable.postValue(endpoints);
            }

            @Override
            public void onEndpointLost(@NonNull String endpointId) {
                List<String> endpoints = endpointsAvailable.getValue();
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

    public LiveData<List<String>> getEndpointsAvailable() {
        return endpointsAvailable;
    }

    protected MutableLiveData<Queue<Payload>> connectToMaster(final String endpointId) {
        List<String> endpoints = endpointsAvailable.getValue();
        if (!endpoints.contains(endpointId)) {
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
                //TODO handle connectionsresolution
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
                        //TODO instantiate room and set toreturn to the value
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
