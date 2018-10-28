package io.hamo.qdio.view.fragments.search;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.hamo.qdio.R;
import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.view.TrackListAdapter;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 *
 *
 * Fragment for showing the search view, will display a search bar with auto updating results when changeing the search query
 * expects the parent activity to implement the TrackListAdapter.OnListItemClickedListener interface
 */
public class SearchFragment extends Fragment {
    private SearchFragmentViewModel viewModel;
    private TrackListAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TrackListAdapter.OnListItemClickedListener onResultItemListener;

    public SearchFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onResultItemListener = (TrackListAdapter.OnListItemClickedListener) context;
        } catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + " must implement " + TrackListAdapter.OnListItemClickedListener.class.getSimpleName());
        }
    }

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //construct adapter with empty list
        adapter = new TrackListAdapter(new ArrayList<Track>(), onResultItemListener);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration did = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(did);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_DRAGGING) return;
                View view = getView();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

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
