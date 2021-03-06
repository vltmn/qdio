package io.hamo.qdio.view.fragments.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.hamo.qdio.information.MusicDataServiceFactory;
import io.hamo.qdio.model.music.Track;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * View model for the searchfragment, gets results when the search query has changed after 500ms
 */
public class SearchFragmentViewModel extends ViewModel {
    public static final long SEARCH_DELAY = 500;

    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private Observer<String> searchQueryObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            try {
                List<Track> tracks = MusicDataServiceFactory.getService().searchForTrack(s).call();
                trackList.setValue(tracks);
            } catch (Exception e) {
                Log.e(getClass().getCanonicalName(), e.getLocalizedMessage());
            }
        }
    };

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private MutableLiveData<List<Track>> trackList = new MutableLiveData<>();

    public SearchFragmentViewModel() {
        searchQuery.setValue("");
        isLoading.setValue(false);
        searchQuery.observeForever(searchQueryObserver);
        trackList.setValue(new ArrayList<Track>());
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public LiveData<Boolean> getIsLoading() {
        return this.isLoading;
    }

    public LiveData<List<Track>> getTrackList() {
        return trackList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        searchQuery.removeObserver(searchQueryObserver);
    }
}
