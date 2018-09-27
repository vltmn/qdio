package io.hamo.qdio.playback;

import android.util.Log;

import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;

import io.hamo.qdio.music.MusicObject;
import io.hamo.qdio.music.Track;

class SpotifyPlayer implements Player {

    private SpotifyAppRemote spotifyAppRemote;
    private Track currentTrack;
    private PlayerState playerState;
    private Timer t = new Timer();
    private long latestPosition;

    SpotifyPlayer(SpotifyAppRemote spotifyAppRemote) {
        this.spotifyAppRemote = spotifyAppRemote;
        spotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<com.spotify.protocol.types.PlayerState>() {
            @Override
            public void onEvent(com.spotify.protocol.types.PlayerState playerState) {
                Log.w(getClass().getSimpleName(), playerState.toString(), null);
                latestPosition = playerState.playbackPosition;
                t.resetTimer();
                currentTrack = new Track(playerState.track);
            }
        });
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
    public void play(MusicObject obj) {
        spotifyAppRemote.getPlayerApi().play(obj.getURI());
        playerState = PlayerState.PLAYING;
    }


    @Override
    public Track getCurrentTrack() {
        return currentTrack;
    }


    @Override
    public Long getCurrentPosition() {
        return latestPosition + t.getTime();
    }


    @Override
    public PlayerState getPlayerState() {
        return playerState;
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


