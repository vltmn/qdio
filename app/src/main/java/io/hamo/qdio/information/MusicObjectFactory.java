package io.hamo.qdio.information;

import java.util.ArrayList;
import java.util.List;

import io.hamo.qdio.model.music.Album;
import io.hamo.qdio.model.music.Artist;
import io.hamo.qdio.model.music.Track;
import kaaes.spotify.webapi.android.models.ArtistSimple;

/**
 * Factory to create MusicObject
 */
public class MusicObjectFactory {

    /**
     * Creates a Qdio implementation of Track based on Spotify remote API Track implementation
     * @return a Track object
     */
    public static Track createTrack(com.spotify.protocol.types.Track track) {
        if (track.uri == null) {
            throw new RuntimeException("Track uri is null");
        }
        if (track.album == null) {
            throw new RuntimeException("Track album is null");
        }
        if (track.artists == null) {
            throw new RuntimeException("Track artists is null");
        }
        if (track.duration == 0) {
            throw new RuntimeException("Track duration is zero");
        }
        if (track.name == null) {
            throw new RuntimeException("Track name is null");
        }
        if (track.imageUri == null) {
            throw new RuntimeException("Track imageUri is null");
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

    /**
     * Creates a Qdio implementation of Track based on Spotify web API Track implementation
     * @return a Track object
     */
    public static Track createTrack(kaaes.spotify.webapi.android.models.Track track) {
        if (track.uri == null) {
            throw new RuntimeException("Track URI is null");
        }
        if (track.album == null) {
            throw new RuntimeException("Album is null");
        }
        if (track.artists == null) {
            throw new RuntimeException("Artists list is null");
        }
        if (track.duration_ms == 0) {
            throw new RuntimeException("Duration is zero");
        }
        if (track.name == null) {
            throw new RuntimeException("track name is null");
        }
        if (track.album.images == null || track.album.images.size() == 0) {
            throw new RuntimeException("track album images is null");
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

    /**
     * Creates a Qdio implementation of Artist based on Spotify remote API Artist implementation
     * @return a Artist object
     */
    public static Artist createArtist(com.spotify.protocol.types.Artist artist) {
        if (artist.uri == null) {
            throw new RuntimeException("Artist uri is null");
        }
        if (artist.name == null) {
            throw new RuntimeException("Artist name is null");
        }
        String URI = artist.uri;
        String name = artist.name;
        return new Artist(URI, name);
    }

    /**
     * Creates a Qdio implementation of Artist based on Spotify web API Artist implementation
     * @return a Artist object
     */
    public static Artist createArtist(ArtistSimple artist) {
        if (artist.uri == null) {
            throw new RuntimeException("Artist uri is null");
        }
        if (artist.name == null) {
            throw new RuntimeException("Artist name is null");
        }
        String URI = artist.uri;
        String name = artist.name;
        return new Artist(URI, name);
    }

    /**
     * Creates a Qdio implementation of Album based on Spotify remote API Album implementation
     * @return a Album object
     */
    public static Album createAlbum(com.spotify.protocol.types.Album album) {
        if (album.uri == null) {
            throw new RuntimeException("Album uri is null");
        }
        if (album.name == null) {
            throw new RuntimeException("Album name is null");
        }
        String URI = album.uri;
        String name = album.name;
        return new Album(URI, name);
    }

    /**
     * Creates a Qdio implementation of Album based on Spotify web API Album implementation
     * @return a Album object
     */
    public static Album createAlbum(kaaes.spotify.webapi.android.models.Album album) {
        if (album.uri == null) {
            throw new RuntimeException("Album Uri is null");
        }
        if (album.name == null) {
            throw new RuntimeException("Album name is null");
        }
        if (album.images == null || album.images.size() == 0) {
            throw new RuntimeException("Album images list is null");
        }
        if (album.artists == null) {
            throw new RuntimeException("Album artists is null");
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
