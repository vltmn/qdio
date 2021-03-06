package io.hamo.qdio.model.music;

import java.util.Objects;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 *
 *
 * This class represents an Artist. It is immutable
 */
public class Artist implements MusicObject {

    private final String URI;
    private final String name;

    public Artist(String URI, String name) {
        this.URI = URI;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getURI() {
        return URI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(URI, artist.URI) &&
                Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(URI, name);
    }
}
