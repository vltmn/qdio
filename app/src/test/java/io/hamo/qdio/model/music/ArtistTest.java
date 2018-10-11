package io.hamo.qdio.model.music;

import org.junit.Test;

import io.hamo.qdio.TestUtil.MusicData;
import io.hamo.qdio.information.MusicObjectFactory;
import kaaes.spotify.webapi.android.models.Artist;

import static org.junit.Assert.*;

public class ArtistTest {

    @Test
    public void getName() {
        Artist fromApi = MusicData.getInstance().getTestArtist();
        fromApi.name = "TEST123";
        io.hamo.qdio.model.music.Artist converted = MusicObjectFactory.createArtist(fromApi);
        assertEquals(converted.getName(), fromApi.name);
    }

    @Test
    public void getURI() {
        Artist fromApi = MusicData.getInstance().getTestArtist();
        fromApi.uri = "TEST123";
        io.hamo.qdio.model.music.Artist converted = MusicObjectFactory.createArtist(fromApi);
        assertEquals(converted.getURI(), fromApi.uri);
    }
}