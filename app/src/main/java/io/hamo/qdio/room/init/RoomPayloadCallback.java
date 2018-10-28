package io.hamo.qdio.room.init;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;

import java.util.Queue;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * Payload callback tghat puts the transferred payloads in a LiveData queue and notifys the observers
 */
public class RoomPayloadCallback extends PayloadCallback {
    private final MutableLiveData<Queue<Payload>> incomingQueue;

    public RoomPayloadCallback(MutableLiveData<Queue<Payload>> incomingQueue) {
        this.incomingQueue = incomingQueue;
    }

    @Override
    public void onPayloadReceived(@NonNull String s, @NonNull Payload payload) {
        Log.i(getClass().getSimpleName(), "Payload received: " + payload.toString());
        Queue<Payload> value = incomingQueue.getValue();
        value.add(payload);
        incomingQueue.postValue(value);
    }

    @Override
    public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {
        Log.v(getClass().getSimpleName(), "Transferred " + payloadTransferUpdate.getBytesTransferred() + " / " + payloadTransferUpdate.getTotalBytes());
    }
}
