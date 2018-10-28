package io.hamo.qdio.information;

import io.hamo.qdio.information.auth.AccessTokenGenerator;
import kaaes.spotify.webapi.android.SpotifyApi;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 *
 *
 * Factory to get a musicdataservice
 */
public class MusicDataServiceFactory {
    private static MusicDataService serviceImpl;

    /**
     * Get an instance of musicdataservice
     *
     * @return the instance
     */
    public static MusicDataService getService() {
        if (serviceImpl == null) {
            SpotifyApi spotifyApi = new SpotifyApi();
            String accessToken = AccessTokenGenerator.getNewToken();
            spotifyApi.setAccessToken(accessToken);
            serviceImpl = new SpotifyMusicDataService(spotifyApi, accessToken);
        }
        return serviceImpl;
    }
}
