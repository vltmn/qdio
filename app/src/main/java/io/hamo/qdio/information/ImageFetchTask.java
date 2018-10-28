package io.hamo.qdio.information;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * @author Melker Veltman
 * @author Hugo Cliffordson
 * @author Oskar Wallgren
 * @author Alrik Kjellberg
 * <p>
 * <p>
 * Async task to get a bitmap from an URL
 */
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
