package io.hamo.qdio.view;

import android.app.ListActivity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.hamo.qdio.MainActivity;
import io.hamo.qdio.room.RoomInstanceHolder;
import io.hamo.qdio.room.init.RoomDiscoveryService;

public class RoomDiscoveryActivity extends ListActivity{

    private List<String> listItems = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    private RoomDiscoveryService discoveryService;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);
        discoveryService = new RoomDiscoveryService(this);
        discoveryService.startDiscovering();
        discoveryService.getEndpointsAvailable().observeForever(new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                listItems.clear();
                listItems.addAll(strings);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        RoomInstanceHolder.setRoomInstance(discoveryService.connectToRoom(listItems.get(position)));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
