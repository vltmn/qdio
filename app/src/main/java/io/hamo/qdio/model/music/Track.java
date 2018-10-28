package io.hamo.qdio.model.music;

import java.util.List;
import java.util.Objects;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * This class represents a track. It is immutable.
 */

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

    /**
     * Gives the uri to the album the track is contained by
     *
     * @return String uri
     */
    public String getAlbumURI() {
        return albumURI;
    }

    /**
     * Gives the duration of the track
     *
     * @return duration in milliseconds as a long
     */
    public long getDurationMs() {
        return durationMs;
    }


    public String getName() {
        return name;
    }

    /**
     * Gives the url to the Image connected with track
     *
     * @return string
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * gives a list containg the artist or artists that are included in the track
     *
     * @return
     */
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

