package cz.cvut.sindepe8.feeder.fragments;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.module.Module;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContentImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntryImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.cvut.sindepe8.feeder.activities.MainActivity;
import cz.cvut.sindepe8.feeder.models.ArticleItemModel;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.persistence.ArticleTable;
import cz.cvut.sindepe8.feeder.persistence.DbConstants;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;
import cz.cvut.sindepe8.feeder.persistence.FeedTable;

//import

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

    boolean attached = false;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);


        mCallbacks = (TaskCallbacks) ((MainActivity)activity).GetArticlesFragment();
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
            // Get feeds
            Cursor cursor = getContext().getContentResolver().query(FeedReaderContentProvider.FEEDS_URI, new String[] {
                DbConstants.URL }, null, null, null);
            ArrayList<URL> feeds = new ArrayList<>();
            while (cursor.moveToNext()){
                try {
                    String rawUrl = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
                    URL url = new URL(rawUrl);
                    feeds.add(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();

            // Remove all articles from DB
            getContext().getContentResolver().delete(FeedReaderContentProvider.ARTICLES_URI, null, null);

            // Get articles
            for (URL url : feeds) {

                SyndFeedInput input = new SyndFeedInput();
                try {


                    SyndFeed feed = input.build(new XmlReader(url));
                    List entries = feed.getEntries();

                    for(Object obj : entries) {
                        SyndEntry ent = (SyndEntry)obj;
                        SyndContent description = ent.getDescription();
                        String title = ent.getTitle();
                        String link = ent.getLink();

                        ContentValues cv = new ContentValues();
                        cv.put(DbConstants.TITLE, title);
                        cv.put(DbConstants.CONTENT, description.getValue());
                        cv.put(DbConstants.URL, link);

                        getContext().getContentResolver().insert(FeedReaderContentProvider.ARTICLES_URI, cv);
                    }

                    System.out.println(feed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // Save articles to db

            // Some time to simulate latency
            for (int i = 0; !isCancelled() && i < 30; i++) {
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
