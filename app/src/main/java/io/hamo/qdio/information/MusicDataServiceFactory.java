package io.hamo.qdio.information;

import io.hamo.qdio.information.auth.AccessTokenGenerator;
import kaaes.spotify.webapi.android.SpotifyApi;

public class MusicDataServiceFactory {
    private static MusicDataService serviceImpl;

    public static MusicDataService getService() {
        if(serviceImpl == null) {
            SpotifyApi spotifyApi = new SpotifyApi();
            String accessToken = AccessTokenGenerator.getNewToken();
            spotifyApi.setAccessToken(accessToken);
            serviceImpl = new SpotifyMusicDataService(spotifyApi, accessToken);
        }
        return serviceImpl;
    }
}
