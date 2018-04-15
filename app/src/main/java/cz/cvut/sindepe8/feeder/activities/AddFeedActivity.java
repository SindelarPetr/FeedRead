package cz.cvut.sindepe8.feeder.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import cz.cvut.sindepe8.feeder.R;

    public class AddFeedActivity extends AppCompatActivity {
    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCEL = 0;
    public static final String DATA_URL = "URL";
    public static final String DATA_TITLE = "TITLE";
    private EditText urlEditText;
    private EditText titleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        urlEditText = findViewById(R.id.url);
        titleEditText = findViewById(R.id.title);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId){
            case android.R.id.home:
                cancelActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        cancelActivity();
    }

    private void cancelActivity(){
        setResult(RESULT_CANCEL);
        finish();
    }

    public void addClicked(View view) {
        try {
            // Validate RSS URL
            String textUrl = urlEditText.getText().toString();
            URL url = new URL(textUrl);
            String title = titleEditText.getText().toString();

            Intent intent = new Intent();
            intent.putExtra(DATA_URL, textUrl);
            intent.putExtra(DATA_TITLE, title);
            setResult(RESULT_OK, intent);
            finish();
        }
        catch (MalformedURLException ex){
            Toast errToast = Toast.makeText(this, "The given URL is not valid.", Toast.LENGTH_SHORT);
            errToast.show();
        }
    }
}
