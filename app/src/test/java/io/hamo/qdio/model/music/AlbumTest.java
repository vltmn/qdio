package io.hamo.qdio.model.music;

import org.junit.Test;

import java.util.ArrayList;

import io.hamo.qdio.testutil.MusicData;
import io.hamo.qdio.information.MusicObjectFactory;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Image;

import static org.junit.Assert.*;

public class AlbumTest {

    @Test
    public void getURI() {
        Album fromApi = MusicData.getInstance().getTestAlbum();
        fromApi.uri = "TEST123";
        io.hamo.qdio.model.music.Album converted = MusicObjectFactory.createAlbum(fromApi);
        assertEquals(converted.getURI(), fromApi.uri);
    }

    @Test
    public void getName() {
        Album fromApi = MusicData.getInstance().getTestAlbum();
        fromApi.name = "TEST123";
        io.hamo.qdio.model.music.Album converted = MusicObjectFactory.createAlbum(fromApi);
        assertEquals(converted.getName(), fromApi.name);
    }

    @Test
    public void getImgageURL() {
        Album fromApi = MusicData.getInstance().getTestAlbum();
        fromApi.images = new ArrayList<Image>();
        Image testImg = new Image();
        testImg.url = "TEST123";
        fromApi.images.add(testImg);
        io.hamo.qdio.model.music.Album converted = MusicObjectFactory.createAlbum(fromApi);
        assertEquals(converted.getImageURL(), fromApi.images.get(0).url);
    }

    @Test
    public void getArtistURI() {
        Album fromApi = MusicData.getInstance().getTestAlbum();
        io.hamo.qdio.model.music.Album converted = MusicObjectFactory.createAlbum(fromApi);
        assertTrue(converted.getArtistURI().contains(fromApi.artists.get(0).uri));
    }
}