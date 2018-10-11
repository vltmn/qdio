package io.hamo.qdio.model.music;

import java.util.List;
import java.util.Objects;

public class Track implements MusicObject {

    private final String URI;
    private final String albumURI;
    private final List<Artist> artists;
    private final long durationMs;
    private final String name;
    private final String imageURL;

    public Track(String URI, String albumURI, List<Artist> artists, long durationMs, String name, String imageURL) {
        this.URI = URI;
        this.albumURI = albumURI;
        this.artists = artists;
        this.durationMs = durationMs;
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getAlbumURI() {
        return albumURI;
    }

    public long getDurationMs() {
        return durationMs;
    }


    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    @Override
    public String getURI() {
        return this.URI;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return durationMs == track.durationMs &&
                Objects.equals(URI, track.URI) &&
                Objects.equals(albumURI, track.albumURI) &&
                Objects.equals(artists, track.artists) &&
                Objects.equals(name, track.name) &&
                Objects.equals(imageURL, track.imageURL);
    }

    @Override
    public int hashCode() {

        return Objects.hash(URI, albumURI, artists, durationMs, name, imageURL);
    }
}

