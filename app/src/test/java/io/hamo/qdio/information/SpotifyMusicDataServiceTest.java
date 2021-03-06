package io.hamo.qdio.information;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.hamo.qdio.testutil.MusicData;
import io.hamo.qdio.model.music.Artist;
import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.model.music.Album;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.TracksPager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpotifyMusicDataServiceTest {

    SpotifyMusicDataService spotifyMusicDataService;
    @Mock
    SpotifyService spotifyService;
    @Mock
    SpotifyApi spotifyApi;

    @Before
    public void setUp() throws Exception {
        when(spotifyApi.getService()).thenReturn(spotifyService);
        spotifyMusicDataService = new SpotifyMusicDataService(spotifyApi, "");
    }

    @Test
    public void searchForTrack() throws Exception {

        kaaes.spotify.webapi.android.models.Track tApi = MusicData.getInstance().getTestTrack();
        Track t = MusicObjectFactory.createTrack(tApi);
        String query = t.getName();
        assertEquals(query, tApi.name);

        TracksPager tracksPager = new TracksPager();
        tracksPager.tracks = new Pager<>();
        tracksPager.tracks.items = Collections.singletonList(tApi);
        when(spotifyService.searchTracks(query)).thenReturn(tracksPager);

        List<Track> call = spotifyMusicDataService.searchForTrack(query).call();
        assertEquals(t, call.get(0));
    }

    @Test
    public void getArtistsFromUris() throws Exception {
        kaaes.spotify.webapi.android.models.Artist aApi = MusicData.getInstance().getTestArtist();
        kaaes.spotify.webapi.android.models.Artist bApi = MusicData.getInstance().getTestArtist();
        Artist a = MusicObjectFactory.createArtist(aApi);
        Artist b = MusicObjectFactory.createArtist(bApi);
        String aURI = a.getURI();
        String bURI = b.getURI();
        when(spotifyService.getArtist(aURI)).thenReturn(aApi);
        when(spotifyService.getArtist(bURI)).thenReturn(bApi);

        Map<String, Artist> gotFromApi = spotifyMusicDataService.getArtistsFromUris(Arrays.asList(aURI, bURI)).call();
        assertTrue(gotFromApi.containsKey(aURI));
        assertTrue(gotFromApi.containsKey(bURI));
        Artist aFromApi = gotFromApi.get(aURI);
        Artist bFromApi = gotFromApi.get(bURI);

        assertEquals(aFromApi, a);
        assertEquals(bFromApi, b);
        assertNotEquals(aFromApi, b);
        assertNotEquals(bFromApi, a);

    }

    @Test
    public void getArtist() throws Exception {
        kaaes.spotify.webapi.android.models.Artist apiArtist = MusicData.getInstance().getTestArtist();
        Artist a = MusicObjectFactory.createArtist(apiArtist);
        String aURI = a.getURI();
        when(spotifyService.getArtist(aURI)).thenReturn(apiArtist);
        Artist fromService = spotifyMusicDataService.getArtist(aURI).call();

        assertEquals(a, fromService);
    }

    @Test
    public void getAlbumFromUri() throws Exception {
        kaaes.spotify.webapi.android.models.Album apiAlbum = MusicData.getInstance().getTestAlbum();
        Album a = MusicObjectFactory.createAlbum(apiAlbum);
        String aUri = a.getURI();
        when(spotifyService.getAlbum(aUri)).thenReturn(apiAlbum);
        Album fromService = spotifyMusicDataService.getAlbumFromUri(aUri).call();

        assertEquals(a, fromService);
    }

    @Test
    public void getTrackFromUri() throws Exception {
        kaaes.spotify.webapi.android.models.Track apiTrack = MusicData.getInstance().getTestTrack();
        Track t = MusicObjectFactory.createTrack(apiTrack);
        String tURI = t.getURI();
        when(spotifyService.getTrack(tURI)).thenReturn(apiTrack);
        Track fromService = spotifyMusicDataService.getTrackFromUri(tURI).call();

        assertEquals(t, fromService);
    }

    @Test
    public void getTracksFromUris() throws Exception {
        kaaes.spotify.webapi.android.models.Track aApi = MusicData.getInstance().getTestTrack();
        kaaes.spotify.webapi.android.models.Track bApi = MusicData.getInstance().getTestTrack();
        Track a = MusicObjectFactory.createTrack(aApi);
        Track b = MusicObjectFactory.createTrack(bApi);
        String aURI = a.getURI();
        String bURI = b.getURI();
        when(spotifyService.getTrack(aURI)).thenReturn(aApi);
        when(spotifyService.getTrack(bURI)).thenReturn(bApi);

        Map<String, Track> gotFromApi = spotifyMusicDataService.getTracksFromUris(Arrays.asList(aURI, bURI)).call();
        assertTrue(gotFromApi.containsKey(aURI));
        assertTrue(gotFromApi.containsKey(bURI));

        Track aFromApi = gotFromApi.get(aURI);
        Track bFromApi = gotFromApi.get(bURI);
        assertEquals(aFromApi, a);
        assertEquals(bFromApi, b);
        assertNotEquals(aFromApi, b);
        assertNotEquals(bFromApi, a);
    }
}