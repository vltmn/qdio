package io.hamo.qdio.model.music;

import org.junit.Test;

import io.hamo.qdio.information.MusicObjectFactory;
import kaaes.spotify.webapi.android.models.Artist;

import static org.junit.Assert.*;

public class ArtistTest {

    @Test
    public void getName() {
        Artist fromApi = new Artist();
        fromApi.name = "TEST123";
        io.hamo.qdio.model.music.Artist converted = MusicObjectFactory.createArtist(fromApi);
        assertEquals(converted.getName(), fromApi.name);
    }

    @Test
    public void getURI() {
        Artist fromApi = new Artist();
        fromApi.uri = "TEST123";
        io.hamo.qdio.model.music.Artist converted = MusicObjectFactory.createArtist(fromApi);
        assertEquals(converted.getURI(), fromApi.uri);
    }
}