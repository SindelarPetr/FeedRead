package cz.cvut.sindepe8.feeder.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URI;
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

        String title = cursor.getString(cursor.getColumnIndex(DbConstants. TITLE));
        String url = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
        String id = cursor.getString(cursor.getColumnIndex(ID));

        TextView titleTextView = view.findViewById(R.id.title);
        TextView urlTextView = view.findViewById(R.id.url);

        ViewHolder holder = new ViewHolder(titleTextView, urlTextView);
        holder.update(title, url, id);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(DbConstants. TITLE));
        String url = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
        String id = cursor.getString(cursor.getColumnIndex(ID));

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.update(title, url, id);
    }

    public class ViewHolder{
        private TextView title;
        private TextView url;
        private String id;

        public ViewHolder(TextView title, TextView url){

            this.title = title;
            this.url = url;
        }

        public void update(String title, String url, String  id){
            this.title.setText(title);
            this.url.setText(url);
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}

