package io.hamo.qdio.model.music;

import org.junit.Test;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;

import static org.junit.Assert.*;

public class AlbumTest {

    @Test
    public void getURI() {
        Album fromApi = new Album();
        fromApi.uri = "TEST123";
        io.hamo.qdio.model.music.Album converted = new io.hamo.qdio.model.music.Album(fromApi);
        assertEquals(converted.getURI(), fromApi.uri);
    }

    @Test
    public void getName() {
        Album fromApi = new Album();
        fromApi.name = "TEST123";
        io.hamo.qdio.model.music.Album converted = new io.hamo.qdio.model.music.Album(fromApi);
        assertEquals(converted.getName(), fromApi.name);
    }

    @Test
    public void getImgageURL() {
        Album fromApi = new Album();
        fromApi.images = new ArrayList<Image>();
        Image testImg = new Image();
        testImg.url = "TEST123";
        fromApi.images.add(testImg);
        io.hamo.qdio.model.music.Album converted = new io.hamo.qdio.model.music.Album(fromApi);
        assertEquals(converted.getImageURL(), fromApi.images.get(0).url);
    }

    @Test
    public void getArtistURI() {
        Album fromApi = new Album();
        fromApi.artists = new ArrayList<>();
        ArtistSimple a1 = new ArtistSimple();
        ArtistSimple a2 = new ArtistSimple();
        a1.uri = "TEST1";
        a2.uri = "TEST2";
        fromApi.artists.add(a1);
        fromApi.artists.add(a2);
        io.hamo.qdio.model.music.Album converted = new io.hamo.qdio.model.music.Album(fromApi);
        assertTrue(converted.getArtistURI().contains(fromApi.artists.get(0).uri));
        assertTrue(converted.getArtistURI().contains(fromApi.artists.get(1).uri));
    }
}