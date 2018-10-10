package io.hamo.qdio.information.auth;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.hamo.qdio.BuildConfig;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class SpotifyAuthHelper {

    public String getAccessToken() {

        String url = "https://accounts.spotify.com/api/token";
        OkHttpClient client = new OkHttpClient();
        String encodedClientIdAndSecret = getEncodedCredentials();

        FormBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();
        Request rq = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Basic " + encodedClientIdAndSecret)
                .build();
        Call call = client.newCall(rq);
        String toReturn;
        try {
            toReturn = new AuthAsyncTask().execute(call).get();
            return toReturn;

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getEncodedCredentials() {
        String clientId = BuildConfig.spotifyClientId;
        String clientSecret = BuildConfig.spotifyClientSecret;
        return Base64.encodeToString((clientId + ":" + clientSecret).getBytes(), Base64.NO_WRAP);
    }

    private static class AuthAsyncTask extends AsyncTask<Call, Void, String> {

        @Override
        protected String doInBackground(Call... calls) {
            Call c = calls[0];
            try {
                Response response = c.execute();
                if(!response.isSuccessful()) throw new IOException(response.message());
                String json = response.body().string();
                if(json == null) throw new IOException(response.message());
                JsonParser parser = new JsonParser();
                JsonObject object = (JsonObject)parser.parse(json);
                JsonPrimitive access_token = (JsonPrimitive) object.get("access_token");
                return access_token.getAsString();
            } catch (IOException e) {
                Log.e("AuthAsyncTask", e.getLocalizedMessage());
            }
            return null;
        }
    }

}
