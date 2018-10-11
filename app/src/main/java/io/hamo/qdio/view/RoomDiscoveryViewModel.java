package io.hamo.qdio.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.List;

import io.hamo.qdio.room.RoomInstanceHolder;
import io.hamo.qdio.room.init.RoomDiscoveryService;

public class RoomDiscoveryViewModel extends ViewModel {

    private RoomDiscoveryService discoveryService;
    private MutableLiveData<List<String>> availableRooms;

    public RoomDiscoveryViewModel(Context context) {
        discoveryService = new RoomDiscoveryService(context);
        availableRooms = new MutableLiveData<>();

        discoveryService.startDiscovering();
        discoveryService.getEndpointsAvailable().observeForever( new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                availableRooms.setValue(strings);
            }
        });
    }

    public LiveData<List<String>> getAvailableRooms() {
        return availableRooms;
    }

    public void connectToRoom(String roomName) {
        RoomInstanceHolder.setRoomInstance(discoveryService.connectToRoom(roomName));
    }
}
