package io.hamo.qdio.playback;

import io.hamo.qdio.model.music.MusicObject;
import io.hamo.qdio.model.music.Track;

public interface Player {

    void resume();

    void pause();

    void seek(long ms);

    void play(MusicObject obj);

    Track getCurrentTrack();

    Long getCurrentPosition();

    PlayerState getPlayerState();

    void setOnSongEndCallback(OnSongEndCallback onSongEndCallback);

    interface OnSongEndCallback {

        Track onSongEnd();

    }


}
