package cz.cvut.sindepe8.feeder.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.fragments.ArticlesFragment;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.services.StorageService;

public class MainActivity extends AppCompatActivity implements ArticlesFragment.IArticlesFragmentListener {

    private ArticlesFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If the fragments are already created, return.
        if(savedInstanceState != null)
            return;

        fragment = new ArticlesFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.ArticlesFragmentLayout, fragment);
        ft.commit();

        StorageService.InitStorage();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: load articles to the ArticlesFragment
        List<ArticleModel> articles = StorageService.GetAllArticles();
        fragment.DisplayArticles(articles);
    }

    @Override
    public void ArticleSelected(ArticleModel article) {

        // Start a new activity which will get the article id in bundle
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.BUNDLE_ARTICLE_ID, article.getId());
        startActivity(intent);
    }
}
