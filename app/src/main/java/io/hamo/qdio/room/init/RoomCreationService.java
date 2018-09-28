package io.hamo.qdio.room.init;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayDeque;
import java.util.Queue;

import io.hamo.qdio.room.Room;

public class RoomCreationService {
    private static final String ROOM_ID = "TEST";
    private final Context context;

    public RoomCreationService(Context context) {
        this.context = context.getApplicationContext();
    }

    private AdvertisingOptions getAdvertisingOptions() {
        return new AdvertisingOptions.Builder().setStrategy(NearbyUtil.STRATEGY).build();
    }

    public LiveData<Queue<byte[]>> createRoom(final String myId) {
        MutableLiveData<Room> toReturn = new MutableLiveData<>();

        MutableLiveData<Queue<Payload>> incomingPayloadQueue = new MutableLiveData<>();
        incomingPayloadQueue.setValue(new ArrayDeque<Payload>());
        final PayloadCallback payloadCallback = new RoomPayloadCallback(incomingPayloadQueue);
        ConnectionLifecycleCallback lifecycleCallback = new ConnectionLifecycleCallback() {
            @Override
            public void onConnectionInitiated(@NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
                Nearby.getConnectionsClient(context).acceptConnection(endpointId, payloadCallback);
            }

            @Override
            public void onConnectionResult(@NonNull String endpointId, @NonNull ConnectionResolution connectionResolution) {
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
            public void onDisconnected(@NonNull String endpointId) {

            }
        };
        Nearby.getConnectionsClient(context)
                .startAdvertising(myId, NearbyUtil.SERVICE_ID, lifecycleCallback, getAdvertisingOptions())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(getClass().getSimpleName(), "Successfully started advertising with id: " + myId);
                        //TODO instantiate room and set toreturn to the value
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(getClass().getSimpleName(), "Failed to start advertising", e);
                    }
                });
        return null;
    }
}
