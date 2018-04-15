package cz.cvut.sindepe8.feeder.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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

        // Set the fragment
        if(savedInstanceState != null)
            return;


        Intent intent = getIntent();
        int articleId = intent.getIntExtra(BUNDLE_ARTICLE_ID, 0);
        Bundle bundle = new Bundle();
        bundle.putInt(ArticleFragment.BUNDLE_ARTICLE_ID, articleId);

        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.ArticleFragmentLayout, fragment);
        ft.commit();
    }
}
