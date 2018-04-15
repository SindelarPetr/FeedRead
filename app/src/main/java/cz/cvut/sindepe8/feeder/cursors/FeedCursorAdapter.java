package cz.cvut.sindepe8.feeder.cursors;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.persistence.DbConstants;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;

import static cz.cvut.sindepe8.feeder.persistence.DbConstants.ID;

/**
 * Created by petrs on 15-Apr-18.
 */

public class FeedCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private Context mContext;

    public FeedCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_feed, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        try {
            String title = cursor.getString(cursor.getColumnIndex(DbConstants. TITLE));
            URL url = new URL(cursor.getString(cursor.getColumnIndex(DbConstants.URL)));

        TextView titleTextView = view.findViewById(R.id.title);
        TextView urlTextView = view.findViewById(R.id.url);

        titleTextView.setText(title);
        urlTextView.setText(url.toString());

        view.findViewById(R.id.delete).setOnClickListener(deleteArticleListener);
        view.findViewById(R.id.delete).setTag(cursor.getString(cursor.getColumnIndex(ID)));

        } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    }

    private View.OnClickListener deleteArticleListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            deleteArticle((String) v.getTag());
        }
    };

    private void deleteArticle(String id) {
        mContext.getContentResolver().delete(Uri.withAppendedPath(FeedReaderContentProvider.CONTENT_URI, id), null, null);
    }

}
