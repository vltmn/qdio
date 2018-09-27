package io.hamo.qdio.playback;

import io.hamo.qdio.music.MusicObject;
import io.hamo.qdio.music.Track;

public interface Player {

    public void resume();

    public void pause();

    public void seek(long ms);

    public void play(MusicObject obj);

    public Track getCurrentTrack();

    public Long getCurrentPosition();

    public PlayerState getPlayerState();


}
