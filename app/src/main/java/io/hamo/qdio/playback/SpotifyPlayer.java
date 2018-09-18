package io.hamo.qdio.playback;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class SpotifyPlayer implements Player {

    private SpotifyAppRemote mSpotifyAppRemote;
    private long ms;

    public SpotifyPlayer (SpotifyAppRemote mSpotifyAppRemote, long ms){
        this.mSpotifyAppRemote = mSpotifyAppRemote;
        this.ms = ms;
    }


    @Override
    public void resume() {
        mSpotifyAppRemote.getPlayerApi().resume();

    }

    @Override
    public void pause() {
        mSpotifyAppRemote.getPlayerApi().pause();
    }

    @Override
    public void seek(long ms) {
        mSpotifyAppRemote.getPlayerApi().seekTo(ms);
    }
}
