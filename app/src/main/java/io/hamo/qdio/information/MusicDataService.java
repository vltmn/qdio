package io.hamo.qdio.information;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.hamo.qdio.model.music.Album;
import io.hamo.qdio.model.music.Artist;
import io.hamo.qdio.model.music.Track;

/**
 * Service for fetching information from the Spotify Web APIs
 */
public interface MusicDataService {

    /**
     * Search the music library by using a query
     * @param query the query to search with, may be a track name, artist name or album naem
     * @return a callable that returns a list of the tracks that matches the query
     */
    Callable<List<Track>> searchForTrack(String query);

    /**
     * Get a list of artists from a list of artist URIs.
     * @param artists a list of artist URIs
     * @return a callable that returns a map with the artist URI and corresponding information from the music library
     */
    Callable<Map<String, Artist>> getArtistsFromUris(List<String> artists);

    /**
     * Get a single artist by using an URI
     * @param artistUri the artist URI to get information for
     * @return a callable that returns the information from the music library
     */
    Callable<Artist> getArtist(String artistUri);

    /**
     * Get a single album from an URI
     * @param albumUri the album URI to get information for
     * @return a callable that returns the album found
     */
    Callable<Album> getAlbumFromUri(String albumUri);

    /**
     * Get a single track by a URI
     * @param trackUri the track URI to get information for
     * @return a callable that returns the information found about the track
     */
    Callable<Track> getTrackFromUri(String trackUri);

    /**
     * Get multiple tracks by URIs
     * @param tracks the URIs to get tracks for
     * @return a callable that returns a map with the track uri and the corresponding information
     */
    Callable<Map<String, Track>> getTracksFromUris(List<String> tracks);
}
