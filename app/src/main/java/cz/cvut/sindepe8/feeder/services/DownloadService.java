package cz.cvut.sindepe8.feeder.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.cvut.sindepe8.feeder.fragments.TaskFragment;
import cz.cvut.sindepe8.feeder.helpers.DownloadWakeLockHelper;
import cz.cvut.sindepe8.feeder.models.ArticleItemModel;
import cz.cvut.sindepe8.feeder.persistence.DbConstants;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;

public class DownloadService extends Service {
    public static final int MSG_REGISTER = 1;
    public static final int MSG_UNREGISTER = 2;
    public static final int MSG_SET = 3;
    public static final int MSG_DOWNLOAD_STARTED = 4;
    public static final int MSG_DOWNLOAD_FINISHED = 5;


    public static final String EXTRA_LOG_MESSAGE = "cz.cvut.sindepe8.EXTRA_LOG_MESSAGE";
    private static final int LOG_COUNT = 5;
    private static final int LOG_INTERVAL_MS = 200;

    private Handler mPostHandler;

    ArticlesAsyncTask mTask;

    ArrayList<Messenger> mClients = new ArrayList<>();
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    int mValue = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread("serviceThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        mPostHandler = new Handler(looper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }

        if(mTask == null || mTask.getStatus() == AsyncTask.Status.FINISHED)
        {
            // Start async command for refreshing the articles
            mTask = new ArticlesAsyncTask();
            mTask.execute();
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER: mClients.add(msg.replyTo); break;
                case MSG_UNREGISTER: mClients.remove(msg.replyTo); break;


                default: super.handleMessage(msg);
            }
        }
    }

    void sendMessage(int m) {
        for(Messenger client : mClients) {
            try {
                client.send(Message.obtain(null, m));
            } catch (RemoteException e) {}
        }
    }

    private class ArticlesAsyncTask extends AsyncTask<URL, Integer, List<ArticleItemModel>> {

        @Override
        protected void onPreExecute() {
            sendMessage(MSG_DOWNLOAD_STARTED);
        }

        @Override
        protected List<ArticleItemModel> doInBackground(URL... none) {
            // Get feeds
            Cursor cursor = getContentResolver().query(FeedReaderContentProvider.FEEDS_URI, new String[] {
                    DbConstants.URL }, null, null, null);
            ArrayList<URL> feeds = new ArrayList<>();
            while (cursor.moveToNext()){
                try {
                    String rawUrl = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
                    URL url = new URL(rawUrl);
                    feeds.add(url);
                    Thread.sleep(3000);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e)
                {}
            }
            cursor.close();

            // Remove all articles from DB
            getContentResolver().delete(FeedReaderContentProvider.ARTICLES_URI, null, null);

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

                        getContentResolver().insert(FeedReaderContentProvider.ARTICLES_URI, cv);
                    }

                    System.out.println(feed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... percent) {
//            if (mCallbacks != null) {
//                mCallbacks.onProgressUpdate(percent[0]);
//            }
        }

        @Override
        protected void onCancelled() {
            sendMessage(MSG_DOWNLOAD_FINISHED);
            DownloadWakeLockHelper.release();
        }

        @Override
        protected void onPostExecute(List<ArticleItemModel> articles) {
            sendMessage(MSG_DOWNLOAD_FINISHED);
            DownloadWakeLockHelper.release();
        }
    }
}
