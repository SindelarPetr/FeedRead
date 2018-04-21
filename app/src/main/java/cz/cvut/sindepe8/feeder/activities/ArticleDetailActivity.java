package cz.cvut.sindepe8.feeder.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.fragments.ArticleFragment;

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String BUNDLE_ARTICLE_ID = "ArticleId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set the fragment
        if(savedInstanceState != null)
            return;


        Intent intent = getIntent();
        int articleId = intent.getExtras().getInt(BUNDLE_ARTICLE_ID);
        Bundle bundle = new Bundle();
        bundle.putInt(ArticleFragment.BUNDLE_ARTICLE_ID, articleId);

        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.ArticleFragmentLayout, fragment);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
