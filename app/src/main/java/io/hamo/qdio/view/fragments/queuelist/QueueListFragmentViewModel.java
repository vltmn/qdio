package io.hamo.qdio.view.fragments.queuelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import java.util.List;

import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.room.RoomInstanceHolder;

public class QueueListFragmentViewModel extends ViewModel {
    private final MutableLiveData<List<Track>> queueList;
    private Handler handler = new Handler();
    private static final long UPDATE_DELAY = 50;

    public QueueListFragmentViewModel() {
        this.queueList = new MutableLiveData<>();
        handler.post(fetchUpdate);
    }

    private Runnable fetchUpdate = new Runnable() {
        @Override
        public void run() {
            queueList.postValue(RoomInstanceHolder.getRoomInstance().getQueueList());
            handler.postDelayed(fetchUpdate, UPDATE_DELAY);
        }
    };

    public LiveData<List<Track>> getQueueList() {
        return queueList;
    }
}
