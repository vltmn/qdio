package io.hamo.qdio.information;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageFetchTask extends AsyncTask<String, Void, Bitmap> {

    protected Bitmap doInBackground(String... urls) {
        String urlShow = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(urlShow).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return bitmap;
    }
}
