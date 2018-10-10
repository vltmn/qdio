package io.hamo.qdio.model.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kaaes.spotify.webapi.android.models.ArtistSimple;

public class Track implements MusicObject {

    private final String URI;
    private final String albumURI;
    private final List<Artist> artists = new ArrayList<>();
    private final long durationMs;
    private final String name;
    private final String imageURL;

    public Track(com.spotify.protocol.types.Track track) {
        this.URI = track.uri;
        if (track.album != null) {
            this.albumURI = track.album.uri;
        } else {
            this.albumURI = null;
        }
        if (track.artists != null) {
            for (com.spotify.protocol.types.Artist a : track.artists) {
                this.artists.add(new Artist(a));
            }
        }
        this.durationMs = track.duration;
        this.name = track.name;
        this.imageURL = track.imageUri.raw;

    }


    public Track(kaaes.spotify.webapi.android.models.Track track) {
        this.URI = track.uri;
        if (track.album != null) {
            this.albumURI = track.album.uri;
        } else {
            this.albumURI = null;
        }
        if (track.artists != null) {
            for (ArtistSimple a : track.artists) {
                this.artists.add(new Artist(a));
            }
        }
        this.durationMs = track.duration_ms;
        this.name = track.name;

        this.imageURL = track.album != null && track.album.images != null && track.album.images.size() > 0 ?
                track.album.images.get(0).url : null;
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

