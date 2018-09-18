package io.hamo.qdio.music;

import kaaes.spotify.webapi.android.models.ArtistSimple;

public class Artist implements MusicObject {

    private String URI;
    private String name;


    public Artist(ArtistSimple artist){
        this.URI=artist.uri;
        this.name=artist.name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getURI() {
        return URI;
    }
}
