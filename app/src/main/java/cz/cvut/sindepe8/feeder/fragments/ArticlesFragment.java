package cz.cvut.sindepe8.feeder.fragments;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Dictionary;
import java.util.List;
import java.util.zip.Inflater;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.activities.FeedsActivity;
import cz.cvut.sindepe8.feeder.cursors.ArticlesCursorAdapter;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.persistence.DbConstants;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;
import cz.cvut.sindepe8.feeder.persistence.FeedTable;
import cz.cvut.sindepe8.feeder.services.FeedService;

import static cz.cvut.sindepe8.feeder.persistence.DbConstants.CONTENT;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.ID;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.TITLE;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor>, TaskFragment.TaskCallbacks {
    private final int ARTICLE_LOADER = 2;
    private IArticlesFragmentListener listener;
    private ListView articlesListView;
    private ArticlesCursorAdapter adapter;
    private boolean refreshing = false;
    private TaskFragment taskFragment;


    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof IArticlesFragmentListener)
        {
            this.listener = (IArticlesFragmentListener)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.listener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FragmentManager fm = getFragmentManager();
        taskFragment = (TaskFragment) fm.findFragmentByTag("task");

        if (taskFragment == null) {
            taskFragment = new TaskFragment();
            fm.beginTransaction().add(taskFragment, "task").commit();
        }
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
                ArticleModel article = (ArticleModel) parent.getItemAtPosition(position);

                listener.ArticleSelected(article.getId());
            }
        });

        adapter = new ArticlesCursorAdapter(getContext(), null, 0);
        articlesListView.setAdapter(adapter);

        getLoaderManager().initLoader(ARTICLE_LOADER, null, this);
        return view;
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

    private void refresh(){

        // Start TaskFragment
        taskFragment.executeTask();


        // Get all feeds
        //Cursor cursor = getActivity().getContentResolver().query(FeedReaderContentProvider.FEEDS_URI, new String[] { ID, TITLE, URL}, null, null, null);

        // Iterate all feeds
        //while (cursor.moveToNext()) {
        //    String url = cursor.getString(cursor.getColumnIndex(DbConstants.URL));

            // For each feed get articles

            // Save articles to the database
        //}
        // ListView with feeds should be updated automatically

        //cursor.close();
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
    }

    @Override
    public void onProgressUpdate(int percent) {

    }

    @Override
    public void onCancelled() {
        refreshing = false;
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onPostExecute() {
        refreshing = false;
        getActivity().invalidateOptionsMenu();
    }

    public interface IArticlesFragmentListener{
        void ArticleSelected(int articleId);
    }

    private void insertContentValue(ContentValues cv) {
        getActivity().getContentResolver().insert(FeedReaderContentProvider.ARTICLES_URI, cv);
}
}

