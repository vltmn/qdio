package android.os;


public abstract class AsyncTask<A, B, C> {
    C result;

    protected abstract C doInBackground(A... params);

    protected void onPostExecute(C result) {
        this.result = result;
    }

    public C get() {
        return result;
    }

    protected void onProgressUpdate(B... values) {
    }

    public AsyncTask<A, B, C> execute(A... params) {
        C result = doInBackground(params);
        onPostExecute(result);
        return this;
    }
}