package io.hamo.qdio.view.search;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.hamo.qdio.R;
import io.hamo.qdio.music.Track;


public class SearchFragment extends Fragment {
    private SearchFragmentViewModel viewModel;
    private SearchFragmentListAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new SearchFragmentViewModel();
        //add observers
        viewModel.getTrackList().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> tracks) {
                if (adapter == null) return;
                viewModel.setIsLoading(false);
                adapter.setData(tracks);
            }

        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading == null) return;
                recyclerView.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //get view objects
        SearchView searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.searchList);
        progressBar = view.findViewById(R.id.progressBar);

        searchView.setOnQueryTextListener(new SearchOnQueryTextListener(viewModel));

        //set recyclerview fixedsize for performance
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //construct adapter with empty list
        adapter = new SearchFragmentListAdapter(new ArrayList<Track>());
        recyclerView.setAdapter(adapter);

        return view;
    }


    private static class SearchOnQueryTextListener implements SearchView.OnQueryTextListener {
        private final long delayMs = SearchFragmentViewModel.SEARCH_DELAY;
        private final Handler handler = new Handler();
        private final SearchFragmentViewModel viewModel;
        private Runnable currentRunnable = null;


        public SearchOnQueryTextListener(SearchFragmentViewModel searchFragmentViewModel) {
            this.viewModel = searchFragmentViewModel;
        }

        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(final String s) {
            if (currentRunnable != null) {
                //remove the previous runnable if not done
                handler.removeCallbacks(currentRunnable);
                currentRunnable = null;
            }
            viewModel.setIsLoading(true);
            currentRunnable = new Runnable() {
                @Override
                public void run() {
                    viewModel.setSearchQuery(s);
                }
            };
            handler.postDelayed(currentRunnable, delayMs);
            return false;
        }
    }

}
