package io.hamo.qdio.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.Map;

import io.hamo.qdio.room.RoomInstanceHolder;
import io.hamo.qdio.room.init.RoomDiscoveryService;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * ViewModel for the RoomDiscoveryActivity, uses the RoomDiscoveryService to search for rooms and connect to them
 */
public class RoomDiscoveryViewModel extends ViewModel {

    private RoomDiscoveryService discoveryService;
    private MutableLiveData<Map<String, String>> availableRooms;

    public RoomDiscoveryViewModel(Context context) {
        discoveryService = new RoomDiscoveryService(context);
        availableRooms = new MutableLiveData<>();

        discoveryService.startDiscovering();
        discoveryService.getEndpointsAvailable().observeForever(new Observer<Map<String, String>>() {
            @Override
            public void onChanged(@Nullable Map<String, String> stringStringMap) {
                availableRooms.setValue(stringStringMap);
            }
        });
    }

    public LiveData<Map<String, String>> getAvailableRooms() {
        return availableRooms;
    }

    public void connectToRoom(String roomName) {
        String roomId = null;
        for (Map.Entry entry : availableRooms.getValue().entrySet()) {
            if (entry.getValue().equals(roomName)) {
                roomId = entry.getKey().toString();
            }
        }
        RoomInstanceHolder.setRoomInstance(discoveryService.connectToRoom(roomId));
    }
}
