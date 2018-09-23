package io.hamo.qdio.music;

import org.junit.Test;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

import static org.junit.Assert.*;

public class TrackTest {


    @Test
    public void getAlbumURI() {
        Album api = new Album();
        api.uri = "TEST";
        Track fromApi = new Track();
        fromApi.album = api;
        fromApi.album.uri = "TEST123";
        io.hamo.qdio.music.Track converted = new io.hamo.qdio.music.Track(fromApi);
        assertEquals(converted.getAlbumURI(), fromApi.album.uri);
    }

    @Test
    public void getArtistURI() {
        Track fromApi = new Track();
        fromApi.artists = new ArrayList<>();
        ArtistSimple a1 = new ArtistSimple();
        ArtistSimple a2 = new ArtistSimple();
        a1.uri = "TEST1";
        a2.uri = "TEST2";
        fromApi.artists.add(a1);
        fromApi.artists.add(a2);
        io.hamo.qdio.music.Track converted = new io.hamo.qdio.music.Track(fromApi);
        assertTrue(converted.getArtists().contains(new Artist(fromApi.artists.get(0))));
        assertTrue(converted.getArtists().contains(new Artist(fromApi.artists.get(1))));
    }

    @Test
    public void getDurationMs() {
        Track fromApi = new Track();
        fromApi.duration_ms=1000;
        io.hamo.qdio.music.Track converted = new io.hamo.qdio.music.Track(fromApi);
        assertEquals(converted.getDurationMs(), fromApi.duration_ms);

    }

    @Test
    public void isPlayable() {
        //Also checked with false and got the expected result
        Track fromApi = new Track();
        fromApi.is_playable=true;
        io.hamo.qdio.music.Track converted = new io.hamo.qdio.music.Track(fromApi);
        assertTrue(converted.isPlayable());
    }

    @Test
    public void getName() {
        Track fromApi = new Track();
        fromApi.name = "TEST123";
        io.hamo.qdio.music.Track converted = new io.hamo.qdio.music.Track(fromApi);
        assertEquals(converted.getName(), fromApi.name);

    }

    @Test
    public void getURI() {
        Track fromApi = new Track();
        fromApi.uri = "TEST123";
        io.hamo.qdio.music.Track converted = new io.hamo.qdio.music.Track(fromApi);
        assertEquals(converted.getURI(), fromApi.uri);

    }

    @Test
    public void getImageURL() {
        Album api = new Album();
        Image testImg = new Image();
        testImg.url = "TEST123";
        api.images = new ArrayList<Image>();
        api.images.add(testImg);

        Track fromApi = new Track();
        fromApi.album=api;
        fromApi.album.images=api.images;

        io.hamo.qdio.music.Track converted = new io.hamo.qdio.music.Track(fromApi);
        assertEquals(converted.getImageURL(), fromApi.album.images.get(0).url);

    }
}