package cz.cvut.sindepe8.feeder.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.fragments.ArticleFragment;
import cz.cvut.sindepe8.feeder.fragments.ArticlesFragment;
import cz.cvut.sindepe8.feeder.services.DownloadService;

public class MainActivity extends AppCompatActivity implements ArticlesFragment.ArticleSelectionListener {
    private final String ARTICLES_FRAGMENT = "ArticlesFragment";
    private final String ARTICLE_FRAGMENT = "ArticleFragment";
    private final String STATE_ARTICLE_ID = "ArticleId";
    private final int ARTICLE_LOADER = 2;

    private int selectedArticle = -1;

    public ArticlesFragment GetArticlesFragment(){
        return (ArticlesFragment)getSupportFragmentManager().findFragmentByTag(ARTICLES_FRAGMENT);
    }

    private ArticleFragment GetArticleFragment(){
        return (ArticleFragment)getSupportFragmentManager().findFragmentByTag(ARTICLE_FRAGMENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // If the fragments are already created, return.
        if(savedInstanceState != null) {
            selectedArticle = savedInstanceState.getInt(STATE_ARTICLE_ID, -1);
        }

        FragmentManager fm = getSupportFragmentManager();

        // Create articles fragment
        if(GetArticlesFragment() == null) {
            ArticlesFragment articles = new ArticlesFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.articles, articles, ARTICLES_FRAGMENT);
            ft.commit();
        }

        // Create article fragment
        if(findViewById(R.id.article) != null) {
            FragmentTransaction ft = fm.beginTransaction();
            if (findViewById(R.id.article) != null) {
                ArticleFragment articleFragment = new ArticleFragment();
                Bundle args = new Bundle();
                args.putInt(ArticleFragment.BUNDLE_ARTICLE_ID, selectedArticle);
                articleFragment.setArguments(args);
                ft.replace(R.id.article, articleFragment, ARTICLE_FRAGMENT);
            }
            ft.commit();
        }

        // Start alarms for downloading
        AlarmManager am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES / 15;
        long time = System.currentTimeMillis() + interval;
        Intent launchIntent = new Intent(this, AlarmManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, launchIntent, PendingIntent.FLAG_NO_CREATE);
        // pendingIntent is null when the alarm is already set
        if(pendingIntent != null) {
            am.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, pendingIntent);
            Log.i("BootBroadcastReceiver", "Alarm has been set.");
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_ARTICLE_ID, selectedArticle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void articleSelected(int id) {
        selectedArticle = id;
        if(findViewById(R.id.article) == null) {
            // On portrait - start a new activity with the given article id
            Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
            intent.putExtra(ArticleDetailActivity.BUNDLE_ARTICLE_ID, id);
            startActivity(intent);
        }
        else {
            // On landscape - display the article
            GetArticleFragment().DisplayArticle(id);
        }
    }

    private void startDownloadService() {
        Intent startIntent = new Intent(this, DownloadService.class);
        //startIntent.putExtra(DownloadService.EXTRA_LOG_MESSAGE, logMessage);
        startService(startIntent);
    }
}
