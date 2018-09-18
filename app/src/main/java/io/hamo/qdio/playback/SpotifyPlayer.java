package io.hamo.qdio.playback;

import com.spotify.android.appremote.api.SpotifyAppRemote;

public class SpotifyPlayer implements Player {

    private SpotifyAppRemote spotifyAppRemote;
    private static SpotifyPlayer ourInstance;


    private SpotifyPlayer() {
    }

    public static SpotifyPlayer getInstance(){
        if(ourInstance == null) ourInstance = new SpotifyPlayer();
        return ourInstance;
    }

    protected void setSpotifyRemote(SpotifyAppRemote spotifyRemote) {
        this.spotifyAppRemote = spotifyRemote;
    }

    @Override
    public void resume() {
        spotifyAppRemote.getPlayerApi().resume();

    }

    @Override
    public void pause() {
        spotifyAppRemote.getPlayerApi().pause();
    }

    @Override
    public void seek(long ms) {
        spotifyAppRemote.getPlayerApi().seekTo(ms);
    }

}


