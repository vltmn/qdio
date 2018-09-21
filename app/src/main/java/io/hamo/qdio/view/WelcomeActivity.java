package io.hamo.qdio.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.hamo.qdio.MainActivity;
import io.hamo.qdio.R;

public class WelcomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void createRoom(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void joinRoom(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
