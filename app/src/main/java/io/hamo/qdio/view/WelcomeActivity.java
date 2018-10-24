package io.hamo.qdio.view;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import io.hamo.qdio.R;
import io.hamo.qdio.playback.ConnectFragment;
import io.hamo.qdio.playback.PlayerFactory;
import io.hamo.qdio.room.Room;
import io.hamo.qdio.room.RoomInstanceHolder;
import io.hamo.qdio.room.init.RoomCreationService;

/**
 * The first activity shwon when the application is launched
 * User gets choice to either create a room or join an existing room
 */
public class WelcomeActivity extends FragmentActivity {
    private ProgressBar roomConnectProgress;
    private LinearLayout buttonGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        checkPermissions();
        roomConnectProgress = findViewById(R.id.roomConnectProgress);
        buttonGroup = findViewById(R.id.buttonGroup);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.w(getClass().getSimpleName(), "Permission not granted", null);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);

        }
    }

    public void createRoom(View view) {
        buttonGroup.setVisibility(View.INVISIBLE);
        roomConnectProgress.setVisibility(View.VISIBLE);
        Fragment connectFragment = new ConnectFragment();
        getSupportFragmentManager().beginTransaction()
            .add(connectFragment, ConnectFragment.TAG).commit();

        final Intent intent = new Intent(this, MainActivity.class);
        final RoomCreationService roomCreationService = new RoomCreationService(this);
        PlayerFactory.getIsInstantiated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null || !aBoolean) {
                    return;
                }
                Room room = roomCreationService.createRoom(getRandomRoomName());
                RoomInstanceHolder.setRoomInstance(room);
                startActivity(intent);
            }
        });
    }

    private static String getRandomRoomName() {
        return String.valueOf((int)Math.floor(100 * Math.random()));
    }

    public void joinRoom(View view) {
        Intent intent = new Intent(this, RoomDiscoveryActivity.class);
        startActivity(intent);
    }


}
