package io.hamo.qdio.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kaaes.spotify.webapi.android.models.ArtistSimple;

public class Track implements MusicObject {

    private String URI;
    private String albumURI;
    private List<String> artistURI = new ArrayList<>();
    private long durationMs;
    private boolean isPlayable;
    private String name;
    private String imageURL;


    public Track(String URI) {
        this.URI = URI;
    }

    public Track(kaaes.spotify.webapi.android.models.Track track) {
        this.URI = track.uri;
        if (track.album != null) {
            this.albumURI = track.album.uri;
        }
        if (track.artists != null) {
            for (ArtistSimple a : track.artists) {
                this.artistURI.add(a.uri);
            }
        }
        this.durationMs = track.duration_ms;
        if (track.is_playable != null) {
            this.isPlayable = track.is_playable;
        }
        this.name = track.name;
        if (track.album != null) {
            this.imageURL = track.album.images != null && track.album.images.size() > 0 ?
                    track.album.images.get(0).url : null;
        }
        this.imageURL= track.album != null && track.album.images != null && track.album.images.size() > 0 ?
                track.album.images.get(0).url : null;

    }

    public String getAlbumURI() {
        return albumURI;
    }

    public List<String> getArtistURI() {
        return artistURI;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
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
                isPlayable == track.isPlayable &&
                Objects.equals(URI, track.URI) &&
                Objects.equals(albumURI, track.albumURI) &&
                Objects.equals(artistURI, track.artistURI) &&
                Objects.equals(name, track.name) &&
                Objects.equals(imageURL, track.imageURL);
    }

    @Override
    public int hashCode() {

        return Objects.hash(URI, albumURI, artistURI, durationMs, isPlayable, name, imageURL);
    }
}

