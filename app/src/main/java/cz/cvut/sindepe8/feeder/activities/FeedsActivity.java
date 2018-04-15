package cz.cvut.sindepe8.feeder.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.cursors.FeedCursorAdapter;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;

import static cz.cvut.sindepe8.feeder.persistence.DbConstants.*;

public class FeedsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int REQUEST_ADD_FEED = 0;
    private final int FEED_LOADER = 1;
    private ListView mListView;
    private FeedCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        mListView = findViewById(R.id.feeds);
        mAdapter = new FeedCursorAdapter(this, null, 0);
        mListView.setAdapter(mAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getLoaderManager().initLoader(FEED_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu ,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(this, AddFeedActivity.class);
                startActivityForResult(intent, REQUEST_ADD_FEED);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode != REQUEST_ADD_FEED || resultCode == AddFeedActivity.RESULT_CANCEL)
            return;

        String textUrl = data.getStringExtra(AddFeedActivity.DATA_URL);
        try {
            URL url = new URL(textUrl);

            // Add the url to the database

            // Add the url to the list of feeds
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case FEED_LOADER:
                return new CursorLoader(FeedsActivity.this, FeedReaderContentProvider.CONTENT_URI, new String[] { ID, URL, TITLE}, null, null, null);
            default:
                break;
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case FEED_LOADER:
                mAdapter.swapCursor(cursor);
                break;

            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case FEED_LOADER:
                mAdapter.swapCursor(null);
                break;

            default:
                break;
        }
    }
}
