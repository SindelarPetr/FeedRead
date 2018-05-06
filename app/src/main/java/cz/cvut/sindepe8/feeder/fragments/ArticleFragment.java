package cz.cvut.sindepe8.feeder.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.persistence.DbConstants;
import cz.cvut.sindepe8.feeder.persistence.FeedReaderContentProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {
    public static final String BUNDLE_ARTICLE_ID = "ArticleId";

    private ShareActionProvider shareActionProvider;

    private TextView placeholder;
    private TextView headerTextView;
    private TextView contentTextView;
    private String title;
    private String content;
    private String uri;

    public void DisplayArticle(int articleId)
    {
        if(articleId == -1)
            return;

        // Hide select an article text
        if(placeholder != null)
            placeholder.setVisibility(View.INVISIBLE);

        // Get the article by the ID
        Cursor cursor = getContext().getContentResolver().query(FeedReaderContentProvider.ARTICLES_URI, new String[]{DbConstants.URL, DbConstants.CONTENT, DbConstants.TITLE},
                "_id = ?", new String[] { Integer.toString(articleId) }, null);

        if(!cursor.moveToFirst()) {
            return;
            //throw new Resources.NotFoundException("Couldnt find an article with such ID: " + articleId);
        }

        title = cursor.getString(cursor.getColumnIndex(DbConstants.TITLE));
        uri = cursor.getString(cursor.getColumnIndex(DbConstants.URL));
        content = cursor.getString(cursor.getColumnIndex(DbConstants.CONTENT));

        headerTextView.setText(title);
        contentTextView.setText(content);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        // Get header and content TextViews
        headerTextView = view.findViewById(R.id.title);
        contentTextView = view.findViewById(R.id.content);
        placeholder = view.findViewById(R.id.select_article_text);

        // Get article id
        if(getArguments() != null) {
            int articleId = getArguments().getInt(BUNDLE_ARTICLE_ID, -1);
            DisplayArticle(articleId);
        }

        final FloatingActionButton fab = view.findViewById(R.id.open_in_browser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });


        // Nested scroll view
//        NestedScrollView nsv = view.findViewById(R.id.scrollView);
//        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY > oldScrollY) {
//                    fab.hide();
//                } else {
//                    fab.show();
//                }
//            }
//        });

        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.article_detail_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_share);
//
//        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
//        setShareActionIntent();
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    private void setShareActionIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        shareActionProvider.setShareIntent(intent);
    }
}
