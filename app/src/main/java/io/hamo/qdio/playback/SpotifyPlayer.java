package io.hamo.qdio.playback;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import io.hamo.qdio.music.MusicObject;

class SpotifyPlayer implements Player {

    private SpotifyAppRemote spotifyAppRemote;


    SpotifyPlayer(SpotifyAppRemote spotifyAppRemote) {
        this.spotifyAppRemote = spotifyAppRemote;
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

    @Override
    public void play(MusicObject obj) {
        spotifyAppRemote.getPlayerApi().play(obj.getURI());
    }

}


