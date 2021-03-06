package io.hamo.qdio.model.music;

import org.junit.Test;

import java.util.ArrayList;

import io.hamo.qdio.testutil.MusicData;
import io.hamo.qdio.information.MusicObjectFactory;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

import static org.junit.Assert.*;

public class TrackTest {


    @Test
    public void getAlbumURI() {
        Track fromApi = MusicData.getInstance().getTestTrack();
        io.hamo.qdio.model.music.Track converted = MusicObjectFactory.createTrack(fromApi);
        assertEquals(converted.getAlbumURI(), fromApi.album.uri);
    }

    @Test
    public void getArtistURI() {
        Track fromApi = MusicData.getInstance().getTestTrack();
        io.hamo.qdio.model.music.Track converted = MusicObjectFactory.createTrack(fromApi);
        assertTrue(converted.getArtists().contains(MusicObjectFactory.createArtist(fromApi.artists.get(0))));
    }

    @Test
    public void getDurationMs() {
        Track fromApi = MusicData.getInstance().getTestTrack();
        fromApi.duration_ms=1000;
        io.hamo.qdio.model.music.Track converted = MusicObjectFactory.createTrack(fromApi);
        assertEquals(converted.getDurationMs(), fromApi.duration_ms);

    }

    @Test
    public void getName() {
        Track fromApi = MusicData.getInstance().getTestTrack();
        fromApi.name = "TEST123";
        io.hamo.qdio.model.music.Track converted = MusicObjectFactory.createTrack(fromApi);
        assertEquals(converted.getName(), fromApi.name);

    }

    @Test
    public void getURI() {
        Track fromApi = MusicData.getInstance().getTestTrack();
        fromApi.uri = "TEST123";
        io.hamo.qdio.model.music.Track converted = MusicObjectFactory.createTrack(fromApi);
        assertEquals(converted.getURI(), fromApi.uri);

    }

    @Test
    public void getImageURL() {
        Album api = MusicData.getInstance().getTestAlbum();
        Image testImg = new Image();
        testImg.url = "TEST123";
        api.images = new ArrayList<Image>();
        api.images.add(testImg);

        Track fromApi = MusicData.getInstance().getTestTrack();
        fromApi.album=api;
        fromApi.album.images=api.images;

        io.hamo.qdio.model.music.Track converted = MusicObjectFactory.createTrack(fromApi);
        assertEquals(converted.getImageURL(), fromApi.album.images.get(0).url);

    }
}