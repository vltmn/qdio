package io.hamo.qdio.information.auth;

/**
 * Generator to get a new spotify access token without having to think about the SpotifyAuthHelper
 */
public class AccessTokenGenerator {
    private static SpotifyAuthHelper authHelper;

    /**
     * Get a new access token for the API
     * @return the access token
     */
    public static String getNewToken() {
        if(authHelper == null) authHelper = new SpotifyAuthHelper();
        return authHelper.getAccessToken();
    }
}
