package io.hamo.qdio.view.fragments.queuelist;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import io.hamo.qdio.R;
import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.view.TrackListAdapter;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * Fragment for displaying the queuelist as a simple list of the songs queued
 */

public class QueueListFragment extends Fragment {

    private QueueListFragmentViewModel viewModel;
    private TrackListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new QueueListFragmentViewModel();
        viewModel.getQueueList().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> tracks) {
                adapter.setData(tracks);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_queue_list, container, false);
        RecyclerView queueList = (RecyclerView) v.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        queueList.setLayoutManager(linearLayoutManager);
        adapter = new TrackListAdapter(Collections.<Track>emptyList(), new TrackListAdapter.OnListItemClickedListener() {
            @Override
            public void onSearchResultItemClicked(Track t) {
                Log.d(getClass().getSimpleName(), t.getName());
            }
        });
        queueList.setAdapter(adapter);
        return v;
    }
}
