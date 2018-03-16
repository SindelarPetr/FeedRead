package cz.cvut.sindepe8.feeder.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.fragments.ArticleFragment;

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String BUNDLE_ARTICLE_ID = "ArticleId";
    private ArticleFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Set the fragment
        if(savedInstanceState != null)
            return;

        detailFragment = new ArticleFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.ArticleFragmentLayout, detailFragment);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        int articleId = intent.getIntExtra(BUNDLE_ARTICLE_ID, 0);
        detailFragment.DisplayArticle(articleId);
    }
}
