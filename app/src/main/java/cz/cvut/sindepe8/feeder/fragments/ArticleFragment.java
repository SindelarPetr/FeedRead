package cz.cvut.sindepe8.feeder.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.services.FeedService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {
    public static final String BUNDLE_ARTICLE_ID = "ArticleId";

    private ShareActionProvider shareActionProvider;

    private TextView headerTextView;
    private TextView contentTextView;
    private ArticleModel article;

    public void DisplayArticle(int articleId)
    {
        article = FeedService.GetArticleById(articleId);

        if(article == null)
            return;

        headerTextView.setText(article.getTitle());
        contentTextView.setText(article.getContent());
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
        headerTextView = view.findViewById(R.id.ArticleTitleTextView);
        contentTextView = view.findViewById(R.id.ArticleContentTextView);

        // Get article id
        int articleId = getArguments().getInt(BUNDLE_ARTICLE_ID, -1);
        DisplayArticle(articleId);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.article_detail_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setShareActionIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, article.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, article.getContent());
        shareActionProvider.setShareIntent(intent);
    }
}
