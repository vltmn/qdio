package io.hamo.qdio.view.fragments.playing;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import io.hamo.qdio.R;
import io.hamo.qdio.model.music.Artist;
import io.hamo.qdio.model.music.Track;

public class PlayingStatusFragment extends Fragment {

    private PlayingStatusViewModel viewModel;
    private TextView trackName;
    private ImageView albumImage;
    private TextView artistName;
    private SeekBar seekBar;
    private ImageButton playPauseBtn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new PlayingStatusViewModel();

        viewModel.getCurrentPosition().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
                seekBar.setProgress(aLong.intValue());
            }
        });

        viewModel.getCurrentlyPlayingTrack().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(@Nullable Track track) {
                StringBuilder sb = new StringBuilder();
                for (Artist a : track.getArtists()){
                    if(track.getArtists().indexOf(a) != 0){
                        sb.append(", ");
                    }
                    sb.append(a.getName());
                }
                artistName.setText(sb.toString());
                seekBar.setMax((int) track.getDurationMs());
            }
        });

        viewModel.getIsPlaying().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    playPauseBtn.setImageResource(android.R.drawable.ic_media_pause);
                }
                else {
                    playPauseBtn.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        });

        viewModel.getCurrentlyPlayingTrack().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(@Nullable Track track) {
                trackName.setText(track.getName());
            }
        });

        viewModel.getCurrentAlbumImage().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                albumImage.setImageBitmap(bitmap);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playing_status, container, false);
        trackName = (TextView)view.findViewById(R.id.trackName);
        artistName = (TextView)view.findViewById(R.id.artistName);
        albumImage = (ImageView) view.findViewById(R.id.albumImage);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        playPauseBtn = (ImageButton) view.findViewById(R.id.playPauseBtn);
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.playBtnClicked();
            }
        });
        if (!viewModel.isMasterRoom()){
            View buttonContainer = view.findViewById(R.id.buttonContainer);
            buttonContainer.setVisibility(View.GONE);
            seekBar.setVisibility(View.GONE);
        }
        else{
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int progress = seekBar.getProgress();
                    viewModel.onSeekbarDrag(progress);
                }
            });
        }


        return view;

    }
}
