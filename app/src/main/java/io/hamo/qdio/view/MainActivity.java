package io.hamo.qdio.view;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import io.hamo.qdio.R;
import io.hamo.qdio.model.music.Track;
import io.hamo.qdio.room.RoomInstanceHolder;

public class MainActivity extends FragmentActivity implements TrackListAdapter.OnListItemClickedListener{

    private View mainWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainWrapper = findViewById(R.id.mainWrapper);
        mainWrapper.bringToFront();


        final View searchFragmentWrapper = findViewById(R.id.searchFragmentWrapper);


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFragmentWrapper.bringToFront();
            }
        });
    }


    @Override
    public void onSearchResultItemClicked(Track t) {
        RoomInstanceHolder.getRoomInstance().addToQueue(t);
        mainWrapper.bringToFront();
    }

    public void hideSearchView(View view){

    }
}
