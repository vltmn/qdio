package io.hamo.qdio.music;

import java.util.ArrayList;
import java.util.List;

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
}
