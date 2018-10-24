package io.hamo.qdio.playback;

import android.util.Log;

import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;

import io.hamo.qdio.information.MusicObjectFactory;
import io.hamo.qdio.model.music.MusicObject;
import io.hamo.qdio.model.music.Track;

/**
 * Handles functionality for the music playing on the device
 */

class SpotifyPlayer implements Player {

    private SpotifyAppRemote spotifyAppRemote;
    private Track currentTrack;
    private PlayerState playerState;
    private Timer t = new Timer();
    private long latestPosition;
    private OnSongEndCallback onSongEndCallback;

    SpotifyPlayer(SpotifyAppRemote spotifyAppRemote) {
        this.spotifyAppRemote = spotifyAppRemote;
        spotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<com.spotify.protocol.types.PlayerState>() {
            @Override
            public void onEvent(com.spotify.protocol.types.PlayerState state) {
                Log.w(getClass().getSimpleName(), state.toString(), null);
                updateWithNewState(state);

            }
        });
    }

    private void updateWithNewState(com.spotify.protocol.types.PlayerState state) {
        //update position
        latestPosition = state.playbackPosition;
        t.resetTimer();

        //update current track
        currentTrack = MusicObjectFactory.createTrack(state.track);
        playerState = state.isPaused ? PlayerState.PAUSED : PlayerState.PLAYING;

        if(playerState == PlayerState.PAUSED && latestPosition == 0) {
            handleSongEnd();
        }

    }


    private void handleSongEnd() {
        if (onSongEndCallback == null) {
            return;
        }
        Track track = onSongEndCallback.onSongEnd();
        if (track == null) {

            return;
        }
        play(track);
    }

    @Override
    public void resume() {
        spotifyAppRemote.getPlayerApi().resume();
        playerState = PlayerState.PLAYING;
    }

    @Override
    public void pause() {
        spotifyAppRemote.getPlayerApi().pause();
        playerState = PlayerState.PAUSED;
    }


    @Override
    public void seek(long ms) {
        spotifyAppRemote.getPlayerApi().seekTo(ms);
    }


    @Override
    public void play(final MusicObject obj) {
        spotifyAppRemote.getPlayerApi().play(obj.getURI());

        playerState = PlayerState.PLAYING;
    }


    @Override
    public Track getCurrentTrack() {
        return currentTrack;
    }


    @Override
    public Long getCurrentPosition() {
        return playerState == PlayerState.PAUSED ? latestPosition : latestPosition +  t.getTime();
    }


    @Override
    public PlayerState getPlayerState() {
        return playerState;
    }


    @Override
    public void setOnSongEndCallback(OnSongEndCallback onSongEndCallback) {
        this.onSongEndCallback = onSongEndCallback;

    }


    private static class Timer {
        private long startTime = 0;

        Timer() {
            resetTimer();
        }

        public long getTime() {
            return System.currentTimeMillis() - startTime;
        }

        public void resetTimer() {
            startTime = System.currentTimeMillis();
        }
    }
}


