package io.hamo.qdio.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TrackSimple;

public class Album implements MusicObject {

    private String URI;
    private String name;
    private String imgageURL;
    private List<String> artistURI = new ArrayList<>();


    public Album(kaaes.spotify.webapi.android.models.Album album){
        this.URI=album.uri;
        this.name=album.name;
        this.imgageURL=album.images != null && album.images.size() > 0 ?
                album.images.get(0).url : null;
        if (album.artists != null) {
            for (ArtistSimple a : album.artists) {
                this.artistURI.add(a.uri);
            }
        }

    }

    @Override
    public String getURI() {
        return URI;
    }

    public String getName() {
        return name;
    }

    public String getImgageURL() {
        return imgageURL;
    }

    public List<String> getArtistURI() {
        return artistURI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(URI, album.URI) &&
                Objects.equals(name, album.name) &&
                Objects.equals(imgageURL, album.imgageURL) &&
                Objects.equals(artistURI, album.artistURI);
    }

    @Override
    public int hashCode() {

        return Objects.hash(URI, name, imgageURL, artistURI);
    }
}
