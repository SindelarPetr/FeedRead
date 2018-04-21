package cz.cvut.sindepe8.feeder.activities;

import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.adapters.FeedCursorAdapter;
import cz.cvut.sindepe8.feeder.persistence.FeedReadDatabaseHelper;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;
import cz.cvut.sindepe8.feeder.persistence.FeedTable;

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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFeedId = ((FeedCursorAdapter.ViewHolder) view.getTag()).getId(); // ASK - how to make it clean
                new AlertDialog.Builder(FeedsActivity.this)
                        .setTitle("Delete feed")
                        .setMessage("Do you want to delete the feed?")
                        .setPositiveButton("Delete", new ConfirmDeleteDialogListener(selectedFeedId))
                        .setNegativeButton("Cancel", null).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode != REQUEST_ADD_FEED || resultCode == AddFeedActivity.RESULT_CANCEL)
            return;

        String url = data.getStringExtra(AddFeedActivity.DATA_URL);
        String title = data.getStringExtra(AddFeedActivity.DATA_TITLE);

        insertContentValue(FeedTable.createContentValues(title, url));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case FEED_LOADER:
                return new CursorLoader(FeedsActivity.this, FeedReaderContentProvider.FEEDS_URI, new String[] { ID, URL, TITLE}, null, null, null);
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

    private void insertContentValue(ContentValues cv) {
        getContentResolver().insert(FeedReaderContentProvider.FEEDS_URI, cv);
    }

    public void addMobilmania(View view) {
        insertFeed("Mobilmania", "https://www.mobilmania.cz/rss/sc-47/");
    }

    public void addSmartmania(View view) {
        insertFeed("Smartmania", "https://smartmania.cz/feed/");
    }

    public void addZive(View view) {
        insertFeed("Zive", "https://www.zive.cz/rss/sc-47");
    }

    private class ConfirmDeleteDialogListener implements Dialog.OnClickListener {
        String id;
        public ConfirmDeleteDialogListener(String id){
            super();
            this.id = id;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            getContentResolver().delete(Uri.withAppendedPath(FeedReaderContentProvider.FEEDS_URI, id), null, null);
        }
    }

    private void insertFeed(String title, String url){
        ContentValues cv = FeedTable.createContentValues(title, url);
        insertContentValue(cv);
    }
}
