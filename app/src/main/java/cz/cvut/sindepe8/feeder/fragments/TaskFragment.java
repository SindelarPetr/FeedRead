package cz.cvut.sindepe8.feeder.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import java.net.URL;
import java.util.List;

import cz.cvut.sindepe8.feeder.models.ArticleItemModel;

/**
 * Created by petrs on 13-Apr-18.
 */

public class TaskFragment extends Fragment {
    private TaskCallbacks mCallbacks;
    private ArticlesAsyncTask mTask;

    public static interface TaskCallbacks {
        void onPreExecute();

        void onProgressUpdate(int percent);

        void onCancelled();

        void onPostExecute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }


    public void executeTask(){
        mTask = new ArticlesAsyncTask();
        mTask.execute();
    }

    public void cancelTask(){
        mTask.cancel(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private class ArticlesAsyncTask extends AsyncTask<URL, Integer, List<ArticleItemModel>> {

        @Override
        protected void onPreExecute() {
            if (mCallbacks != null) {
                mCallbacks.onPreExecute();
            }
        }

        @Override
        protected List<ArticleItemModel> doInBackground(URL... none) {
            for (int i = 0; !isCancelled() && i < 100; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... percent) {
            if (mCallbacks != null) {
                mCallbacks.onProgressUpdate(percent[0]);
            }
        }

        @Override
        protected void onCancelled() {
            if (mCallbacks != null) {
                mCallbacks.onCancelled();
            }
        }

        @Override
        protected void onPostExecute(List<ArticleItemModel> articles) {
            if (mCallbacks != null) {
                mCallbacks.onPostExecute();
            }
        }
    }
}
