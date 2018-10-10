package io.hamo.qdio.model.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kaaes.spotify.webapi.android.models.ArtistSimple;

public class Album implements MusicObject {

    private final String URI;
    private final String name;
    private final String imageURL;
    private final List<String> artistURI;

    public Album(String URI, String name) {
        this.URI = URI;
        this.name = name;
        imageURL = null;
        artistURI = new ArrayList<>();
    }

    public Album(String URI, String name, String imageURL, List<String> artistURI) {
        this.URI = URI;
        this.name = name;
        this.imageURL = imageURL;
        this.artistURI = artistURI;
    }

    @Override
    public String getURI() {
        return URI;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public List<String> getArtistURI() {
        return artistURI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(URI, album.URI) &&
                Objects.equals(name, album.name) &&
                Objects.equals(imageURL, album.imageURL) &&
                Objects.equals(artistURI, album.artistURI);
    }

    @Override
    public int hashCode() {

        return Objects.hash(URI, name, imageURL, artistURI);
    }
}
