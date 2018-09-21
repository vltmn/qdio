package io.hamo.qdio.playback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import io.hamo.qdio.BuildConfig;


public class ConnectFragment extends Fragment {

    public static final String TAG = "custom_activity_state";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(BuildConfig.spotifyClientId)
                        .setRedirectUri("io.hamo.qdio://callback")
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.CONNECTOR.connect(getContext(), connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        SpotifyPlayer.getInstance().setSpotifyRemote(spotifyAppRemote);
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

}
