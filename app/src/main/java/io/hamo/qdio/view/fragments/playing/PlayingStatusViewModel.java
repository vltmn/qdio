package io.hamo.qdio.view.fragments.playing;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;


import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.playback.PlayerFactory;
import io.hamo.qdio.playback.PlayerState;
import io.hamo.qdio.room.RoomInstanceHolder;
import io.hamo.qdio.model.room.RoomType;
import io.hamo.qdio.information.ImageFetchTask;

/**
 * A viewmodel in the model-view-viewmodel pattern. Uses a thread to check every STATUS_POLL_INTERVAL
 * ( 500 ms ) for playingstatus values (instance varibles) and updates values to pass to the
 * playingfragment.
 */

public class PlayingStatusViewModel extends ViewModel {

    private static final int STATUS_POLL_INTERVAL = 500;
    private final MutableLiveData<Track> currentlyPlayingTrack;
    private final MutableLiveData<Long> currentPosition;
    private final MutableLiveData<Bitmap> currentAlbumImage;
    private final MutableLiveData<Boolean> isPlaying;


    private Handler handler = new Handler();

    public PlayingStatusViewModel() {
        currentlyPlayingTrack = new MutableLiveData<>();
        currentPosition = new MutableLiveData<>();
        currentAlbumImage = new MutableLiveData<>();
        isPlaying = new MutableLiveData<>();


        handler.postDelayed(runnableCode, 10000);
    }

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            updateThread();
            handler.postDelayed(this, STATUS_POLL_INTERVAL);
        }
    };

    /**
     * Sets values that will be represented in playingstatus. Differentiates between master
     * and slave room through type on roominstanceholder. Used in runnable every 500ms
     */

    public void updateThread(){
        Track track = RoomInstanceHolder.getRoomInstance().getCurrentSong();
        //values only used for master room
        if (RoomInstanceHolder.getRoomInstance().getType().equals(RoomType.MASTER)) {
            Long currentPos = PlayerFactory.getPlayer().getCurrentPosition();
            currentPosition.postValue(currentPos);
            isPlaying.postValue(PlayerFactory.getPlayer().getPlayerState() == PlayerState.PLAYING);
        }
        if (track == null){
            return;
        }
        if (track.equals(currentlyPlayingTrack.getValue())){
            return;
        }
        currentlyPlayingTrack.postValue(track);
        try {
            ImageFetchTask getImageFromUriTask = new ImageFetchTask();
            String albumUri = track.getImageURL();
            Bitmap b = getImageFromUriTask.execute(albumUri).get();
            currentAlbumImage.postValue(b);

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Could not get bitmap from image", e);
        }
    }

    public LiveData<Bitmap> getCurrentAlbumImage() {
        return currentAlbumImage;
    }

    public LiveData<Track> getCurrentlyPlayingTrack() {
        return currentlyPlayingTrack;
    }

    public LiveData<Long> getCurrentPosition() {
        return currentPosition;
    }

    public MutableLiveData<Boolean> getIsPlaying(){
        return isPlaying;
    }

    public void onSeekbarDrag(int ms){
        PlayerFactory.getPlayer().seek(ms);
    }

    public void playBtnClicked(){
        if (isPlaying.getValue()){
            PlayerFactory.getPlayer().pause();
        }
        else {
            PlayerFactory.getPlayer().resume();
        }
    }

    public boolean isMasterRoom(){
        return RoomInstanceHolder.getRoomInstance().getType().equals(RoomType.MASTER);
    }

}
