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
import java.util.Map;


public class RoomDiscoveryActivity extends ListActivity{

    private RoomDiscoveryViewModel viewModel;
    private List<String> listItems = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        viewModel = new RoomDiscoveryViewModel(this);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);
        viewModel.getAvailableRooms().observeForever(new Observer<Map<String, String>>() {
            @Override
            public void onChanged(@Nullable Map<String, String> stringStringMap) {
                listItems.clear();
                listItems.addAll(stringStringMap.values());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        viewModel.connectToRoom(listItems.get(position));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
