package cz.cvut.sindepe8.feeder.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Console;
import java.util.logging.Logger;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.fragments.ArticlesFragment;
import cz.cvut.sindepe8.feeder.services.FeedService;

public class MainActivity extends AppCompatActivity implements ArticlesFragment.IArticlesFragmentListener {

    private ArticlesFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // If the fragments are already created, return.
        if(savedInstanceState != null)
            return;

        fragment = new ArticlesFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.ArticlesFragmentLayout, fragment);
        ft.commit();

        FeedService.InitService();

        try {
            FeedService.RefreshArticles();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void ArticleSelected(int articleId) {

        // Start a new activity which will get the article id in bundle
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.BUNDLE_ARTICLE_ID, articleId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_configure_feeds:
                Intent intent = new Intent(this, FeedsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
