package io.hamo.qdio.view.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.hamo.qdio.MusicData.SpotifyMusicDataService;
import io.hamo.qdio.music.Track;


public class SearchFragmentViewModel extends ViewModel {
    public static final long SEARCH_DELAY = 500;

    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private Observer<String> searchQueryObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            try {
                //TODO use our tracks
                List<Track> tracks = SpotifyMusicDataService.getInstance().searchForTrack(s).call();
                List<String> toSet = new ArrayList<>();

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
