package io.hamo.qdio.MusicData;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.hamo.qdio.music.Album;
import io.hamo.qdio.music.Artist;
import io.hamo.qdio.music.Track;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

import kaaes.spotify.webapi.android.models.TracksPager;

public class SpotifyMusicDataService implements MusicDataService {
    private static SpotifyMusicDataService ourInstance;
    private SpotifyService spotifyService;
    private final SpotifyApi spotifyApi;
    private String accessToken;


    public static SpotifyMusicDataService getInstance() {
        if (ourInstance == null) {
            ourInstance = new SpotifyMusicDataService();
        }
        return ourInstance;
    }

    private SpotifyMusicDataService() {
        this.accessToken = SpotifyAuthHelper.getInstance().getAccessToken();
        spotifyApi = new SpotifyApi();
        spotifyApi.setAccessToken(accessToken);
        spotifyService = spotifyApi.getService();
    }

    private void generateNewAccessToken() {
        this.accessToken = SpotifyAuthHelper.getInstance().getAccessToken();
        spotifyApi.setAccessToken(accessToken);
        spotifyService = spotifyApi.getService();
    }

    public Callable<List<Track>> searchForTrack(final String query) {
        final AsyncTask<String, Void, List<Track>> asyncTask = new SearchTracksAsyncTask(spotifyService);
        return new Callable<List<Track>>() {
            @Override
            public List<Track> call() throws Exception {
                return asyncTask.execute(query).get();

            }
        };
    }

    private static class SearchTracksAsyncTask extends AsyncTask<String, Void, List<Track>> {
        private final SpotifyService spotifyService;

        SearchTracksAsyncTask(SpotifyService spotifyService) {
            this.spotifyService = spotifyService;
        }

        @Override
        protected List<Track> doInBackground(String... strings) {
            String query = strings[0];
            if ("".equals(query)) return Collections.emptyList();
            TracksPager tracksPager = spotifyService.searchTracks(query);
            List<Track> tracks = new ArrayList<>();
            for (kaaes.spotify.webapi.android.models.Track t : tracksPager.tracks.items) {
                tracks.add(new Track(t));
            }
            return tracks;
        }
    }

    @Override
    public Callable<Map<String, Artist>> getArtistsFromUris(List<String> artists) {
        final Map<String, AsyncTask<String, Void, Artist>> taskMap = new HashMap<>();
        for (String uri : artists) {
            taskMap.put(uri, new GetArtistAsyncTask(spotifyService));
        }
        return new Callable<Map<String, Artist>>() {
            @Override
            public Map<String, Artist> call() throws Exception {
                Map<String, Artist> toReturn = new HashMap<>();
                for (Map.Entry<String, AsyncTask<String, Void, Artist>> taskEntry : taskMap.entrySet()) {
                    taskEntry.getValue().execute(taskEntry.getKey());
                }

                for (Map.Entry<String, AsyncTask<String, Void, Artist>> taskEntry : taskMap.entrySet()) {
                    toReturn.put(taskEntry.getKey(), taskEntry.getValue().get());
                }
                return toReturn;
            }
        };
    }

    private static class GetArtistAsyncTask extends AsyncTask<String, Void, Artist> {
        private SpotifyService spotifyService;

        GetArtistAsyncTask(SpotifyService spotifyService) {
            this.spotifyService = spotifyService;
        }

        @Override
        protected Artist doInBackground(String... strings) {
            String toGet = strings[0];
            kaaes.spotify.webapi.android.models.Artist artist = spotifyService.getArtist(toGet);
            return new Artist(artist);
        }
    }

    @Override
    public Callable<Artist> getArtist(final String artistUri) {
        final Callable<Map<String, Artist>> artistsFromUris = getArtistsFromUris(Collections.singletonList(artistUri));
        return new Callable<Artist>() {
            @Override
            public Artist call() throws Exception {
                return artistsFromUris.call().get(artistUri);
            }
        };
    }

    @Override
    public Callable<Album> getAlbumFromUri(final String albumUri) {
        final GetAlbumAsyncTask asyncTask = new GetAlbumAsyncTask(spotifyService);
        return new Callable<Album>() {
            @Override
            public Album call() throws Exception {
                return asyncTask.execute(albumUri).get();
            }
        };
    }

    private static class GetAlbumAsyncTask extends AsyncTask<String, Void, Album> {
        private final SpotifyService spotifyService;

        GetAlbumAsyncTask(SpotifyService spotifyService) {
            this.spotifyService = spotifyService;
        }

        @Override
        protected Album doInBackground(String... strings) {
            String albumUri = strings[0];
            kaaes.spotify.webapi.android.models.Album album = spotifyService.getAlbum(albumUri);
            return new Album(album);
        }
    }


}
