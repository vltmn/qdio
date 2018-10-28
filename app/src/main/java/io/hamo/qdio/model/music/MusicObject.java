package io.hamo.qdio.model.music;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 *
 *
 * A collection of Track, Album and Artist info from the SpotifyMusicDataService class
 */
public interface MusicObject {

    /**
     * Gives the URI key to the musicobject
     *
     * @return URI string
     */
    String getURI();

    /**
     * Gives the name of the musicobject
     *
     * @return String
     */
    String getName();


}
