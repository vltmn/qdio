package io.hamo.qdio.information.auth;

public class AccessTokenGenerator {
    private static SpotifyAuthHelper authHelper;

    public static String getNewToken() {
        if(authHelper == null) authHelper = new SpotifyAuthHelper();
        return authHelper.getAccessToken();
    }
}
