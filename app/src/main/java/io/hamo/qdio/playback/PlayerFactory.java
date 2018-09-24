package io.hamo.qdio.playback;

import android.util.Log;

import com.spotify.android.appremote.api.SpotifyAppRemote;

public class PlayerFactory {
    private static SpotifyPlayer spotifyPlayer;
    public static void instantiatePlayer(SpotifyAppRemote spotifyAppRemote) {
        spotifyPlayer = new SpotifyPlayer(spotifyAppRemote);
    }

    public static Player getPlayer() {
        if(spotifyPlayer == null) {
            String message = "Spotify Player has not yet been instantiated, use the connect fragment to instantiate it";
            Log.e(PlayerFactory.class.getSimpleName(), message);
            throw new RuntimeException(message);
        }
        return spotifyPlayer;
    }
}
