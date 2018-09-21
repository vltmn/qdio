package android.os;


public abstract class AsyncTask<Params, Progress, Result> {
    Result result;

    protected abstract Result doInBackground(Params... params);

    protected void onPostExecute(Result result) {
        this.result = result;
    }

    public Result get() {
        return result;
    }

    protected void onProgressUpdate(Progress... values) {
    }

    public AsyncTask<Params, Progress, Result> execute(Params... params) {
        Result result = doInBackground(params);
        onPostExecute(result);
        return this;
    }
}