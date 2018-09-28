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

import io.hamo.qdio.room.Room;

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
                List<String> endpoints = endpointsAvailable.getValue();
                endpoints.add(endpointId);
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


    public MutableLiveData<Queue<byte[]>> connectToHost(final String endpointId) {
        List<String> endpoints = endpointsAvailable.getValue();
        final MutableLiveData<Room> toReturn = new MutableLiveData<>();

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

        return null;
    }

    private String getMyName() {
        return "TEST";
    }


}
