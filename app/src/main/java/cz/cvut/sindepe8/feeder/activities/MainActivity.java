package cz.cvut.sindepe8.feeder.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.fragments.ArticlesFragment;

public class MainActivity extends AppCompatActivity {
    private final String ARTICLES_FRAGMENT = "ArticlesFragment";
    private final int ARTICLE_LOADER = 2;
    public ArticlesFragment GetArticlesFragment(){
        return (ArticlesFragment)getSupportFragmentManager().findFragmentByTag(ARTICLES_FRAGMENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // If the fragments are already created, return.
        if(savedInstanceState != null)
            return;

        Fragment fragment = new ArticlesFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.ArticlesFragmentLayout, fragment, ARTICLES_FRAGMENT);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_configure_feeds){
            Intent intent = new Intent(MainActivity.this, FeedsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
