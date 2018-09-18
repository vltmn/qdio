package io.hamo.qdio.playback;

import io.hamo.qdio.music.MusicObject;

public interface Player {

    public void resume();

    public void pause();

    public void seek(long ms);

    public void play(MusicObject obj);


}
