package cz.cvut.sindepe8.feeder.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.persistence.DbConstants;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;

/**
 * Created by petrs on 15-Apr-18.
 */

public class ArticlesCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private Context mContext;

    public ArticlesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_article, parent, false);

        ArticleModel article = getArticleFromCursor(cursor);

        TextView titleTextView = view.findViewById(R.id.title);
        TextView contentTextView = view.findViewById(R.id.content);

        ViewHolder holder = new ViewHolder(titleTextView, contentTextView, article);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        ArticleModel article = getArticleFromCursor(cursor);
        holder.setArticle(article);
    }

    private ArticleModel getArticleFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(DbConstants.ID));
        String title = cursor.getString(cursor.getColumnIndex(DbConstants.TITLE));
        String url = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
        String content = Html.fromHtml(cursor.getString(cursor.getColumnIndex(DbConstants.CONTENT))).toString();

        return new ArticleModel(id, title, content, url);
    }

    private View.OnClickListener deleteArticleListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            deleteArticle((String) v.getTag());
        }
    };

    private void deleteArticle(String id) {
        mContext.getContentResolver().delete(Uri.withAppendedPath(FeedReaderContentProvider.ARTICLES_URI, id), null, null);
    }

    public class ViewHolder {
        private TextView title;
        private TextView content;
        private ArticleModel article;

        public ViewHolder(TextView title, TextView content, ArticleModel article){
            this.title = title;
            this.content = content;
            setArticle(article);
        }

        public void setArticle(ArticleModel article){
            this.title.setText(article.getTitle());
            this.content.setText(article.getContent());
            this.article = article;
        }

        public ArticleModel getArticle() {
            return article;
        }
    }
}
