package io.hamo.qdio.information;

import java.util.ArrayList;
import java.util.List;

import io.hamo.qdio.model.music.Album;
import io.hamo.qdio.model.music.Artist;
import io.hamo.qdio.model.music.Track;
import kaaes.spotify.webapi.android.models.ArtistSimple;

public class MusicObjectFactory {

    public static Track createTrack(com.spotify.protocol.types.Track track) {
        if (track.uri == null) {
            throw new RuntimeException("Value is null");
        }
        if (track.album == null) {
            throw new RuntimeException("Value is null");
        }
        if (track.artists == null) {
            throw new RuntimeException("Value is null");
        }
        if (track.duration == 0) {
            throw new RuntimeException("Value is null");
        }
        if (track.name == null) {
            throw new RuntimeException("Value is null");
        }
        if (track.imageUri == null) {
            throw new RuntimeException("Value is null");
        }
        String URI = track.uri;
        String albumURI = track.album.uri;
        List<Artist> artists = new ArrayList<>();
        for (com.spotify.protocol.types.Artist a : track.artists) {
            artists.add(MusicObjectFactory.createArtist(a));
        }
        long durationMs = track.duration;
        String name = track.name;
        String imageURL = track.imageUri.raw;

        return new Track(URI, albumURI, artists, durationMs, name, imageURL);
    }

    public static Track createTrack(kaaes.spotify.webapi.android.models.Track track) {
        if (track.uri == null) {
            throw new RuntimeException("Value is null");
        }
        if (track.album == null) {
            throw new RuntimeException("Value is null");
        }
        if (track.artists == null) {
            throw new RuntimeException("Value is null");
        }
        if (track.duration_ms == 0) {
            throw new RuntimeException("Value is null");
        }
        if (track.name == null) {
            throw new RuntimeException("Value is null");
        }
        if (track.album.images == null || track.album.images.size() == 0) {
            throw new RuntimeException("Value is null");
        }
        String URI = track.uri;
        String albumURI = track.album.uri;
        List<Artist> artists = new ArrayList<>();
        for (ArtistSimple a : track.artists) {
            artists.add(MusicObjectFactory.createArtist(a));
        }
        long durationMs = track.duration_ms;
        String name = track.name;
        String imageURL;
        imageURL = track.album.images.get(0).url;

        return new Track(URI, albumURI, artists, durationMs, name, imageURL);
    }

    public static Artist createArtist(com.spotify.protocol.types.Artist artist) {
        if (artist.uri == null) {
            throw new RuntimeException("Value is null");
        }
        if (artist.name == null) {
            throw new RuntimeException("Value is null");
        }
        String URI = artist.uri;
        String name = artist.name;
        return new Artist(URI, name);
    }

    public static Artist createArtist(ArtistSimple artist) {
        if (artist.uri == null) {
            throw new RuntimeException("Value is null");
        }
        if (artist.name == null) {
            throw new RuntimeException("Value is null");
        }
        String URI = artist.uri;
        String name = artist.name;
        return new Artist(URI, name);
    }

    public static Album createAlbum(com.spotify.protocol.types.Album album) {
        if (album.uri == null) {
            throw new RuntimeException("Value is null");
        }
        if (album.name == null) {
            throw new RuntimeException("Value is null");
        }
        String URI = album.uri;
        String name = album.name;
        return new Album(URI, name);
    }

    public static Album createAlbum(kaaes.spotify.webapi.android.models.Album album) {
        if (album.uri == null) {
            throw new RuntimeException("Value is null");
        }
        if (album.name == null) {
            throw new RuntimeException("Value is null");
        }
        if (album.images == null || album.images.size() == 0) {
            throw new RuntimeException("Value is null");
        }
        if (album.artists == null) {
            throw new RuntimeException("Value is null");
        }
        String URI = album.uri;
        String name = album.name;
        String imageURL = album.images.get(0).url;
        List<String> artistURI = new ArrayList<>();
        for (ArtistSimple a : album.artists) {
            artistURI.add(a.uri);
        }

        return new Album(URI, name, imageURL, artistURI);
    }
}
