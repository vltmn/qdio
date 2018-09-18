package io.hamo.qdio.music;

import java.util.List;

import kaaes.spotify.webapi.android.models.ArtistSimple;

public class Track implements MusicObject {

    private String URI;
    private String albumURI;
    private List<String> artistURI;
    private long durationMs;
    private boolean isPlayable;
    private String name;
    private String imageURL;


    public Track(String URI) {
        this.URI = URI;
    }

    public Track(kaaes.spotify.webapi.android.models.Track track) {
        this.URI = track.uri;
        this.albumURI = track.album.uri;
        if (track.artists != null) {
            for (ArtistSimple a : track.artists) {
                this.artistURI.add(a.uri);
            }
        }
        this.durationMs = track.duration_ms;
        this.isPlayable = track.is_playable;
        this.name = track.name;
        this.imageURL = track.album.images != null && track.album.images.size() > 0 ?
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
}

