package cz.cvut.sindepe8.feeder.fragments;


import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.activities.ArticleDetailActivity;
import cz.cvut.sindepe8.feeder.adapters.ArticlesCursorAdapter;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;
import cz.cvut.sindepe8.feeder.services.DownloadService;

import static cz.cvut.sindepe8.feeder.persistence.DbConstants.CONTENT;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.ID;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.TITLE;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor>, TaskFragment.TaskCallbacks {
    private final String STATE_REFRESHING = "refreshing";
    private final int ARTICLE_LOADER = 2;

    private ListView articlesListView;
    private ArticlesCursorAdapter adapter;
    private boolean refreshing = false;
    //private TaskFragment taskFragment;
    private ProgressBar progressBar;

    private ArticleSelectionListener listener;

    private ArticlesHandler mHandler;
    Messenger mService;
    boolean mIsBound;
    final Messenger mMessenger = new Messenger(new ArticlesHandler());
    private ServiceConnection mConnection = new ArticlesServiceConnection();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FragmentManager fm = getFragmentManager();

        //taskFragment = (TaskFragment) fm.findFragmentByTag("task");
        //if (taskFragment == null) {
         //   taskFragment = new TaskFragment();
        //    fm.beginTransaction().add(taskFragment, "task").commit();
        //}

        if(savedInstanceState != null) {
            refreshing = savedInstanceState.getBoolean(STATE_REFRESHING);
            return;
        }

        mHandler = new ArticlesHandler();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ArticleSelectionListener)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        articlesListView = view.findViewById(R.id.articles);
        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleModel article = ((ArticlesCursorAdapter.ViewHolder) view.getTag()).getArticle();

                listener.articleSelected(article.getId());
            }
        });

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        progressBar = toolbar.findViewById(R.id.progress);
        progressBar.setVisibility(refreshing ? View.VISIBLE : View.GONE);

        adapter = new ArticlesCursorAdapter(getContext(), null, 0);
        articlesListView.setAdapter(adapter);

        getLoaderManager().initLoader(ARTICLE_LOADER, null, this);

        // Bind to service
        doBindService();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_refresh);
        item.setVisible(!refreshing);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putBoolean(STATE_REFRESHING, refreshing);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        doUnbindService();
    }

    private void refresh(){

        // Start downloading
        getContext().startService(new Intent(getContext(), DownloadService.class));
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case ARTICLE_LOADER:
                return new CursorLoader(getContext(), FeedReaderContentProvider.ARTICLES_URI, new String[] { ID, TITLE, URL, CONTENT}, null, null, null);
            default:
                break;
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case ARTICLE_LOADER:
                adapter.swapCursor(cursor);
                break;

            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        switch (loader.getId()) {
            case ARTICLE_LOADER:
                adapter.swapCursor(null);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPreExecute() {
        refreshing = true;
        getActivity().invalidateOptionsMenu();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressUpdate(int percent) {

    }

    @Override
    public void onCancelled() {
        refreshing = false;
        getActivity().invalidateOptionsMenu();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPostExecute() {
        refreshing = false;
        getActivity().invalidateOptionsMenu();
        progressBar.setVisibility(View.GONE);
    }

    void doBindService() {
        Intent intent = new Intent(getActivity().getApplicationContext(), DownloadService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if(!mIsBound)
            return;

        if(mService != null) {
            try {
                Message msg = Message.obtain(null, DownloadService.MSG_UNREGISTER);
                msg.replyTo = mMessenger;
                mService.send(msg);
            }
            catch (RemoteException e) { }
            getActivity().unbindService(mConnection);
            mIsBound = false;
        }
    }

    public interface ArticleSelectionListener{
        public void articleSelected(int id);
    }

    private class ArticlesHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DownloadService.MSG_DOWNLOAD_STARTED:
                    onPreExecute();
                    break;
                case DownloadService.MSG_DOWNLOAD_FINISHED:
                    onPostExecute();
                    break;
                default: super.handleMessage(msg);
            }
        }
    }

    private class ArticlesServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            try {
                Message msg = Message.obtain(null, DownloadService.MSG_REGISTER);
                msg.replyTo = mMessenger;
                mService.send(msg);
            }
            catch (RemoteException e) { }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    }
}

