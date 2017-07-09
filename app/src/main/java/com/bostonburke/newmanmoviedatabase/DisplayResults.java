package com.bostonburke.newmanmoviedatabase;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Vector;

/**
 * View controller for activity_display_results.xml.
 *
 * <Credit></Credit>
 */
public class DisplayResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        MRParcel mrp = intent.getParcelableExtra("results");
        ArrayList<MovieRecord> results = mrp.get();

        // TODO: RecyclerView to display movie records
    }

    /**
     * Handles Up navigation
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
