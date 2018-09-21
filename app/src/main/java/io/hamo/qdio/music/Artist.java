package io.hamo.qdio.music;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(URI, artist.URI) &&
                Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(URI, name);
    }
}
