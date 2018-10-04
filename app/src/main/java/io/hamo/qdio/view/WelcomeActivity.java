package io.hamo.qdio.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import io.hamo.qdio.MainActivity;
import io.hamo.qdio.R;
import io.hamo.qdio.room.Room;
import io.hamo.qdio.room.RoomInstanceHolder;
import io.hamo.qdio.room.init.RoomCreationService;

public class WelcomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        checkPermissions();
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
        Intent intent = new Intent(this, MainActivity.class);
        RoomCreationService roomCreationService = new RoomCreationService(this);
        Room room = roomCreationService.createRoom(getRandomRoomName());
        RoomInstanceHolder.setRoomInstance(room);
        startActivity(intent);
    }

    private static String getRandomRoomName() {
        return String.valueOf(Math.floor(3 * Math.random()));
    }

    public void joinRoom(View view) {
        Intent intent = new Intent(this, RoomDiscoveryActivity.class);
        startActivity(intent);
    }
}
