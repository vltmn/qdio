package io.hamo.qdio.information;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.hamo.qdio.model.music.Album;
import io.hamo.qdio.model.music.Artist;
import io.hamo.qdio.model.music.Track;

public interface MusicDataService {

    Callable<List<Track>> searchForTrack(String query);

    Callable<Map<String, Artist>> getArtistsFromUris(List<String> artists);

    Callable<Artist> getArtist(String artistUri);

    Callable<Album> getAlbumFromUri(String albumUri);

    Callable<Track> getTrackFromUri(String trackUri);

    Callable<Map<String, Track>> getTracksFromUris(List<String> tracks);
}
